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
        url: config.RECRUIT_HOST + '/recruits',
        params: payload,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
        }
      }).then(response => {
        if (response.data.status == 200) {
          context.commit('setCalendar', response.data.data);
          return true
        }
        return false
      }).catch(() =>{
        // 연결이 안되있는 경우
        return []
      })
    },
    likeToggle(context, payload){     
      // var access_token = localStorage.getItem('accessToken')
      var access_token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5YmNoYWUwODAxQGdtYWlsLmNvbSIsInVzZXJJZCI6MSwiZW1haWwiOiJ5YmNoYWUwODAxQGdtYWlsLmNvbSIsIm5pY2tuYW1lIjoidGVzdCIsInJvbGVzIjpbIlVTRVIiXSwidG9rZW5UeXBlIjoiUkVGUkVTSF9UT0tFTiIsImV4cCI6MTU4MTM5NjYzMX0.l5whELZ4WcE3tAqPPLoQAJfzr4HmdRj6d0bUOUqTkNw'
      var method = ['post', 'delete']
      var path = ['like', 'unlike']
      
      return axios({
        method: method[payload.action],
        url: config.RECRUIT_HOST + '/recruits/detail/'+payload.recruit_id+'/'+ path[payload.action],
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer '+ access_token,
        }
      }).then(response => {
        if (response.data.status == 200) {
          if(payload.action == 0) return true
          return false
        }
        // 실패시, 변화 없음
        if(payload.action == 0) return false
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