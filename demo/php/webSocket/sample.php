<?php

require 'vendor/autoload.php';


$user_token = 'c912b6f823d925c25d14e8855c04ef5ba4f0712e382ca8421098773b16c511f564cc2544364e5c9f57f6c73c416f848c28df90886e6dcd2adef67c386df5d4588bd4a5715723315afd8731e6a84019d19002d41d6b7b24025cf53123d7cce101';
$device_id = 'bot392522205541433344';
$verison_number = '1.6.60';
$super_str = json_encode(array("platform" => "bot","version" => "1.6.60","channel" => "office","device_id" => $device_id,"build_number" => "1"));
$super_str = base64_encode($super_str);

try {
    $ws = new WebSocket\WebSocketClient("wss://gateway-bot.fanbook.mobi/websocket?id=${user_token}&dId=${device_id}&v=${verison_number}&x-super-properties=${super_str}");
    $data = $ws->run();
    foreach ($data as $msg) {
        echo $msg.PHP_EOL;
    }
} catch (Exception $e) {
    var_dump('异常结束：' . $e->getMessage());
}