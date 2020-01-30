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
    }
  },
  mutations: {
    setUserInfo(state, payload) {
      state.user.id = payload.id;
      state.user.email = payload.email;
      state.user.nickname = payload.nickname;
      state.user.role = payload.role;

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
    }
  },
  actions: {
    async signin({ commit }, payload) {

      const response = await axios.post(config.AUTH_HOST + '/users/signin', payload);

      if (!response.data.success) {
        alert(response.data.message);
        return false;
      }

      if (typeof (Storage) !== 'undefined') {
        localStorage.setItem('accessToken', response.data.data.accessToken);
        localStorage.setItem('refreshToken', response.data.data.refreshToken);
      } else {
        console.error('로컬스토리지를 사용 할 수 없습니다.');
        return;
      }

      commit('setUserInfo', response.data.data);
      alert(response.data.message);

      return true;
    },
    async signup(context, payload) {
      const response = await axios.post(config.AUTH_HOST + '/users/signup', payload);
      alert(response.data.message);
      return response.data.success;
    },
    async signout({ commit }) {
      const response = await axios.get(config.AUTH_HOST + '/users/signout', {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('refreshToken')
        }
      });

      if (response.data.success) alert(response.data.message);

      commit('signout');

      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');

      sessionStorage.removeItem('nickname');
      sessionStorage.removeItem('email');
      sessionStorage.removeItem('role');
      sessionStorage.removeItem('id');

      router.push('/');
    },
    async refreshToken({ commit }) {
      let refreshToken = localStorage.getItem('refreshToken');
      const response = await axios.get(config.AUTH_HOST + '/users/refresh', {
        headers: {
          Authorization: 'Bearer ' + refreshToken
        }
      });

      if (response.data.success) {
        localStorage.setItem('accessToken', response.data.data.accessToken);
        localStorage.setItem('refreshToken', response.data.data.refreshToken);
        commit('setUserInfo', response.data.data);
        return true;
      } else {
        console.error(response.data.message);
        alert('세션이 만료되었습니다.');

        localStorage.removeItem('refreshToken');
        localStorage.removeItem('accessToken');

        sessionStorage.removeItem('nickname');
        sessionStorage.removeItem('email');
        sessionStorage.removeItem('role');
        sessionStorage.removeItem('id');
        return false;
      }

    }
  }
}