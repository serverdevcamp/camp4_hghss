import config from "./config";
import axios from 'axios'
import Vue from 'vue';

export default {
  state: {
    ChatState: false, // 채팅방 열림 여부
    Rank: {},
  },
  mutations: {
    setRank(state, payload){
      Vue.set(state.Rank, payload.target, payload.data)
    }
  },
  actions: {
    rankAPI(context, payload) {
      axios({
        method: 'get',
        url: config.RECRUIT_HOST + '/ranking/' + payload.path,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
        }
      }).then(response => {
        if (response.data.status == 200) {
          context.commit('setRank', { target: payload.path, data: response.data.data });
        }

      })
    },
  },
  getters: {
    getChatState(state) {
      return state.ChatState
    },
    getRank(state) {
      return state.Rank
    }
  }
}