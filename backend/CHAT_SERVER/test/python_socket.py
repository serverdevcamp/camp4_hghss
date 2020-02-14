from tornado.ioloop import IOLoop, PeriodicCallback
from tornado.websocket import websocket_connect
from tornado.escape import json_encode
from tornado import gen 

user_cnt = 0

class Client(object):
    def __init__(self, url, timeout):
        print("create client")
        self.url = url
        self.timeout = timeout
        self.ioloop = IOLoop.instance()
        self.ws = None
        self.connect()
        # PeriodicCallback(self.keep_alive, 20000).start()
        self.ioloop.start()

    @gen.coroutine
    def connect(self):
        print("trying to connect")
        try:
            self.ws = yield websocket_connect(self.url)
        except Exception as e:
            print("connection error: ",e)
        else:
            print("connected")
            self.run()

    @gen.coroutine
    def run(self):
        while True:
            global user_cnt
            user_cnt += 1
            msg = yield self.ws.read_message()
            if msg is None:
                print("connection closed")
                self.ws = None
                break
            print(msg)
            message = {
                "message": str(user_cnt)+"번 유저가 보낸 메시지",
                "nickname": str(user_cnt),
                "date": "2020-02-13",
                "time": "16:50"
            }
            self.ws.write_message(json_encode(message))
            print("메시지 전송완료!!",message)
            self.ioloop.stop() 

    # 계속이 끊겼을 때 연결 다시 하기
    # def keep_alive(self):
    #     if self.ws is None:
    #         self.connect()
    #     else:
    #         self.ws.write_message("keep alive")

if __name__ == "__main__":
    client = Client("ws://13.125.68.49:8000/chat/0/1", 5)
    print(client)
