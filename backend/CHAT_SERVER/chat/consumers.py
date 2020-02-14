from channels.generic.websocket import AsyncWebsocketConsumer
from channels.layers import get_channel_layer
import redis
import json


# https://ssungkang.tistory.com/entry/Django-Channels-%EB%B9%84%EB%8F%99%EA%B8%B0%EC%A0%81-%EC%B1%84%ED%8C%85-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-WebSocket-3?category=320582
class ChatConsumer(AsyncWebsocketConsumer) :
    async def connect(self) :
        
        # chat/<company>/ 에서 company 를 가져온다.
        self.company = self.scope['url_route']['kwargs']['company']
        self.company_chat = 'chat_%s' % self.company


        # 동기적인 함수를 비동기적으로 변경
        await self.channel_layer.group_add(
            self.company_chat,
            self.channel_name
        )
        
        await self.accept()

        # TDOD : 최적화 필요
        # 이전 대화 목록 + 몇번째 대화인지 보이기
        r = redis.Redis(charset="utf-8", decode_responses=True)
        chat_list = r.lrange(self.company_chat,0,-1)

        # user_id를 기준으로 현재 채팅의 참여자 수 가져오기, 전체 채팅방 제외!!
        user_cnt = 0

        if self.company != "0":
            self.key = self.company_chat+':user'
            self.user_id = self.scope['url_route']['kwargs']['user']
            self.user_key = self.key+"_"+self.user_id

            # 사용자 저장
            user = r.get(self.user_key) or 0
            r.set(self.user_key, int(user)+1) # 같은 사용자가 열고 있는 소켓 수

            r.sadd(self.key, self.user_id)
            user_cnt = r.scard(self.key)
            
        else :
            # 전체 : 열려있는 소켓 수
            user_cnt = len(r.zrange('asgi::group:chat_0',0,-1))
        
        await self.send(text_data = json.dumps({
            'type' : 1,
            'chat_list' : chat_list,
            'user_cnt' : user_cnt,
        }))

    async def disconnect(self, close_code) :
        # 사용자 제거
        r = redis.Redis()
        if self.company != "0":
            user = r.get(self.user_key)
            user = int(user)-1
            r.set(self.user_key, user)
            # 동시 접속한 사용자가 없다면 삭제 
            if user == 0 : 
                r.srem(self.key, self.user_id)
        
        await self.channel_layer.group_discard(
            self.company_chat,
            self.channel_name
        )

    async def receive(self, text_data) :
        text_data_json = json.loads(text_data)
        nickname = text_data_json['nickname']
        message = text_data_json['message']
        date = text_data_json['date']
        time = text_data_json['time']
        
        r = redis.Redis()
        r.rpush(self.company_chat, text_data)

        await self.channel_layer.group_send(
            self.company_chat,
            {
                'type' : 'chat_message',
                'message' : message,
                'nickname' : nickname,
                'date' : date,
                'time' : time
            }
        )
    
    # 채팅방에서 메시지 receive
    async def chat_message(self, event) :
        message = event['message']
        nickname = event['nickname']
        date = event['date']
        time = event['time']

        # 소켓에게 메시지 전달
        await self.send(text_data = json.dumps({
            'type' : 2,
            'chat_list' : [{
                'message': message,
                'nickname' : nickname,
                'date' : date,
                'time' : time
            }]
        }))
