import axios from 'axios'

export default {
  state: {
    user: {
      email: "",
      nickname: ""
    }
  },
  getters: {
    getUser(state) {
      return state.user;
    }
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

      if (response.data.success) {
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

        commit('setUserInfo', {email: email, nickname: nickname});
        alert(response.data.message);
        return true;
      }

      alert(response.data.message);
      return false;

    },
    async signup(context, payload) {
      const response = await axios.post('http://localhost:8000/users/signup', payload);
      alert(response.data.message);
      return (response.data.success === "true")? true : false;
    },
    async signout({ commit }) {
      const response = await axios.get('http://localhost:8000/users/signout', {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('refreshToken')
        }
      });

      if (response.data.success) commit('signout');
      alert(response.data.message);
    },
    async refreshToken({ commit }) {
      let refreshToken = localStorage.getItem('refreshToken');
      const response = await axios.get('http://localhost:8000/users/refresh', {
        headers: {
          Authorization: 'Bearer ' + refreshToken
        }
      });

      if (response.data.success) {

        let email = response.data.data.email;
        let nickname = response.data.data.nickname;

        localStorage.setItem('accessToken', response.data.data.accessToken);
        localStorage.setItem('refreshToken', response.data.data.refreshToken);

        commit('setUserInfo', { email: email, nickname: nickname });

      } else {
        console.error(response.data.message);
        alert('세션이 만료되었습니다.');
      }

    }
  }
}