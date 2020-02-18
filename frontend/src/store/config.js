export default {
    AUTH_HOST : 'http://10.99.13.27:7000',
    RECRUIT_HOST : 'http://10.99.13.132:7000',
    RESUME_HOST : 'http://10.99.13.27:7000',
    CHAT_HOST : '10.99.13.17:8081',

    access_token : sessionStorage.getItem('accessToken'),
    refresh_token : sessionStorage.getItem('refreshToken')
}