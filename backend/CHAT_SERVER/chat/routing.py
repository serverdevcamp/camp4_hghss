from django.conf.urls import url
from . import consumers

websocket_urlpatterns = [
    url(r"^chat/(?P<company>\w+)/$", consumers.ChatConsumer)
]

"""
var socket = new WebSocket('ws://' + 'localhost:8081' + '/chat/0/');
var chat = []

// 데이터 전송
socket.onopen = function (event) {
    // 소켓열리고 실행
    var msg = {message:"메시지 테스트@@!!", nickname: "헤온 쥐잡이뱀"};
    socket.send(JSON.stringify(msg)); 
};

// 데이터 수신
socket.onmessage = function(event) {
    var msg = JSON.parse(event.data);
    console.log(msg)
    chat.push(msg)
}
"""