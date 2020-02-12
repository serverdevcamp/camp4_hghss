import config from "./config";
import axios from 'axios'

export default {
  state: {
    calendar: [],
    calendarLike: [],
  },
  mutations: {
    setCalendar(state, payload) {
      state.calendar = payload
    },
    setCalendarLike(state, payload) {
      state.calendarLike = payload
    },
    setLikeOrUnlike(state, payload){
      if(payload.action == 0){
        state.calendarLike.push(payload.recruit_id)
      }else{
        state.calendarLike.splice(state.calendarLike.indexOf(payload.recruit_id), 1)
      }
    }
  },
  actions: {
    // Calendar 리스트 가져오기
    calendarLikeAPI(context, payload) {
      axios({
        method: 'get',
        url: config.RECRUIT_HOST + '/recruits/likeList',
        params: payload,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer ' + config.access_token,
        }
      }).then(response => {
        if (response.data.status == 200) {
          context.commit('setCalendarLike', response.data.data);
        }
        else if (response.data.status == 402) {
          context.dispatch('refreshToken', { funcName: 'calendarLikeAPI', param: payload })
        }
      })
    },
    calendarAPI(context, payload) {
      // 로그인했다면, 좋아요 리스트 가져오기
      if (config.access_token) {
        context.dispatch('calendarLikeAPI', payload)
      }
      return axios({
        method: 'get',
        url: config.RECRUIT_HOST + '/recruits/calendar/refactor',
        params: payload,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
        }
      }).then(response => {
        let status = response.data.status
        if (status == 200 || status == 401 || status == 402) {
          // TODO : 402 코드 발생시 로그아웃
          context.commit('setCalendar', response.data.data);
          return true
        }
        return []
      })
    },
    likeToggle(context, payload) {
      var method = ['post', 'delete']
      var path = ['like', 'unlike']

      return axios({
        method: method[payload.action],
        url: config.RECRUIT_HOST + '/recruits/' + path[payload.action] + '/' + payload.recruit_id,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer ' + config.access_token,
        }
      }).then(response => {
        if (response.data.status == 200) {
          if (payload.action == 0) {
            // 즐찾이 완료되면? 채팅리스트에 넣어두기
            return true
          }
          // 취소 완료시
          return false
        }
        // 실패시, 변화 없음
        if (payload.action == 0) {
          return false
        }
        return true
      }).catch(() => {
        if (payload.action == 0) return false
        return true
      })
    },
  },
  getters: {
    getCalendar(state) {
      return state.calendar
    },
    getCalendarLike(state) {
      return state.calendarLike
    }
  }
}