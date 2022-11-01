# Fanbook WebSocket消息结构

### 视频

```java
{
  "action": "push",
  "data": {
    "content": "{\"type\":\"video\",\"url\":\"https://fb-cdn-video.fanbook.mobi/fanbook/app/files/chatroom/video/7fc642feb67b1f437ed41ae2038578e0.mp4\",\"videoName\":\"45704-1661928761.mp4\",\"width\":768,\"height\":1024,\"localPath\":\"/data/user/0/com.idreamsky.buff/cache/multi_image_pick/thumb/45704-1661928761.mp4\",\"thumbUrl\":\"https://fb-cdn.fanbook.mobi/fanbook/app/files/chatroom/image/34bb8c63cfd531c7547563ef2c15a729.jpg\",\"thumbWidth\":540,\"thumbHeight\":960,\"duration\":10,\"thumbName\":\"45704-1661928761.jpg\",\"fileType\":\"video/mp4\",\"localThumbPath\":\"/data/user/0/com.idreamsky.buff/cache/multi_image_pick/thumb/45704-1661928761.jpg\",\"localIdentify\":\"45704\"}",
    "time": 1662001441157,
    "user_id": "266195639770021888",
    "channel_id": "384533822017904640",
    "message_id": "405918672620658688",
    "quote_l1": null,
    "quote_l2": null,
    "guild_id": "357042844231282688",
    "channel_type": 0,
    "status": 0,
    "nonce": "405905321354104833",
    "ctype": null,
    "member": {
      "nick": null,
      "roles": [],
      "guild_card": []
    },
    "author": {
      "nickname": "尼坑",
      "username": "724521",
      "avatar": "https://fb-cdn.fanbook.mobi/fanbook/app/files/service/headImage/dd7b17069196ffba51c27a26aa1c1204.jpg",
      "avatar_nft": null,
      "bot": false
    },
    "desc": ""
  },
  "ack": -1,
  "seq": null
}
```

### 语音

```java
{
  "action": "push",
  "data": {
    "content": "{\"type\":\"voice\",\"url\":\"https://fb-cdn-video.fanbook.mobi/fanbook/app/files/chatroom/audio/787aefc929801eb1c2d589a4025d609b.aac\",\"path\":\"/data/user/0/com.idreamsky.buff/cache/voice/1662001525887.aac\",\"second\":3,\"isRead\":false}",
    "time": 1662001528697,
    "user_id": "266195639770021888",
    "channel_id": "384533822017904640",
    "message_id": "405919039790030848",
    "quote_l1": null,
    "quote_l2": null,
    "guild_id": "357042844231282688",
    "channel_type": 0,
    "status": 0,
    "nonce": "405918672620658689",
    "ctype": null,
    "member": {
      "nick": null,
      "roles": [],
      "guild_card": []
    },
    "author": {
      "nickname": "尼坑",
      "username": "724521",
      "avatar": "https://fb-cdn.fanbook.mobi/fanbook/app/files/service/headImage/dd7b17069196ffba51c27a26aa1c1204.jpg",
      "avatar_nft": null,
      "bot": false
    },
    "desc": ""
  },
  "ack": -1,
  "seq": null
}
```

### 图片

```java
{
  "action": "push",
  "data": {
    "content": "{\"type\":\"image\",\"url\":\"https://fb-cdn.fanbook.mobi/fanbook/app/files/chatroom/image/f804bb49ede228141a24377ac80fd4e1.jpg\",\"width\":1080,\"height\":1080,\"fileType\":\"image/jpeg\",\"localFilePath\":\"/data/user/0/com.idreamsky.buff/cache/multi_image_pick/thumb/45703-1661776299-thumb.jpg\",\"localIdentify\":\"45703\",\"thumb\":true}",
    "time": 1662001654555,
    "user_id": "266195639770021888",
    "channel_id": "384533822017904640",
    "message_id": "405919567676743680",
    "quote_l1": null,
    "quote_l2": null,
    "guild_id": "357042844231282688",
    "channel_type": 0,
    "status": 0,
    "nonce": "405919039790030849",
    "ctype": null,
    "member": {
      "nick": null,
      "roles": [],
      "guild_card": []
    },
    "author": {
      "nickname": "尼坑",
      "username": "724521",
      "avatar": "https://fb-cdn.fanbook.mobi/fanbook/app/files/service/headImage/dd7b17069196ffba51c27a26aa1c1204.jpg",
      "avatar_nft": null,
      "bot": false
    },
    "desc": ""
  },
  "ack": -1,
  "seq": null
}
```

### 普通文本

```java
{
  "action": "push",
  "data": {
    "content": "{\"type\":\"text\",\"text\":\"${@!405327427904995328}${/签到}\",\"contentType\":67}",
    "time": 1663057604639,
    "user_id": "173022860380475392",
    "channel_id": "384533822017904640",
    "message_id": "410348543337861120",
    "quote_l1": null,
    "quote_l2": null,
    "guild_id": "357042844231282688",
    "channel_type": 0,
    "status": 0,
    "nonce": "408877101677064193",
    "ctype": 67,
    "mentions": [
      {
        "nickname": "积分任务机器人TEST",
        "user_id": "405327427904995328"
      }
    ],
    "member": {
      "nick": null,
      "roles": [
        "363599918687846400",
        "369379755633410048",
        "400936965375000576",
        "363598927506702336"
      ],
      "guild_card": [
        "357042844231282688;1651893627580;1",
        "357042844231282688;1651893820654;1",
        "357042844231282688;1651893946974;1",
        "357042844231282688;1651894025264;1",
        "357042844231282688;1652084644782;1"
      ]
    },
    "author": {
      "nickname": "Peng",
      "username": "279968",
      "avatar": "https://fb-cdn.fanbook.mobi/fanbook/app/files/service/headImage/448cae3840a2f16d16cf939e0d254fd9",
      "avatar_nft": null,
      "bot": false
    },
    "desc": ""
  },
  "ack": -1,
  "seq": null
}
```

### 表态

```java
{
  "action": "push",
  "data": {
    "content": "{\"type\":\"reaction\",\"action\":\"add\",\"id\":\"405920114198745088\",\"emoji\":{\"name\":\"%E7%9E%8C%E7%9D%A1\",\"avatar\":null,\"count\":1},\"text\":\"\"}",
    "time": 1662001857436,
    "user_id": "173022860380475392",
    "channel_id": "384533822017904640",
    "message_id": "405920418612903936",
    "guild_id": "357042844231282688",
    "channel_type": 0
  }
}
```

### 圈子点赞（取消点赞无消息）

```java
{
  "action": "circle",
  "data": {
    "method": "reaction",
    "channel_id": "357042845351161856",
    "post_id": "403808639791398912",
    "comment_id": "",
    "guild_id": "357042844231282688",
    "user_id": "173022860380475392",
    "circle_type": "post_like",
    "topic_id": "364695415284576256",
    "receive_id": "314352321645522944",
    "is_push": false,
    "send_id": "173022860380475392"
  }
}
```

### 圈子发帖

```java
{
  "action": "circleEnter",
  "data": {
    "records": {
      "guild_id": "357042844231282688",
      "channel_id": "357042845351161856",
      "user_id": "266195639770021888",
      "topic_id": "364695415284576256",
      "post_id": "405908121265897472",
      "content": "[{\"insert\":\"当前版本暂不支持此信息类型\"}]",
      "title": "",
      "comment": false,
      "reaction": false,
      "created_at": 1661998925518,
      "updated_at": 1661998925518,
      "comment_at": 1661998925518,
      "content_v2": "[{\"insert\":\"\\n\"},{\"insert\":{\"name\":\"2ab4e2bb-321d-4374-a30a-6141ec06a329/756ad484-7757-4ded-b05a-7a0a743d3d38.jpg\",\"source\":\"https://fb-cdn.fanbook.mobi/fanbook/app/files/chatroom/image/32b1ce3273548c55bb534580ac41207c.jpg\",\"width\":1080.0,\"height\":1412.0,\"checkPath\":\"\",\"identifier\":\"\",\"_type\":\"image\",\"request_id\":\"2ab4e2bb-321d-4374-a30a-6141ec06a329\",\"_inline\":false}},{\"insert\":\"\\n\"}]",
      "post_type": "image",
      "has_video": 0,
      "file_id": "",
      "geo_region": "广东",
      "geo_ip": "103.170.4.212",
      "mentions": "[]"
    },
    "action": "circleEnter",
    "guild_id": "357042844231282688",
    "method": "post_add",
    "new_post_total": 1
  }
}
```

### 邀请

```java
{
  "action": "push",
  "data": {
    "guild_id": "357042844231282688",
    "channel_id": "357042844399042560",
    "time": 1662006747495,
    "content": "{\"user_id\":\"266195639770021888\",\"type\":\"newJoin\",\"message_id\":\"405940929011122178\",\"code\":\"ECEK3F9r\",\"index\":36}",
    "user_id": "266195639770021888",
    "message_id": "405940929011122178",
    "channel_type": 0,
    "msg_type": 6,
    "author": {
      "nickname": "尼坑",
      "username": "724521",
      "avatar": "https://fb-cdn.fanbook.mobi/fanbook/app/files/service/headImage/dd7b17069196ffba51c27a26aa1c1204.jpg",
      "avatar_nft": null,
      "bot": false
    }
  }
}
```

### 退出服务器

```java
{
  "action": "guildNotice",
  "data": {
    "method": "userQuit",
    "guild_id": "357042844231282688",
    "user_id": "266195639770021888"
  }
}
```

### 圈子分享

```java
{
  "action": "push",
  "data": {
    "content": "{\"type\":\"circleShareEntity\",\"data\":{\"user\":{\"user_id\":\"314352321645522944\",\"avatar\":\"https://fb-cdn.fanbook.mobi/fanbook/app/files/service/headImage/ee2be06bc80ff976af9a9ffc438ce67d.jpg\",\"username\":\"1908591\",\"nickname\":\"404NotFound\",\"gnick\":\"\"},\"post\":{\"topic_id\":\"364695415284576256\",\"topic_name\":\"碎碎念\",\"guild_id\":\"357042844231282688\",\"channel_id\":\"357042845351161856\",\"post_id\":\"403808639791398912\",\"created_at\":\"1661498370223\",\"content\":\"[{\\\"insert\\\":\\\"当前版本暂不支持此信息类型\\\"}]\",\"post_type\":\"image\",\"content_v2\":\"[{\\\"insert\\\":\\\"[爱心]\\\"},{\\\"insert\\\":\\\"\\\\n\\\"},{\\\"insert\\\":{\\\"name\\\":\\\"1a8857aa-f89d-410c-a52a-ef84569d4ba6/d942a874-1529-4984-a024-db9aa8d42ca2.jpg\\\",\\\"source\\\":\\\"https://fb-cdn.fanbook.mobi/fanbook/app/files/chatroom/image/8c1ec2aefb6b4c70ae3423b392433d42.jpg\\\",\\\"width\\\":1380.0,\\\"height\\\":1080.0,\\\"checkPath\\\":\\\"\\\",\\\"identifier\\\":\\\"\\\",\\\"_type\\\":\\\"image\\\",\\\"request_id\\\":\\\"1a8857aa-f89d-410c-a52a-ef84569d4ba6\\\",\\\"_inline\\\":false}},{\\\"insert\\\":\\\"\\\\n\\\"}]\",\"title\":\"sz\",\"tc_doc_content\":\"\"},\"sub_info\":{\"comment_total\":\"8\",\"comment_detail\":[],\"like_total\":\"1\",\"liked\":\"0\",\"like_id\":\"405256116801372160\"}}}",
    "time": 1662001960720,
    "user_id": "173022860380475392",
    "channel_id": "384533822017904640",
    "message_id": "405920851825823744",
    "quote_l1": null,
    "quote_l2": null,
    "guild_id": "357042844231282688",
    "channel_type": 0,
    "status": 0,
    "nonce": "405920114198745089",
    "ctype": null,
    "member": {
      "nick": null,
      "roles": [
        "363599918687846400",
        "369379755633410048",
        "400936965375000576",
        "363598927506702336"
      ],
      "guild_card": []
    },
    "author": {
      "nickname": "Peng",
      "username": "279968",
      "avatar": "https://fb-cdn.fanbook.mobi/fanbook/app/files/service/headImage/448cae3840a2f16d16cf939e0d254fd9",
      "avatar_nft": null,
      "bot": false
    },
    "desc": ""
  },
  "ack": -1,
  "seq": null
}
```

### 话题分享

```java
{
  "action": "push",
  "data": {
    "content": "{\"messageId\":\"405707616044490752\",\"channelId\":\"384533822017904640\",\"userId\":\"173022860380475392\",\"type\":\"topicShare\"}",
    "time": 1662004566127,
    "user_id": "173022860380475392",
    "channel_id": "384533822017904640",
    "message_id": "405931779694825472",
    "quote_l1": null,
    "quote_l2": null,
    "guild_id": "357042844231282688",
    "channel_type": 0,
    "status": 0,
    "nonce": "405931693719982081",
    "ctype": null,
    "member": {
      "nick": null,
      "roles": [
        "363599918687846400",
        "369379755633410048",
        "400936965375000576",
        "363598927506702336"
      ],
      "guild_card": []
    },
    "author": {
      "nickname": "Peng",
      "username": "279968",
      "avatar": "https://fb-cdn.fanbook.mobi/fanbook/app/files/service/headImage/448cae3840a2f16d16cf939e0d254fd9",
      "avatar_nft": null,
      "bot": false
    },
    "desc": ""
  },
  "ack": -1,
  "seq": null
}
```

### 富文本

```java
{
  "action": "push",
  "data": {
    "content": "{\"type\":\"richText\",\"title\":\"hh\",\"document\":\"[{\\\"insert\\\":\\\"hhjj\\\\n\\\"}]\",\"v2\":\"[{\\\"insert\\\":\\\"hhjj\\\\n\\\"}]\",\"v\":2}",
    "time": 1662006361106,
    "user_id": "266195639770021888",
    "channel_id": "384533822017904640",
    "message_id": "405939308378193920",
    "quote_l1": null,
    "quote_l2": null,
    "guild_id": "357042844231282688",
    "channel_type": 0,
    "status": 0,
    "nonce": "405938212414988289",
    "ctype": null,
    "member": {
      "nick": null,
      "roles": [],
      "guild_card": []
    },
    "author": {
      "nickname": "尼坑",
      "username": "724521",
      "avatar": "https://fb-cdn.fanbook.mobi/fanbook/app/files/service/headImage/dd7b17069196ffba51c27a26aa1c1204.jpg",
      "avatar_nft": null,
      "bot": false
    },
    "desc": ""
  },
  "ack": -1,
  "seq": null
}
```

### 用户离线消息（无）

### 用户上线消息（无）