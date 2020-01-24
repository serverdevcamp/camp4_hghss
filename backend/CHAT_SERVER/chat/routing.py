from django.conf.urls import url
from . import consumers

websocket_urlpatterns = [
    url(r"^chat/(?P<company>\w+)/$", consumers.ChatConsumer)
]

# var socket = new WebSocket('ws://' + 'localhost:8080' + '/chat/company1/');