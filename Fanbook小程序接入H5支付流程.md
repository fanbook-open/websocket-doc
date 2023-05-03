# Fanbook小程序接入H5支付流程

## 1. Fanbook小程序开发

Fanbook的小程序是在传统的web页面的基础上，对于部分的js方法做了屏蔽。在url链接加上```fb_redirect&open_type=mp```的后缀，发送到Fanbook的频道或者私聊中打开链接，就可以以小程序的方式打开。

小程序的开发过程中，会用的Fanbook jssdk，使用方法如下：

- 添加script脚本（请尽量使用最新版本）

```<script src="https://fanbook-1251001060.cos.ap-guangzhou.myqcloud.com/fanbook/sdk/fanbook-1.0.4.min.js"></script>```
- 执行初始化方法

```

fb.init({

    success: () => {

        // 初始化逻辑

    }

});

```


- 接口调用, 如获取当前的用户信息

```
fb.getUserInfo().then(v => console.log(v));
```

详细的接口说明：

https://open.fanbook.mobi/document/manage/doc/%E5%B0%8F%E7%A8%8B%E5%BA%8F/%E5%B0%8F%E7%A8%8B%E5%BA%8FJavaScript%20SDK



## 2. 微信支付接入

https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_20&index=1

### 下单接口

https://api.mch.weixin.qq.com/pay/unifiedorder

- 代码实例

```
String outTradeNo = "P" + snowflake.nextIdStr();
String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
map.put("appid", weChatConfig.getAppid());
map.put("mch_id", weChatConfig.getMerid());
map.put("device_info", "WEB");
map.put("nonce_str", WXPayUtil.generateNonceStr());
map.put("body", "充值星钻");
map.put("out_trade_no", outTradeNo);
map.put("total_fee", String.valueOf(rechargeRequest.getAmount()));
map.put("spbill_create_ip", this.getRemortIP(request));
map.put("notify_url", weChatConfig.getCallback());
map.put("trade_type", "MWEB");
String str = "{\"h5_info\":{\"type\":\"Wap\",\"wap_url\":\"https://fanbot.fanbook.mobi\",\"wap_name\":\"充值\"}}";
map.put("scene_info", JSONObject.parseObject(str).toString());
String signStr = WXPayUtil.generateSignature(map, weChatConfig.getKey());
map.put("sign", signStr);
String xmlStr = WXPayUtil.generateSignedXml(map, weChatConfig.getKey());
log.info("signStr:{}", signStr);
log.info("xmlStr:{}", xmlStr);
Map<String, String> rmap = this.post(unifiedorder, xmlStr);
```

### 查询订单


```
String unifiedorder = "https://api.mch.weixin.qq.com/pay/orderquery";
map.put("appid", weChatConfig.getAppid());
map.put("mch_id", weChatConfig.getMerid());
map.put("nonce_str", WXPayUtil.generateNonceStr());
map.put("transaction_id", '1008450740201411110005820873');
String signStr = WXPayUtil.generateSignature(map, weChatConfig.getKey());
map.put("sign", signStr);
String xmlStr = WXPayUtil.generateSignedXml(map, weChatConfig.getKey());
log.info("signStr:{}", signStr);
log.info("xmlStr:{}", xmlStr);
Map<String, String> rmap = this.post(unifiedorder, xmlStr);

```

