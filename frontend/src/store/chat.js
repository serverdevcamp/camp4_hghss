import config from "./config";
// import axios from 'axios'

export default {
  state: {
    // 채팅 목록
    chatList: {
      // 이런구조로 넣을 것임
      // 0: {
      //   state: false,
      //   socket: '',
      //   chat: []
      // }
    }
  },
  mutations: {
    setSocket(state, payload) {
      state.chatList[payload.company_id] = {
        'state': payload.socket.readyState,
        'socket': payload.socket,
        'chat': [],
      }
      // 소켓 생성이 완료되면 리스너를 달아주자
      payload.socket.onmessage = function (event) {
        var chat_list = JSON.parse(event.data).chat_list
        chat_list.forEach(chat =>{
          if (typeof chat == 'string') chat = JSON.parse(chat)
          Object.assign(state.chatList[payload.company_id].chat, chat)
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
    getSocket: state => company_id =>{
      return state.chatList[company_id].socket
    }
  }
}