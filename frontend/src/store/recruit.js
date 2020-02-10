import config from "./config";
import axios from 'axios'

export default {
  state: {
    calendar: [],
  },
  mutations: {
    setCalendar(state, payload) {
      state.calendar = payload
    }
  },
  actions: {
    calendarAPI(context, payload) {
      return axios({
        method: 'get',
        url: config.RECRUIT_HOST + '/recruits/calendar',
        params: payload,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer '+ config.access_token,
        }
      }).then(response => {
        let status = response.data.status
        if (status == 200 || status == 401 || status == 402) {
          // TODO : 402 코드 발생시 로그아웃
          context.commit('setCalendar', response.data.data);
          return true
        }
        return []
      }).catch(() =>{
        // 연결이 안되있는 경우
        return []
      })
    },
    likeToggle(context, payload){     
      var method = ['post', 'delete']
      var path = ['like', 'unlike']
      
      return axios({
        method: method[payload.action],
        url: config.RECRUIT_HOST + '/recruits/'+path[payload.action]+'/'+ payload.recruit_id,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer '+ config.access_token,
        }
      }).then(response => {
        if (response.data.status == 200) {
          if(payload.action == 0) {
            // 즐찾이 완료되면? 채팅리스트에 넣어두기
            return true
          }
          return false
        }
        // 실패시, 변화 없음
        if(payload.action == 0){
          console.log(response.data)
          return false
        }
        console.log(response.data)
        return true
      }).catch(() =>{
        if(payload.action == 0) return false
        return true
      })
    },
  },
  getters: {
    getCalendar(state) {
      return state.calendar
    }
  }
}