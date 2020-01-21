import axios from 'axios'

export default {
    async signin({commit}, payload) {
        const response = await axios.post('http://localhost:8000/users/signin', payload);
        console.log(response);

        let email = response.data.data.email;
        let nickname = response.data.data.nickname;

        let accessToken = response.data.data.accessToken;
        let refreshToken = response.data.data.refreshToken;

        if(typeof(Storage) !== 'undefined') {
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
        } else {
            console.error('로컬스토리지를 사용 할 수 없습니다.');
        }

        commit('signin', {email: email, nickname: nickname});
    },
    async signup(context, payload) {
        const response = await axios.post('http://localhost:8000/users/signup', payload);
        console.log(response);
    },
    async signout({commit}) {
        const response = await axios.get('http://localhost:8000/users/signout', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('refreshToken')
            }
        });
        console.log(response);
        commit('signout');
    }
}