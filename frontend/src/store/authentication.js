import axios from 'axios'

export default {
  state: {
    user: {
      email: "",
      nickname: ""
    },
  },
  mutations: {
    setUserInfo(state, payload) {
      state.user.email = payload.email;
      state.user.nickname = payload.nickname;
    },
    signout(state) {
      state.user.email = "";
      state.user.nickname = "";

      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
    }
  },
  actions: {
    async signin({ commit }, payload) {
      const response = await axios.post('http://localhost:8000/users/signin', payload);
      // console.log(response);

      if (response.data.status !== 200) {
        console.error(response.data.message);
        return;
      }

      let email = response.data.data.email;
      let nickname = response.data.data.nickname;

      let accessToken = response.data.data.accessToken;
      let refreshToken = response.data.data.refreshToken;

      if (typeof (Storage) !== 'undefined') {
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);
      } else {
        console.error('로컬스토리지를 사용 할 수 없습니다.');
        return;
      }

      commit('setUserInfo', { email: email, nickname: nickname });
    },
    async signup(context, payload) {
      const response = await axios.post('http://localhost:8000/users/signup', payload);
      // console.log(response);

      if (response.data.status !== 200) {
        console.error(response.data.message);
        return;
      }
    },
    async signout({ commit }) {
      const response = await axios.get('http://localhost:8000/users/signout', {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('refreshToken')
        }
      });
      // console.log(response);

      if (response.data.status !== 200) {
        console.error(response.data.message);
        return;
      }

      commit('signout');
    },
    async setUserInfo({ commit }) {
      let refreshToken = '';

      if (typeof (Storage) !== 'undefined') {
        refreshToken = localStorage.getItem('refreshToken');
      } else {
        console.error('로컬스토리지를 사용 할 수 없습니다.');
        // TODO: 다시 로그인
        return;
      }

      const response = await axios.get('http://localhost:8000/users/refresh', {
        headers: {
          Authorization: 'Bearer ' + refreshToken
        }
      });
      // console.log(response);

      if (response.data.status !== 200) {
        console.error(response.data.message);
        return;
      }

      // TODO: 로그인 정보가 없는 경우 다시 로그인

      let email = response.data.data.email;
      let nickname = response.data.data.nickname;
      localStorage.setItem('accessToken', response.data.data.accessToken);
      localStorage.setItem('refreshToken', response.data.data.refreshToken);

      commit('setUserInfo', { email: email, nickname: nickname });
    }
  },
  getters: {
    getUser(state) {
      return state.user;
    },
  }
}