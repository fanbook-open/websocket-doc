package com.ids.fanbotcontentreviewserver.ws;

import com.ids.fanbotcontentreviewserver.service.AsyncService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 1、通道的开启：发送syn、ack数据包
 * 2、三次握手：通道是可靠的，你可以和我交流了
 */
@Data
public class WebSocketClient {
    private String uri;
    private CountDownLatch latch;
    private ClientInitializer clientInitializer;
    private SslContext sslCtx;
    private String host;
    private int port;
    private String scheme;
    private URI websocketURI;
    private String type;
    private String userId;
    private Bootstrap bootstrap;
    private Channel channel;
    int repeatConnectCount = 0;

    public WebSocketClient(String uri, String type, CountDownLatch latch, AsyncService asyncService) throws URISyntaxException {
        this.uri = uri;
        this.websocketURI = new URI(uri);
        this.host = websocketURI.getHost();

        this.scheme = websocketURI.getScheme();
        this.latch = latch;
        this.type = type;
        if ("wss".equals(scheme)) {
            this.port = 443;
            //初始化SslContext，这个在wss协议升级的时候需要用到
            try {
                this.sslCtx = SslContextBuilder.forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            } catch (SSLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("ws".equals(scheme)) {
            this.port=80;
            this.sslCtx = null;
        }
        this.clientInitializer = new ClientInitializer(latch, host, port, sslCtx, type, WebSocketClient.this,asyncService);
    }
    public WebSocketClient(String uri, String type, CountDownLatch latch) throws URISyntaxException {
        this.uri = uri;
        this.websocketURI = new URI(uri);
        this.host = websocketURI.getHost();

        this.scheme = websocketURI.getScheme();
        this.latch = latch;
        this.type = type;
        if ("wss".equals(scheme)) {
            this.port = 443;
            //初始化SslContext，这个在wss协议升级的时候需要用到
            try {
                this.sslCtx = SslContextBuilder.forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            } catch (SSLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("ws".equals(scheme)) {
            this.port=80;
            this.sslCtx = null;
        }
        this.clientInitializer = new ClientInitializer(latch, host, port, sslCtx, type, WebSocketClient.this);
    }
    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup(4);
        try {
            bootstrap = new Bootstrap();
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_BACKLOG, 1024 * 1024 * 10)
                    .group(group)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .channel(NioSocketChannel.class)
                    .handler(clientInitializer);
            doConnect(null, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SneakyThrows
    protected void doConnect(ChannelHandlerContext ctx, Integer count) {
        //重连次数每次加一
        repeatConnectCount++;
        if (repeatConnectCount > count) {
            if (null != ctx) {
                System.out.println("通道关闭、重连失败");
                ctx.channel().close();
                return;
            }
        }
        if (channel != null && channel.isActive()) {
            System.err.println("通道正常");
            return;
        }
        //建立HTTP连接
        ChannelFuture future = bootstrap.connect(host, port).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                    //连接成功重置重连次数为0
                    repeatConnectCount = 0;
                    System.out.println("通道开启成功~");
                    HttpHeaders httpHeaders = new DefaultHttpHeaders();
                    WebSocketClientHandshaker webSocketClientHandshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI,
                            WebSocketVersion.V13,
                            (String) null,
                            true,
                            httpHeaders);
                    WebSocketClientHandler handler = (WebSocketClientHandler) channel.pipeline().get("websocketHandler");
                    //升级为ws协议
                    System.err.println("开始升级http协议~准备开始握手");
                    webSocketClientHandshaker.handshake(channel);
                    handler.setHandshaker(webSocketClientHandshaker);
                } else {
                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            System.err.println("重试开启通道~" + repeatConnectCount);
                            doConnect(ctx, count);
                        }
                    }, 3, TimeUnit.SECONDS);
                }
            }
        });
        channel = future.channel();
    }

}
