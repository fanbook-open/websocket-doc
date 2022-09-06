const axios = require('axios');
const JSONBig = require('json-bigint');
const CryptoJS = require("crypto-js");
const WebSocket = require('ws')
const StringDecoder = require('string_decoder').StringDecoder;
const decoder = new StringDecoder('utf8');


const TOKEN = "f7ee3ade24646420829088ffbbbb692e527f76950d4742a8dfef70c31d4aab62558d07d35d53a76934edf3cccc2a8e5c";

axios.defaults.baseURL = `https://a1.fanbook.mobi/api/bot/${TOKEN}`;
axios.defaults.headers.post['Content-Type'] = 'application/json';
axios.defaults.transformResponse = [function transform(data) {
    if (typeof data === 'string') {
        try {
            data = JSONBig.parse(data);
        } catch (e) { /* Ignore */ }
    }
    return data;
}];

axios.get('/getMe').then(function (res) {
    console.log(res.data.result);
    const user_token = res.data.result.user_token;
    const device_id = "bot348383746979790848";
    const verison_number = "1.6.60";

    // super是base64的内容如下 然后encode_base64上来
    // {"platform":"bot","version":"1.6.60","channel":"office","device_id":"{uuid}","build_number":"1"}
    const header_map = JSON.stringify({ "platform": "bot", "version": verison_number, "channel": "office", "device_id": device_id, "build_number": "1" });
    const super_str = new Buffer(header_map).toString('utf8').toString('base64');

    const ws = new WebSocket(`wss://gateway-bot.fanbook.mobi/websocket?id=${user_token}&dId=${device_id}&v=${verison_number}&x-super-properties=${super_str}`)

    ws.onopen = () => {
        console.log("连接状态", ws);
        console.log("open");
        // ws.open("start");

        setInterval(() => {
            var str = '{"type":"ping"}';
            console.log("发送消息: ", str);
            ws.send(str);

        }, 1000 * 20);

        // 获取当前服务器机器人列表
        // setTimeout(() => {
        //     var str = '{"guild_id":"375197147672350720","action":"qb"}';
        //     console.log("发送", str);
        //     ws.send(str);
        // }, 1000 * 3)
        

        // 搜索服务台用户
        // setTimeout(() => {
        //     var str = '{"q":"a","guild_id":"375197147672350720","action":"q"}';
        //     console.log("发送", str);
        //     ws.send(str);
        // }, 1000 * 10)


        //  获取当前服务器机器人列表 NO
        // setTimeout(() => {
        //     const msg = { "content": "{\"type\":\"text\",\"text\":\"浙江商检\",\"contentType\":0}",
        //     "user_id": "389651456455610368",
        //     "channel_id": "389652143147061248",
        //     "guild_id": "375197147672350720",
        //     "seq":50,
        //     "nonce":"404582783411889801",
        //     "type": "send"
        //  }


        //     // var str = JSONBig.stringify(msg);
        //     var str = '{"action":"send","channel_id":"389652143147061248","seq":55,"quote_l1":null,"quote_l2":null,"guild_id":"375197147672350720","content":"{\"type\":\"text\",\"text\":\"测试消息\",\"contentType\":0}","ctype":0,"nonce":"40489027358977185","app_version":"1.6.66"}'
        //     console.log("发送", str);
        //     ws.send(str);
        // }, 1000 * 5)

    };
    ws.onmessage = (evt) => {
        var b = new Buffer(evt.data, 'hex')
        var s = b.toString('utf8');
        console.log('收到消息: ', s );
        // console.log('ws数据： > ', JSONBig.parse(s))
    };
    ws.onclose = (evt) => {
        console.log("WebSocketClosed!");
        console.log(evt);
    };
    ws.onerror = (evt) => {
        console.log("WebSocketError!");
    };

}).catch(function (err) {
    console.log(err);
});