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
      })
    }
  },
  getters: {
    getCalendar(state) {
      return state.calendar
    }
  }
}