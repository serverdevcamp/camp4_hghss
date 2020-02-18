<template>
  <div></div>
</template>

<script>
  import axios from 'axios';

  export default {
    name: "OAuth2Page",
    async created() {
      const response = await axios.get('http://15.165.146.7:7000/users/oauth2/naver?code='+this.$route.query.code);

      response.data.success?
          this.$store.commit('setUserInfo', response.data.data)
          :alert(response.data.message);

      window.close();
    }
  }
</script>

<style scoped>

</style>