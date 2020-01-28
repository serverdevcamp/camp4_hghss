<template>
  <div id="room">
    <v-row @click="setRoomState(false)" class="room-close point-font">
      <font-awesome-icon icon="arrow-left" />
      {{getMyChat[company_id].company}}
      <span
        class="user_cnt"
      >({{getChatList(company_id).user_cnt}}명)</span>
    </v-row>
    <v-row class="chat-room">
      <div class="chat" v-for="chat in getChatList(company_id).chat" :key="chat">
        <div class="nickname point-font">{{chat.nickname}}</div>
        <div class="content">
          <div class="message">
            {{chat.message}}
            <div class="time">{{chat.time}}</div>
          </div>
        </div>
      </div>
    </v-row>
    <v-row class="send-section">
      <div v-if="getUser.nickname" class="send-box">
        <div class="nickname point-font">{{getUser.nickname}}</div>
        <textarea
          class="message"
          placeholder="채팅 메시지를 입력하세요."
          v-model="message"
          v-on:keyup.enter="sendMessage"
        ></textarea>
      </div>
      <!-- 내일 추가 -->
      <div v-else @click="alert('로그인해라')" class="send-box">
        <div class="nickname">비회원</div>
        <div class="message">채팅에 참여하려면 로그인을 해주세요.</div>
      </div>
    </v-row>
  </div>
</template>
<script>
import moment from "moment";
import { mapGetters, mapMutations } from "vuex";
export default {
  props: ["company_id"],
  data: () => ({
    message: ""
  }),
  computed: {
    ...mapGetters(["getMyChat", "getChatList", "getUser", "getSocket"])
  },
  methods: {
    ...mapMutations(["setRoomState"]),
    // 메시지 보내기
    sendMessage: function() {
      this.message = this.message.replace(/(^\s*)|(\s*$)/g, "");
      if (this.message !== "") {
        var socket = this.getSocket(this.company_id);
        var date_time = moment()
          .format()
          .split("T");
        var msg = {
          message: this.message,
          nickname: this.getUser.nickname,
          date: date_time[0],
          time: date_time[1].substring(0, 5)
        };
        socket.send(JSON.stringify(msg));
        // 메시지 초기화
        this.message = "";
      }
    }
  }
};
</script>
<style lang="scss">
$calendar-border: #f0f0f0;
$calendar-day: #bbbbbb;
$calendar-title: #fafafa;
#room {
  position: absolute;
  background: #ffffff;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  .row.room-close {
    cursor: pointer;
    height: 50px;
    padding: 16px 8%;
    background: $calendar-border;
    font-size: 18px;
    svg {
      margin-right: 10px;
    }
    .user_cnt {
      margin-left: 5px;
      font-size: 0.8rem;
      font-weight: 600;
    }
  }
  .row.chat-room {
    overflow: scroll;
    height: calc(100vh - 100px - 150px);
    padding: 20px 12px;
    .chat {
      display: block;
      width: 100%;
      margin: 10px 0;
      .nickname {
        color: #7d7d7d;
        letter-spacing: 0.1rem;
        font-size: 0.85rem;
      }
      .content {
        overflow: hidden;
        .message,
        .time {
          display: inline-block;
        }
        .message {
          position: relative;
          margin: 5px 10px 0 0;
          max-width: 200px;
          min-width: 150px;
          width: auto;
          padding: 8px;
          background: #f0f0f0;
          border-radius: 5px;
          font-size: 0.75rem;
          line-height: 1.1rem;
          word-break: keep-all;
          .time {
            position: absolute;
            right: -40px;
            font-size: 0.7rem;
          }
        }
      }
    }
  }
  .row.send-section {
    height: 150px;
    padding: 20px 12px;
    background: #fafafa;
    .send-box {
      width: 100%;
      .nickname {
        letter-spacing: 0.1rem;
        font-size: 0.9rem;
      }
      .message {
        resize: none;
        overflow: hidden;
        width: 100%;
        height: 80px;
        margin-top: 10px;
        padding: 8px;
        border-radius: 5px;
        background: #f0f0f0;
        font-size: 0.75rem;
        line-height: 1.1rem;
      }
    }
  }
}
</style>