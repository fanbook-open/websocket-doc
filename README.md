# 说明

Fanbook开放平台获取消息的```getUpdates```暂时无法使用。 当前在bot开发过程中，统一用```websocket```的方案。具体的接入方法如下:

# 流程

## 1. 获取user_token

通过开放平台```getMe```接口，获取bot的```user_token```

## 2. 链接ws服务器


```javascript
const ws = new WebSocket(`wss://gateway-bot.fanbook.mobi/websocket?id=${user_token}&dId=${device_id}&v=${verison_number}&x-super-properties=${super_str}`)
```

**参数说明**

| 参数名          | 类型      |  说明           |
| -------------- | -------- | --------------- |
| user_token     |  string  |  bot的user_token，通过getMe获取，每次getMe会获取新的token |
| device_id      |  string  |  设备的唯一标识，建议用bot+bot_id          |
| verison_number |  string  |  1.6.60        |
| super_str      |  string  |      {"platform":"bot","version":"1.6.60","channel":"office","device_id":"{uuid}","build_number":"1"} 序列化后再base64后的值           |

**返回示例**

```
{
	"action": "connect",
	"data": {
		"client_id": "0a0504670b8900008020"
	}
}
```

**返回参数说明**

无

**示例代码**

```javascript

const user_token = res.data.result.user_token;
const device_id = "bot348383746979790848";
const verison_number = "1.6.60";

// super是base64的内容如下 然后encode_base64上来
// {"platform":"bot","version":"1.6.60","channel":"office","device_id":"{uuid}","build_number":"1"}

const header_map = JSON.stringify({ "platform": "bot", "version": verison_number, "channel": "office", "device_id": device_id, "build_number": "1" });
const super_str = new Buffer(header_map).toString('utf8').toString('base64');

const ws = new WebSocket(`wss://gateway-bot.fanbook.mobi/websocket?id=${user_token}&dId=${device_id}&v=${verison_number}&x-super-properties=${super_str}`)


```

## 3. ws广播

链接上ws服务器后，会受到bot所在的服务器的广播消息

```javascript

 ws.onmessage = (evt) => {
        var b = new Buffer(evt.data, 'hex')
        var s = b.toString('utf8');
        console.log('收到消息: ', s );
        // console.log('ws数据： > ', JSONBig.parse(s))
    };

```

**参数说明**

无

**返回示例**

```
{
	"action": "push",
	"data": {
		"content": "{\"type\":\"text\",\"text\":\" 不不不\",\"contentType\":0}",
		"time": 1662465324372,
		"user_id": "138519745866498048",
		"channel_id": "337228240579985408",
		"message_id": "407864339844861952",
		"quote_l1": null,
		"quote_l2": null,
		"guild_id": "221110739232882688",
		"channel_type": 0,
		"status": 0,
		"nonce": "407500515610501123",
		"ctype": 0,
		"member": {
			"nick": "Benny",
			"roles": ["221110739262242816", "347412434123751424", "347412228695130112", "347412557998338048"],
			"guild_card": ["221110739232882688;92e2ccf8-cabc-49ab-8b82-90f3c0f5d9e7;1", "221110739232882688;ad122c79-9921-496c-9ab7-15d432ca935f;1", "221110739232882688;f693577d65f54cc4a587f968e608b7a1;1", "221110739232882688;ff6b101c-c656-4dde-a1bb-d8285b9e7e1a;3"]
		},
		"author": {
			"nickname": "benny.xu",
			"username": "253097",
			"avatar": "https:\/\/fb-cdn.fanbook.mobi\/fanbook\/app\/files\/service\/headImage\/dea1290cb34129047fd2b4eb0675d793.jpg",
			"avatar_nft": "https:\/\/nft-1251001060.file.myqcloud.com\/collection-admin\/product-picture\/1643534873719-首页老虎.png",
			"bot": false
		},
		"desc": ""
	},
	"ack": -1,
	"seq": null
}
```


**返回参数说明**

| 参数名     | 类型   | 说明                 |
| ---------- | ------ | -------------------- |
| content    | string | 当前消息的内容 |
| time       | number | 时间戳(秒)       |
| user_id    | string | 当前用户Fanbook昵称  |
| channel_id | string | 当前用户FanbookId    |
| message_id | string | 当前用户头像         |
| quote_l1   | string | 当前用户Fanbook短ID  |
| member     | string | 当前用户登录token    |

其他说明待补充；

**示例代码**

```javascript

const user_token = res.data.result.user_token;
const device_id = "bot348383746979790848";
const verison_number = "1.6.60";

// super是base64的内容如下 然后encode_base64上来
// {"platform":"bot","version":"1.6.60","channel":"office","device_id":"{uuid}","build_number":"1"}

const header_map = JSON.stringify({ "platform": "bot", "version": verison_number, "channel": "office", "device_id": device_id, "build_number": "1" });
const super_str = new Buffer(header_map).toString('utf8').toString('base64');

const ws = new WebSocket(`wss://gateway-bot.fanbook.mobi/websocket?id=${user_token}&dId=${device_id}&v=${verison_number}&x-super-properties=${super_str}`)


```


## 4. 心跳

当链接上ws服务器后，每25秒需要向服务器发送心跳包，超过70秒没有发出心跳请求就会断开链接。

```javascript

setInterval(() => {
    var str = '{"type":"ping"}';
    console.log("发送消息: ", str);
    ws.send(str);

}, 1000 * 25);

```

**参数说明**

无

**返回示例**

```
{
	"action": "pong",
	"data": {
		"time": 1662465454
	}
}
```

**返回参数说明**

| 参数名     | 类型   | 说明                 |
| ---------- | ------ | -------------------- |
| time       | number | 时间戳(秒)       |


# 注意事项


1. [项目示例](demo)展示java和nodejs的实现方式，可以作为参考，项目不能独立运行
2. 目前ws的方式无法获取圈子内的评论消息；
3. 每次调用```getMe```接口都会刷新```user_token```,在服务启动时需要先请求```getMe```接口，避免token过期的情况。
4. ```user_tokne```的过期时间为24小时，如果心跳包不间断，token将永久有效。

