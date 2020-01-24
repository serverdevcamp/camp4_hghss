from channels.generic.websocket import AsyncWebsocketConsumer
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

    async def disconnect(self, close_code) :
        await self.channel_layer.group_discard(
            self.company_chat,
            self.channel_name
        )

    async def receive(self, text_data) :
        text_data_json = json.loads(text_data)
        message = text_data_json['message']

        # 채팅방 모두에게 메시지 보내기
        await self.channel_layer.group_send(
            self.company_chat,
            {
                'type' : 'chat_message',
                'message' : message
            }
        )
    
    # 채팅방에서 메시지 receive
    async def chat_message(self, event) :
        message = event['message']
        # 소켓에게 메시지 전달
        await self.send(text_data = json.dumps({
            'message':message
        }))
