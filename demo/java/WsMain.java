package com.ids.fanbotcontentreviewserver.ws;

import com.idreamsky.fanbook.sdk.IFanbookBotClient;
import com.idreamsky.fanbook.sdk.bot.method.v20220429.GetMeMethod;
import com.idreamsky.fanbook.sdk.bot.model.v20220429.User;
import com.ids.fanbotcontentreviewserver.service.AsyncService;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Component
@Slf4j
public class WsMain {

    @Resource
    AsyncService asyncService;
    @Resource
    IFanbookBotClient fanbookBotClient;

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String wssUrl = "wss://a1-newtest.fanbook.mobi:443/websocket?%s&%s&%s&%s";
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String id = "id=c912b6f823d925c25d14e8855c04ef5bab5129b33481a0de9b6dd22725997280a9df6ac860888565b27b12c070330afa38296e2106ea3626fd205b54c0520ae6f2a3331333b8a265f54e9fa9e1e4017d83bac98fcfbf9e654dd608fbc4eda46e";
        String did = "dId=" + uuid;
        String v = "v=1.6.60";
        String s = "{\"platform\":\"bot\",\"version\":\"1.6.60\",\"channel\":\"office\",\"device_id\":\"%s\",\"build_number\":\"1\"}";
        s = String.format(s, uuid);
        String xSuper = "x-super-properties=" + Base64Utils.encodeToString(s.getBytes(StandardCharsets.UTF_8));
        wssUrl = String.format(wssUrl, id, did, v, xSuper);
        String testUrl = "wss://gateway-bot.fanbook.mobi:443/websocket?id=c912b6f823d925c25d14e8855c04ef5b029480a66b4846c8e7b481fd23d8c5497e42e80106fdd6bd8373eb53ab62eb9434f339e147f9b9f550200738dec94030c9e3a084f479ddca61abd7ac0cd4a28963e8af49ed2231a34f5fa79701e5d977&dId=bot352721845394341888&v=1.6.60&x-super-properties=eyJwbGF0Zm9ybSI6ImJvdCIsInZlcnNpb24iOiIxLjYuNjAiLCJjaGFubmVsIjoib2ZmaWNlIiwiZGV2aWNlX2lkIjoiZElkPWJvdDM1MjcyMTg0NTM5NDM0MTg4OCIsImJ1aWxkX251bWJlciI6IjEifQ==";
        WebSocketClient webSocketClient = new WebSocketClient(testUrl, "1", countDownLatch);
        webSocketClient.connect();
        countDownLatch.await();
        webSocketClient.getChannel().writeAndFlush(new TextWebSocketFrame("{\"type\":\"ping\"}"));
    }


    public void initWs() throws InterruptedException {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            String wssUrl = "wss://gateway-bot.fanbook.mobi:443/websocket?%s&%s&%s&%s";
            GetMeMethod getMeMethod = new GetMeMethod();
            User botUser = fanbookBotClient.getBotResponse(getMeMethod);
            String id = "id=" + botUser.getUserToken();
            String did = "dId=bot" + botUser.getId();
            String v = "v=1.6.60";
            String s = "{\"platform\":\"bot\",\"version\":\"1.6.60\",\"channel\":\"office\",\"device_id\":\"%s\",\"build_number\":\"\"}";
            s = String.format(s, did);
            String xSuper = "x-super-properties=" + Base64Utils.encodeToString(s.getBytes(StandardCharsets.UTF_8));
            wssUrl = String.format(wssUrl, id, did, v, xSuper);
            System.err.println("wssUrl:" + wssUrl);
            WebSocketClient webSocketClient = new WebSocketClient(wssUrl, "1", countDownLatch, asyncService);
            webSocketClient.connect();
            countDownLatch.await();
            webSocketClient.getChannel().writeAndFlush(new TextWebSocketFrame("{\"type\":\"ping\"}"));
            while (true) {
                if (!webSocketClient.getChannel().isActive()) {
                    webSocketClient.getChannel().close();
                    log.info("WS断开时间：{}", this.convertTimestamp2Date(System.currentTimeMillis()));
                    break;
                }
                ThreadUtils.sleep(Duration.ofMillis(5000));
                log.info("开始休眠：{}", this.convertTimestamp2Date(System.currentTimeMillis()));
            }
        } catch (Exception e) {
            //异常断开休眠3秒重试
            log.error("异常断开连接");
            ThreadUtils.sleep(Duration.ofMillis(3000));
        }
    }

    public String convertTimestamp2Date(Long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(timestamp));
    }

}
