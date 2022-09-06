# 说明

Fanbook开放平台获取消息的```getUpdates```暂时无法使用。 在bot开发过程中，统一用```websocket```的方案。具体的接入方法如下

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
| user_token     |  string  |                 |
| device_id      |  string  |                 |
| verison_number |  string  |                 |
| super_str      |  string  |                 |

**返回示例**


**返回参数说明**

| 参数名     | 类型   | 说明                 |
| ---------- | ------ | -------------------- |
| createTime | string | 当前用户账号创建时间 |
| roleId     | string | 当前用户角色ID       |
| nickname   | string | 当前用户Fanbook昵称  |
| id         | string | 当前用户FanbookId    |
| avatar     | string | 当前用户头像         |
| username   | string | 当前用户Fanbook短ID  |
| token      | string | 当前用户登录token    |

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

# 注意事项