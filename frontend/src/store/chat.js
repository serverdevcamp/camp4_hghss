import config from "./config"
import Vue from "vue"

export default {
  state: {
    room: false,
    // 채팅 목록 => 로그인할때 가져온다.
    myChat : {
      0 : {
        company : '전체',
        logo_url : 'https://jasoseol.s3-ap-northeast-2.amazonaws.com/chats/images/000/000/001/thumb/%E1%84%80%E1%85%B3%E1%84%85%E1%85%AE%E1%86%B8_271_2x_%281%29.png?1533027704'
      }
    },
    favChat : {},
    hotChat : {},
    // 채팅 내용
    chatList: {
      // 이런구조로 넣을 것임
      // 0: {
      //   state: false,
      //   socket: '',
      //   user_cnt: 1,
      //   chat: [] // 채팅내용저장
      // }
    }
    
  },
  mutations: {
    setRoomState(state,payload){
      state.room = payload
    },
    setSocket(state, payload) {
      Vue.set(state.chatList,payload.company_id,{
        'state': payload.socket.readyState,
        'socket': payload.socket,
        'user_cnt': 1,
        'chat': [],
      })
      // 소켓 리스너
      payload.socket.onmessage = function (event) {
        var socket_data = JSON.parse(event.data)
        var type = socket_data.type
        var chat_list = socket_data.chat_list
        
        if(type === 1){
          // 현재 소켓을 열고있는 유저 수
          state.chatList[payload.company_id].user_cnt = socket_data.user_cnt
        }

        chat_list.forEach(chat =>{
          if (type === 1) chat = JSON.parse(chat)
          
          let chat_list2 = state.chatList[payload.company_id].chat
          chat_list2.push(chat)
          Object.assign(state.chatList[payload.company_id].chat, chat_list2)
        })
        
      }
    }
  },
  actions: {
    openSocket(context, company_id) {
      var socket = new WebSocket('ws://' + config.CHAT_HOST + '/chat/' + company_id + '/');
      // 소켓이 열린 뒤에 실행
      socket.onopen = function () {
        context.commit('setSocket', {
          'company_id': company_id,
          'socket': socket,
        });
      }
    }
  },
  getters: {
    getRoomState(state){
      return state.room
    },
    getMyChat(state) {
      return state.myChat
    },
    getFavChat(state) {
      return state.favChat
    },
    getHotChat(state) {
      return state.hotChat
    },
    getSocket: state => company_id =>{
      return state.chatList[company_id].socket
    },
    getChatList: state => company_id =>{
      if(state.chatList.hasOwnProperty(company_id)) return state.chatList[company_id]
      return []
    },
  }
}