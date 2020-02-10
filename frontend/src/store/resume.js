import config from "./config";
import axios from 'axios'

export default {
  state: {
    resume: {
      1: { 1: [], 2: [], 3: []},
      2: { 1: [], 2: [] },
      3: { 1: [], 2: [] },
      4: { 1: [], 2: [] },
      5: { 1: [], 2: [] },
    }
  },
  mutations: {
    setResume(state, payload){
      payload.forEach( el => {
        state.resume[el.resumeCol][el.resumeRow].push(el)
      });
    }
  },
  actions: {
    resumeListAPI(context){
      var access_token = localStorage.getItem('accessToken')
      context.state.resume = {
        1: { 1: [], 2: [], 3: []},
        2: { 1: [], 2: [] },
        3: { 1: [], 2: [] },
        4: { 1: [], 2: [] },
        5: { 1: [], 2: [] },
      }
      axios({
        method: 'get',
        url: config.RESUME_HOST  + '/resumes/list',
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer '+ access_token,
        }
      }).then(response => {
        if(response.data.status == 200){
          context.commit('setResume', response.data.data)
        }
        else if(response.data.status == 402){
          context.dispatch('refreshToken',{funcName: 'resumeListAPI', param: ''})
        }
      })
    }
  },
  getters: {
    getResume(state){
      return state.resume
    }
  }
}