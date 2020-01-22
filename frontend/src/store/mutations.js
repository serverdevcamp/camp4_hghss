export default {
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
}