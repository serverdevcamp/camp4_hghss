<template>
<div id="chat">
  <v-row class="chat-header">
    <v-col cols="3" class="title point-font">
      채팅
    </v-col>
    <v-col cols="3">
      <input type="text" class="chat-search" placeholder="기업명을 입력하세요."/>
    </v-col>
  </v-row>
  <v-row class="my-chat">
    <v-col>
      <v-row>
        <p class="subtitle point-font">내 채팅</p>
      </v-row>
      <v-row class="chat-room">
        <!-- 여기에 채팅방 내용 보이기 -->
      </v-row>
    </v-col>
  </v-row>
  <v-row class="fav-chat">
    <v-col>
      <v-row>
        <p class="subtitle point-font">즐겨찾기/작성한 기업</p>
      </v-row>
      <v-row class="chat-room">
        \
      </v-row>
    </v-col>
  </v-row>
  <v-row class="hot-chat">
    <v-col>
      <v-row>
        <p class="subtitle point-font" @click="sendMessage()">인기 채팅</p>
      </v-row>
      <v-row class="chat-room">
      </v-row>
    </v-col>
  </v-row>
  
</div>
</template>
<script>
import { mapGetters, mapActions } from "vuex";
export default {
  data: () => ({}),
  computed: {
    ...mapGetters([
      "getSocket",
    ])
  },
  methods: {
    ...mapActions([
      "openSocket"
    ]),
    sendMessage : function(){
      var socket = this.getSocket('company1')
      var msg = {message:"이거슨 추가된 메시지!", nickname: "닉네임"}
      socket.send(JSON.stringify(msg))
    }
  },
  created(){
    this.openSocket('company1')
  }
};
</script>
<style lang="scss">
$calendar-border: #f0f0f0;
$calendar-day: #bbbbbb;
$calendar-title: #fafafa;
#chat{
  width: 100%;
  .row, .col{
    padding:0;
    margin:0;
  }
  p{
    width:100%;
  }
  .row.chat-header{
    padding: 18px 8%;
    background: $calendar-border;
    .title{
      padding: 5px 0;
      text-align: left;
      font-size: 1.2rem;
    }
    .chat-search{
      padding: 5px 15px;
      background: #ffffff;
      border: 1px solid $calendar-day;
      border-radius: 50px;
      font-size: 0.8rem;
    }
  }
  .subtitle{
    padding: 17px 0;
    background: $calendar-title;
    border-top: 1px solid $calendar-border;
    border-bottom: 1px solid $calendar-border;
    text-align: center;
    font-size: 1rem;
  }
  .chat-room{
    overflow: scroll;
  }
  .my-chat{
    .chat-room{
      height: 30vh;
    }
  }
  .fav-chat{
    .chat-room{
      height: 20vh;
    }
  }
    
 
}
</style>