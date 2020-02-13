from django.urls import path
from . import views

urlpatterns = [
    path('<company>/', views.room, name='room'),
]