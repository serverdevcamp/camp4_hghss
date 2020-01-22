<template>
  <section id="changePasswd">
    <div class="passwd-section">
      <v-row>
        <p class="title point-font">비밀번호 찾기</p>
      </v-row>
      <v-row>
        <input type="password" placeholder="비밀번호 입력" class="input-passwd" v-model="passwd" />
        <input type="password" placeholder="비밀번호 확인" class="input-passwd" v-model="passwd2" />
      </v-row>
      <v-row>
        <button class="point-font passwd-reset-btn" @click="changePasswd()">비밀번호 재설정</button>
      </v-row>
    </div>
  </section>
</template>
<script>
export default {
  data: () => ({
    passwd: "",
    passwd2: ""
  }),
  methods: {
    changePasswd() {
      if(this.passwd == this.passwd2 && this.passwd != ''){
        // 비밀번호 재설정 요청보내기
        var key = this.$route.query.key
        console.log(key)
        // 성공했으면 홈으로 보내기
        alert("비밀번호가 변경되었습니다.")
        this.$router.push('/')
      }
    }
  }
};
</script>
<style lang="scss">
$calendar-border: #f0f0f0;
$drop-border: #bbbbbb;
$drop-box: #dddee0;
#changePasswd {
  height: calc(100vh - 50px);
  overflow: hidden;
  background: $calendar-border;
  text-align: center;
  .row,
  .col {
    margin: 0;
    padding: 0;
  }
  .passwd-section {
    margin-top: 25vh;
    display: inline-block;
    min-width: 300px;
    .row {
      padding: 5px 0;
      width: 100%;
      &:nth-child(1) {
        padding: 10px 5px;
        margin-bottom: 5px;
        border-bottom: 1.3px solid $drop-border;
      }
      .title {
        font-size: 1.5rem;
      }
      .input-passwd {
        width: 100%;
        margin-top: 5px;
        padding: 12px 15px;
        background: #ffffff;
        border-radius: 5px;
        border: 1px solid #ddd;
        font-size: 0.85rem;
      }
      .passwd-reset-btn {
        width: 100%;
        padding: 12px 15px;
        border-radius: 5px;
        background: #8b8c8d;
        color: #ffffff;
        &:hover {
          background: rgb(255, 104, 19);
        }
      }
    }
  }
  .alert-section {
    display: inline-block;
    margin-top: 50px;
    padding: 20px 15px;
    max-width: 400px;
    text-align: left;
    background: #ffffff;
    border: 1px solid #ddd;
    .subject {
      font-size: 0.9rem;
    }
    .content {
      position: relative;
      padding: 7px 0 0 23px;
      font-size: 0.8rem;
      &:before {
        content: "✔️";
        position: absolute;
        left: 3px;
        font-size: 0.65rem;
      }
    }
  }
}
</style>

