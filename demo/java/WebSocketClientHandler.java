package com.ids.fanbotcontentreviewserver.ws;

import com.alibaba.fastjson.JSONObject;
import com.ids.fanbotcontentreviewserver.model.WsResult;
import com.ids.fanbotcontentreviewserver.service.AsyncService;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@Data
@Slf4j
public class WebSocketClientHandler extends SimpleChannelInboundHandler {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketClientHandler.class);
    private WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;
    /**
     * 加入计数器的目的：由于连接是异步的，可能出现拿着还没连接成功的 channel来进行发送消息（报错），加入计数器后，只有连接成功才会放行测试类中发送消息的代码
     */
    private CountDownLatch lathc;
    private String result;
    private WebSocketClient webSocketClient;
    private AsyncService asyncService;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        IdleStateEvent event = (IdleStateEvent) evt;
        switch (event.state()) {
            case READER_IDLE:
                break;
            case WRITER_IDLE:
                System.err.println("发送数据：ping");
                webSocketClient.getChannel().writeAndFlush(new TextWebSocketFrame("{\"type\":\"ping\"}"));
                break;
            case ALL_IDLE:
                break;
            default:
                break;
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        log.info("通道状态：{}", ctx.channel().isActive());
        log.info("是否握手成功：{}", this.handshaker.isHandshakeComplete());

        FullHttpResponse response;
        if (!this.handshaker.isHandshakeComplete()) {
            try {
                response = (FullHttpResponse) msg;
                //完成握手
                this.handshaker.finishHandshake(ch, response);
                this.handshakeFuture.setSuccess();
                if (this.handshaker.isHandshakeComplete()) {
                    log.info("ws协议升级成功~");
                }
                lathc.countDown();
            } catch (WebSocketHandshakeException var7) {
                FullHttpResponse res = (FullHttpResponse) msg;
                String errorMsg = String.format("WebSocket Client failed to connect,status:%s,reason:%s", res.status(), res.content().toString(CharsetUtil.UTF_8));
                this.handshakeFuture.setFailure(new Exception(errorMsg));
            }
        } else if (msg instanceof FullHttpResponse) {
            response = (FullHttpResponse) msg;
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        } else {
            WebSocketFrame frame = (WebSocketFrame) msg;
            if (frame instanceof BinaryWebSocketFrame) {
                BinaryWebSocketFrame webSocketFrame = (BinaryWebSocketFrame) frame;
                result = webSocketFrame.content().toString(CharsetUtil.UTF_8);
                JSONObject json = JSONObject.parseObject(result);
                if (json.getString("action").equals("push")) {
                    log.info("WS-push数据：{}" , result);
                    asyncService.exec(result);
                }
                if (json.getString("action").equals("pong")) {
                    log.info("WS-心跳数据：{}" , result);
                }
            } else if (frame instanceof TextWebSocketFrame) {
                String content = ((TextWebSocketFrame) msg).text();
                JSONObject json = JSONObject.parseObject(content);
                if (json.getString("action").equals("push")) {
                    log.info("接收数据：" + content);
//                    asyncService.exec(content);
                } else {
                    log.info("接收数据：" + content);
                }

            }
        }
    }


    /**
     * 通道关闭将会触发此方法,并且进行重连20次
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive 触发，通道关闭！" + ctx.channel().isActive());
        webSocketClient.doConnect(ctx, 20);

    }

    public WebSocketClientHandler(CountDownLatch lathc, WebSocketClient webSocketClient, AsyncService asyncService) {
        this.lathc = lathc;
        this.webSocketClient = webSocketClient;
        this.asyncService = asyncService;
    }

    public WebSocketClientHandler(CountDownLatch lathc, WebSocketClient webSocketClient) {
        this.lathc = lathc;
        this.webSocketClient = webSocketClient;
    }

    /**
     * 这个方法netty执行的时候自己会去调
     *
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }
}
