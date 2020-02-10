import config from "./config"
import Vue from "vue"
import axios from "axios"

export default {
  state: {
    chat_room: {
      is_open : false,
      company_id : 0,
      company: '전체'
    },
    // 채팅 목록 => 로그인할때 가져온다.
    myChat: {
      0: {
        company: '전체',
        logo_url: 'https://jasoseol.s3-ap-northeast-2.amazonaws.com/chats/images/000/000/001/thumb/%E1%84%80%E1%85%B3%E1%84%85%E1%85%AE%E1%86%B8_271_2x_%281%29.png?1533027704'
      }
    },
    favChat: {},
    hotChat: {},
    // 채팅 내용
    chat_id:[],
    chatList: {
      // 이런 구조
      // 0: {
      //   state: false,
      //   socket: '',
      //   user_cnt: 1,
      //   chat: [] // 채팅내용저장
      // }
    }

  },
  mutations: {
    setUserChat(state, payload) {
      var userChat = ['myChat', 'favChat', 'hotChat']
      if (payload.target == 0) {
        // 0 일때 전체 리스트 같이 넣기
        var all = state.myChat[0]
        payload.data[0] = all
      }
      state[userChat[payload.target]] = payload.data
      // 전체 채팅방 중복 제거
      for(let company_id in state[userChat[payload.target]]){
        if(!state.chat_id.includes(company_id)){
          state.chat_id.push(company_id)
        }
      }
    },
    setRoomState(state, payload) {
      state.chat_room.is_open= payload.is_open
      if(payload.company_id){
        state.chat_room.company_id = payload.company_id
        state.chat_room.company = payload.company
      }
    },
    setSocket(state, payload) {
      Vue.set(state.chatList, payload.company_id, {
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

        if (type === 1) {
          // 현재 소켓을 열고있는 유저 수
          state.chatList[payload.company_id].user_cnt = socket_data.user_cnt
        }

        chat_list.forEach(chat => {
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
        })
      }
    },
    // 채팅 구독
    addChat(context, payload){
      // 구독체크
      if(!context.state.myChat.hasOwnProperty(payload.company_id)){
        // 구독!
        context.state.myChat[payload.company_id] = {
          company: payload.company,
          logo_url: payload.logo_url
        }
        axios({
          method: 'post',
          url: config.RECRUIT_HOST + '/chats/detail/'+payload.company_id+'/like',
          headers: {
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Bearer ' + config.access_token,
          }
        }).then(response => {
          if(response.data.status == 402){
            // 토큰만료시 다시 발급
            context.dispatch('refreshToken',{funcName: 'addChat', param: payload})
          }
        })
      }
      // 소켓이 열려있는지 확인
      if(!context.state.chat_id.includes(''+payload.company_id)){
        // 소켓 열기
        context.dispatch('openSocket',payload.company_id)
      }
      // 채팅 열기
      context.commit('setRoomState',{
        is_open : true,
        company_id : payload.company_id,
        company : payload.company,
      })
      // 채팅방 열기
      context.commit('setChatState', true)
      return true
    },
    async userChatAPI(context) {
      var chatURL = ['/my', '/favorite', '/hot']
      for (let i = 0; i < 3; i++) {
        await axios({
          method: 'get',
          url: config.RECRUIT_HOST + '/chats' + chatURL[i],
          headers: {
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Bearer ' + config.access_token,
          }
        }).then(response => {
          if (response.data.status == 200) {
            context.commit('setUserChat', {
              target: i,
              data: response.data.data
            })
          }else{
            context.commit('setUserChat', {
              target: i,
              data: {}
            })
          }
        })
        .then(()=>{
          if(i == 2){
            this.state.chat.chat_id.forEach(company_id=>{
              // 소켓 생성
              context.dispatch('openSocket',company_id)
            })
          }
        })     
      }
    }
  },
  getters: {
    getRoomCompanyInfo(state){
      return state.chat_room
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
    getSocket: state => company_id => {
      return state.chatList[company_id].socket
    },
    getChatList: state => company_id => {
      if (state.chatList.hasOwnProperty(company_id)) return state.chatList[company_id]
      return []
    },
  }
}