<template>
  <div id="chat">
    <v-row class="chat-header">
      <v-col cols="3" class="title point-font">채팅</v-col>
      <v-col cols="3">
        <input type="text" class="chat-search" placeholder="기업명을 입력하세요." v-model="search" />
      </v-col>
    </v-row>
    <v-row class="my-chat">
      <v-col>
        <v-row>
          <p class="subtitle point-font">내 채팅</p>
        </v-row>
        <v-row class="chat-room">
          <div
            class="chat-item"
            v-for="(chat, company_id) in getMyChat"
            v-bind:key="company_id"
            v-if="chat.company.indexOf(search) >= 0"
            @click="openRoom(company_id, chat.company)"
          >
            <div class="company-logo">
              <img :src="chat.logo_url" :alt="chat.company" />
            </div>
            <div class="company-info">
              <p class="company-name">
                {{ chat.company }}
                <span class="user_cnt">({{getChatList(company_id).user_cnt}}명)</span>
              </p>
              <p
                class="last-chat"
                v-if="getChatList(company_id).chat && getChatList(company_id).chat.length >= 1"
              >{{ getChatList(company_id).chat[getChatList(company_id).chat.length-1].message }}</p>
              <p class="last-chat" v-else>이전 채팅이 없습니다.</p>
            </div>
            <div v-if="company_id != 0" class="remove-btn">
              <font-awesome-icon icon="times" @click.stop="closeChat(company_id)"/>
            </div>
          </div>
        </v-row>
      </v-col>
    </v-row>
    <v-row class="fav-chat">
      <v-col>
        <v-row>
          <p class="subtitle point-font">즐겨찾기/작성한 기업</p>
        </v-row>
        <v-row class="chat-room">
          <div
            class="chat-item"
            v-for="(chat, company_id) in getFavChat"
            v-bind:key="company_id"
            @click="openRoom(company_id, chat.company)"
          >
            <div class="company-logo">
              <img :src="chat.logo_url" :alt="chat.company" />
            </div>
            <div class="company-info">
              <p class="company-name">
                {{ chat.company }}
                <span class="user_cnt">({{getChatList(company_id).user_cnt}}명)</span>
              </p>
              <p
                class="last-chat"
                v-if="getChatList(company_id).chat && getChatList(company_id).chat.length >= 1"
              >{{ getChatList(company_id).chat[getChatList(company_id).chat.length-1].message }}</p>
              <p class="last-chat" v-else>이전 채팅이 없습니다.</p>
            </div>
          </div>
        </v-row>
      </v-col>
    </v-row>
    <v-row class="hot-chat">
      <v-col>
        <v-row>
          <p class="subtitle point-font">인기 채팅</p>
        </v-row>
        <v-row class="chat-room">
          <div
            class="chat-item"
            v-for="(chat, company_id) in getHotChat"
            v-bind:key="company_id"
            @click="openRoom(company_id, chat.company)"
          >
            <div class="company-logo">
              <img :src="chat.logo_url" :alt="chat.company" />
            </div>
            <div class="company-info">
              <p class="company-name">
                {{ chat.company }}
                <span class="user_cnt">({{getChatList(company_id).user_cnt || 0}}명)</span>
              </p>
              <p
                class="last-chat"
                v-if="getChatList(company_id).chat && getChatList(company_id).chat.length >= 1"
              >{{ getChatList(company_id).chat[getChatList(company_id).chat.length-1].message }}</p>
              <p class="last-chat" v-else>이전 채팅이 없습니다.</p>
            </div>
          </div>
        </v-row>
      </v-col>
    </v-row>
    <!-- 이거슨 채팅방 -->
    <Room v-if="getRoomCompanyInfo.is_open"/>
  </div>
</template>

<script>
import Room from "./Room";
import { mapGetters, mapActions, mapMutations } from "vuex";
export default {
  components: {
    Room
  },
  data: () => ({
    company_id : 0,
    search: '',
  }),
  computed: {
    ...mapGetters([
      "getChatList",
      "getSocket",
      "getMyChat",
      "getFavChat",
      "getHotChat",
      "getRoomCompanyInfo"
    ])
  },
  methods: {
    ...mapActions(["openSocket","userChatAPI","removeChat"]),
    ...mapMutations(["setRoomState"]),
    openRoom(company_id, company){
      this.company_id = company_id
      this.setRoomState({is_open : true, company_id : company_id, company: company})
    },
    closeChat(company_id){
      this.removeChat({ company_id : company_id})
    }
  },
  created() {
    this.userChatAPI()
  }
};
</script>

<style lang="scss">
$calendar-border: #f0f0f0;
$calendar-day: #bbbbbb;
$calendar-title: #fafafa;
#chat {
  width: 100%;
  .row,
  .col {
    padding: 0;
    margin: 0;
  }
  p {
    width: 100%;
  }
  .row.chat-header {
    padding: 18px 8%;
    background: $calendar-border;
    .title {
      padding: 5px 0;
      text-align: left;
      font-size: 1.2rem;
    }
    .chat-search {
      padding: 5px 15px;
      background: #ffffff;
      border: 1px solid $calendar-day;
      border-radius: 50px;
      font-size: 0.8rem;
    }
  }
  .subtitle {
    padding: 17px 0;
    background: $calendar-title;
    border-top: 1px solid $calendar-border;
    border-bottom: 1px solid $calendar-border;
    text-align: center;
    font-size: 1rem;
  }
  .chat-room {
    display: inline-block;
    overflow: scroll;
    width: 100%;
    .chat-item {
      cursor: pointer;
      width: 100%;
      height: 60px;
      overflow: hidden;
      &:hover {
        background: #f4f4f4;
        .remove-btn{
          width: 30px;
          height: 40px;
          padding-top: 20px;
          // padding-right: 3px;
          display: inline-block;
          path{
            color: #999999;
          }
          &:hover path{
            color: #131417;
          }
        }
      }
      .company-logo,
      .company-info {
        float: left;
        display: inline-block;
      }
      .company-logo {
        position: relative;
        overflow: hidden;
        width: 36px;
        height: 36px;
        margin: 12px;
        border-radius: 50%;
        border: 0.4px solid #ddd;
        background: #ffffff;
        img {
          position: absolute;
          max-width: 100%;
          max-height: 100%;
          width: auto;
          height: auto;
          margin: auto;
          top: 0;
          bottom: 0;
          left: 0;
          right: 0;
        }
      }
      .company-info {
        width: calc(100% - 90px);
        height: 40px;
        margin: 10px 0;
        padding-right: 10px;
        *zoom: 1;
        .company-name {
          margin: 4px 0;
          font-size: 0.8rem;
          font-weight: 600;
          letter-spacing: 0.03rem;
          .user_cnt {
            font-size: 0.75rem;
            font-weight: 500;
          }
        }
        .last-chat {
          color: #999999;
          font-size: 0.73rem;
          letter-spacing: 0.03rem;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        &::after {
          content: "";
          display: block;
          clear: both;
        }
      }
      .remove-btn{
        display: none;
      }
    }
  }
  .my-chat {
    .chat-room {
      height: 30vh;
    }
  }
  .fav-chat {
    .chat-room {
      height: 20vh;
    }
  }
}
</style>