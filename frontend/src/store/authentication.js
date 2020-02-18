import axios from 'axios'
import router from '../router/index.js'
import config from './config'

export default {
  state: {
    user: {
      id: "",
      email: "",
      nickname: "",
      role: ""
    }
  },
  getters: {
    getUser(state) {
      return state.user;
    },
    is_user_login(state){
      return state.user.email ? true : false 
    }
  },
  mutations: {
    setUserInfo(state, payload) {
      state.user.id = payload.id;
      state.user.email = payload.email;
      state.user.nickname = payload.nickname;
      state.user.role = payload.role;

      sessionStorage.setItem('accessToken', payload.accessToken);
      sessionStorage.setItem('refreshToken', payload.refreshToken);

      sessionStorage.setItem('id', payload.id);
      sessionStorage.setItem('email', payload.email);
      sessionStorage.setItem('nickname', payload.nickname);
      sessionStorage.setItem('role', payload.role);

    },
    signout(state) {
      state.user.id = "";
      state.user.email = "";
      state.user.nickname = "";
      state.user.role = "";

      sessionStorage.removeItem('accessToken');
      sessionStorage.removeItem('refreshToken');

      sessionStorage.removeItem('nickname');
      sessionStorage.removeItem('email');
      sessionStorage.removeItem('role');
      sessionStorage.removeItem('id');
    }
  },
  actions: {
    async signin({ commit}, payload) {

      const response = await axios.post(config.AUTH_HOST + '/users/signin', payload, {
        headers: {
          'Access-Control-Allow-Origin': '*'
        }
      });

      if(response.data.success) commit('setUserInfo', response.data.data);
      alert(response.data.message);

      return response.data.success;
    },
    async signup(context, payload) {
      const response = await axios.post(config.AUTH_HOST + '/users/signup', payload);
      alert(response.data.message);
      return response.data.success;
    },
    async signout({ commit }) {
      const response = await axios.get(config.AUTH_HOST + '/users/signout', {
        headers: {
          Authorization: 'Bearer ' + sessionStorage.getItem('refreshToken')
        }
      });

      if (response.data.success) commit('signout');
      alert(response.data.message);

      // 현재페이지 리다이렉트, 유저 맞춤 정보 삭제
      router.go()
    },
    async refreshToken({ commit, dispatch }, payload) {
      let refreshToken = config.refresh_token

      const response = await axios.get(config.AUTH_HOST + '/users/refresh', {
        headers: {
          Authorization: 'Bearer ' + refreshToken
        }
      });

      if (response.data.success) {
        sessionStorage.setItem('accessToken', response.data.data.accessToken);
        sessionStorage.setItem('refreshToken', response.data.data.refreshToken);
        commit('setUserInfo', response.data.data);

        /**** 함수 재실행 ****/
        //context.dispatch('refreshToken',{funcName: '실행시키고싶은 함수이름', param: '인자'})
        if(payload != null) dispatch(payload.funcName, payload.param);

        return true;
      } else {
        alert("로그인 후 사용해주세요.");
        router.push('/')  // 세션만료시 홈으로 리다이렉트

        sessionStorage.removeItem('refreshToken');
        sessionStorage.removeItem('accessToken');

        sessionStorage.removeItem('nickname');
        sessionStorage.removeItem('email');
        sessionStorage.removeItem('role');
        sessionStorage.removeItem('id');

        return false;
      }
      

    }
  }
}