package com.ids.fanbotcontentreviewserver.ws;

import com.ids.fanbotcontentreviewserver.service.AsyncService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * netty 客户端初始化
 */
@AllArgsConstructor
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private CountDownLatch latch;
    private String host;
    private int port;
    private SslContext sslCtx;
    private String type;
    private SimpleChannelInboundHandler handler;
    private WebSocketClient webSocketClient;
    private AsyncService asyncService;

    public ClientInitializer(CountDownLatch latch, String host, int port, SslContext sslCtx, String type, WebSocketClient webSocketClient) {
        this.latch = latch;
        this.host = host;
        this.port = port;
        this.sslCtx = sslCtx;
        this.type = type;
        this.webSocketClient = webSocketClient;
    }

    public ClientInitializer(CountDownLatch latch, String host, int port, SslContext sslCtx, String type, WebSocketClient webSocketClient,AsyncService asyncService) {
        this.latch = latch;
        this.host = host;
        this.port = port;
        this.sslCtx = sslCtx;
        this.type = type;
        this.webSocketClient = webSocketClient;
        this.asyncService=asyncService;
    }
    @Override
    protected void initChannel(SocketChannel sc) {
        //添加wss协议支持
        if (null != sslCtx) sc.pipeline().addLast(sslCtx.newHandler(sc.alloc(), host, port));
        //动态 handler配置支持
        switch (type) {
            case "1":
                handler = new WebSocketClientHandler(latch, webSocketClient,asyncService);
                break;
            default:
                handler = new WebSocketClientHandler(latch, webSocketClient,asyncService);
                break;
        }
        ChannelPipeline pipeline = sc.pipeline();
        /**
         * 添加心跳支持,超过5秒没有 ping 服务器就会触发 READER_IDLE 事件，进行ping服务器操作
         */
        pipeline.addLast(new IdleStateHandler(0, 20, 0));
        pipeline.addLast(new ChannelHandler[]{new HttpClientCodec(), new HttpObjectAggregator(1024 * 1024 * 10)});
        pipeline.addLast("websocketHandler", handler);
    }

}
