'''
Author: benny
Date: 2023-04-27 10:17:34
LastEditTime: 2023-04-27 10:17:56
LastEditors: benny
Description: 
FilePath: /websocket-doc/demo/python/main.py
'''

import base64
import json
import threading
import time
import requests

from websocket import create_connection, WebSocketConnectionClosedException

# 替换成自己的bot token
TOKEN = '3a8b68f92d968febb1e524a9db1223a41e3e42aad657eb799f9f50362b43274338f78c6ce85xxxxxxx'

# 替换bot id
BOT_ID = '49189442376xxxxxxx'
BASE_URL = 'https://a1.fanbook.mobi/api'


def on_message(message):
    s = message.decode('utf8')
    obj = json.loads(s)
    print(obj)



def send_ping(ws):
    while True:
        time.sleep(20)
        ws.send('{"type":"ping"}')

def get_me():
    response = requests.get(f"{BASE_URL}/bot/{TOKEN}/getMe", timeout=3)
    return response.json()

def handleWS(user_token):
    version = '1.6.60'
    device_id = f'bot{BOT_ID}'
    header_map = json.dumps({
        "device_id": device_id,
        "version": version,
        "platform": "bot",
        "channel": "office",
        "build_number": "1"
    })
    super_str = base64.b64encode(header_map.encode('utf8')).decode('utf8')
    addr = f'wss://gateway-bot.fanbook.mobi/websocket?id={user_token}&dId={device_id}&v={version}&x-super-properties={super_str}'
    ws = create_connection(addr)

    ping_thread = threading.Thread(target=send_ping, args=(ws,))
    ping_thread.daemon = True
    ping_thread.start()
    try:
        while True:
            evt_data = ws.recv()
            on_message(evt_data)
    except WebSocketConnectionClosedException:
        print("WebSocketClosed")
    except Exception as e:
        print("WebSocketError: ", e)


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    res = get_me()
    user_token = res["result"]['user_token']
    handleWS(user_token)

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
