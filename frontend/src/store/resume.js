import config from "./config";
import axios from 'axios'

export default {
  state: {
    resume: {}
  },
  mutations: {
    setResume(state, payload) {
      state.resume = {
        1: { 1: [], 2: [], 3: [] },
        2: { 1: [], 2: [] },
        3: { 1: [], 2: [] },
        4: { 1: [], 2: [] },
        5: { 1: [], 2: [] },
      }
      payload.forEach(el => {
        state.resume[el.resumeCol][el.resumeRow].push(el)
      });
    },
  },
  actions: {
    // 추가
    addAnswer(context, payload) {
      var access_token = localStorage.getItem('accessToken')
      return axios({
        method: 'post',
        url: config.RESUME_HOST + '/resumes/answer/add/' + payload.resume_id,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer ' + access_token,
        },
      }).then(async response => {
        if (response.data.status == 200) {
          return response.data.data
        }
        if (response.data.status == 402) {
          let refresh = await context.dispatch('refreshToken')
          if (refresh == true) {
            return context.dispatch('addResume', payload)
          }
        }
      })
    },
    // 삭제
    deleteAnswer(context, payload) {
      var access_token = localStorage.getItem('accessToken')
      axios({
        method: 'delete',
        url: config.RESUME_HOST + '/resumes/answer/delete/' + payload.answer_id,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer ' + access_token,
        },
      }).then(async response => {
        if (response.data.status == 200) {
          console.log("삭제완료!")
        }
        if (response.data.status == 402) {
          context.dispatch('refreshToken', { funcName: 'deleteAnswer', param: payload })
        }
      })
    },
    // 저장
    saveResume(context, payload) {
      var access_token = localStorage.getItem('accessToken')
      axios({
        method: 'put',
        url: config.RESUME_HOST + '/resumes/save/' + payload.resume_id,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer ' + access_token,
        },
        data: payload.body
      }).then(response => {
        if (response.data.status == 200) {
          console.log("저장완료!")
        }
        if (response.data.status == 402) {
          context.dispatch('refreshToken', { funcName: 'saveResume', param: payload })
        }
      })
    },
    // 상세조회
    detailResume(context, payload) {
      var access_token = localStorage.getItem('accessToken')
      return axios({
        method: "get",
        url: config.RESUME_HOST + "/resumes/" + payload.resume_id,
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
          'Authorization': 'Bearer ' + access_token,
        }
      }).then(async response => {
        if (response.data.status == 200) {
          return response.data.data
        }
        if (response.data.status == 402) {
          let refresh = await context.dispatch('refreshToken')
          if (refresh == true) {
            // 리프레시 완료시 다시 호출
            return context.dispatch('detailResume', payload)
          }
        }
        // 이경우는 로그인이 안된거거나 해당하는 자소서 없음
        return false
      });
    },
    // 만들기
    createResume(context, payload) {
      var access_token = localStorage.getItem('accessToken')
      axios({
        method: 'post',
        url: config.RESUME_HOST + '/resumes/create/' + payload.position_id,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer ' + access_token,
        },
        data: payload.body
      }).then(response => {
        if (response.data.status == 200) {
          context.dispatch('resumeListAPI')
        }
        if (response.data.status == 402) {
          context.dispatch('refreshToken', { funcName: 'createResume', param: payload })
        }
      })
    },
    // 삭제
    deleteResume(context, payload) {
      var access_token = localStorage.getItem('accessToken')
      axios({
        method: 'delete',
        url: config.RESUME_HOST + '/resumes/delete/' + payload.resume_id,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer ' + access_token,
        },
      }).then(response => {
        if (response.data.status == 200) {
          // 삭제된 리스트 다시 부르기
          context.dispatch('resumeListAPI')
        }
        if (response.data.status == 402) {
          context.dispatch('refreshToken', { funcName: 'deleteResume', param: payload })
        }
      })
    },
    // 위치 변경
    moveResume(context, payload) {
      var access_token = localStorage.getItem('accessToken')
      axios({
        method: 'put',
        url: config.RESUME_HOST + '/resumes/' + payload.resume_id,
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer ' + access_token,
        },
        params: {
          col: payload.col,
          row: payload.row
        }
      }).then(response => {
        if (response.data.status == 200) {
          // 변경된 리스트 다시 부르기
          // context.dispatch('resumeListAPI')
        }
        if (response.data.status == 402) {
          context.dispatch('refreshToken', { funcName: 'moveResume', param: payload })
        }
      })
    },
    // 목록
    resumeListAPI(context) {
      var access_token = localStorage.getItem('accessToken')
      axios({
        method: 'get',
        url: config.RESUME_HOST + '/resumes/list',
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          'Authorization': 'Bearer ' + access_token,
        }
      }).then(response => {
        if (response.data.status == 200) {
          context.commit('setResume', response.data.data)
        }
        else if (response.data.status == 402) {
          context.dispatch('refreshToken', { funcName: 'resumeListAPI', param: '' })
        }
      })
    }
  },
  getters: {
    getResume(state) {
      return state.resume
    }
  }
}