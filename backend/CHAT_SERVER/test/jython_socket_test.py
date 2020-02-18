from net.grinder.script import Test
from net.grinder.plugin.http import HTTPRequest
from net.grinder.script.Grinder import grinder

from tornado.ioloop import IOLoop
from tornado.websocket import websocket_connect
from tornado import gen 

user_cnt = 0
class Client(object):
    def __init__(self, url, timeout):
        self.url = url
        self.timeout = timeout
        self.ioloop = IOLoop.instance()
        self.ws = None
        self.connect()
        self.ioloop.start()

    @gen.coroutine
    def connect(self):
        try:
            self.ws = yield websocket_connect(self.url)
        except Exception, e:
            grinder.logger.error("connection error: "+str(e))
            grinder.statistics.forLastTest.success = 0
        else:
            self.send_msg()
            self.read_msg()
        finally :
            self.ioloop.stop() 

    @gen.coroutine 
    def send_msg(self) :
        try :
            global user_cnt
            user_cnt += 1
                
            message = {
                "message": "from "+str(user_cnt),
                "nickname": str(user_cnt),
                "date": "2020-02-13",
                "time": "16:50"
            }
            self.ws.write_message(json_encode(message))
        except Exception, e:
            grinder.logger.error("message send error", e) 
            grinder.statistics.forLastTest.success = 0
    
    @gen.coroutine
    def read_msg(self) :
        try :
            msg = yield self.ws.read_message()
        except Exception, e:
            grinder.logger.error("message read error", e)  
            grinder.statistics.forLastTest.success = 0 


class TestRunner:
    def __init__(self):
        grinder.statistics.delayReports=True
 
    def connection_test(self) :
        try:
            client = Client("ws://13.125.68.49:8000/chat/0/0", 5)
            return 1
        except Exception, e:
            grinder.logger.error("everyting not ok", e)  
            grinder.statistics.forLastTest.success = 0 
 
    # test method       
    def __call__(self):
        try :
            msg = self.connection_test()
        except Exception, e:
            grinder.logger.error(str(e))
            grinder.statistics.forLastTest.success = 0
        else :
            grinder.statistics.forLastTest.success = 1
            
Test(1, "HGHSS-ChatServer Test").record(TestRunner.connection_test)