<template>
  <section id="resetPasswd">
    <div class="passwd-section">
      <v-row>
        <p class="title point-font">비밀번호 찾기</p>
      </v-row>
      <v-row>
        <input id="email" type="text" placeholder="아이디(이메일 주소)" class="input-email" v-model="email"/>
      </v-row>
      <v-row>
        <button class="point-font passwd-reset-btn">비밀번호 재설정 메일 보내기</button>
      </v-row>
    </div>
    <br/>
    <div class="alert-section">
      <p class="subject point-font">비밀번호 재설정 메일이 오지 않을 경우</p>
      <p class="content">스팸 메일함을 확인하세요.</p>
      <p class="content">비밀번호를 재설정할 아이디(이메일)로 auth.hghss@gamil.com에 신청 메일을 보내주세요.</p>
    </div>
  </section>
</template>
<script>
export default {
  data: () => ({
    email:'',
  }),
  methods: {
  }
};
</script>
<style lang="scss">
$calendar-border: #f0f0f0;
$drop-border: #bbbbbb;
$drop-box: #dddee0;
#resetPasswd {
  height: calc(100vh - 50px);
  overflow: hidden;
  background: $calendar-border;
  text-align: center;
  .row, .col{
    margin:0;
    padding:0;
  }
  .passwd-section{
    margin-top: 20vh;
    display: inline-block;
    min-width: 300px;
    .row{
      padding: 5px 0;
      width:100%;
      &:nth-child(1){
        padding: 10px 5px;
        margin-bottom: 5px;
        border-bottom: 1.3px solid $drop-border;
      }
      .title{
        font-size: 1.5rem;
      }
      .input-email{
        width: 100%;
        margin-top: 5px;
        padding: 12px 15px;
        background: #ffffff;
        border-radius: 5px;
        border: 1px solid #ddd;
        font-size: 0.85rem;
      }
      .passwd-reset-btn{
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
  .alert-section{
    display: inline-block;
    margin-top: 50px;
    padding: 20px 15px;
    max-width: 400px;
    text-align: left;
    background: #ffffff;
    border: 1px solid #ddd;
    .subject{
      font-size: 0.9rem;
    }
    .content{
      position: relative;
      padding: 7px 0 0 23px;
      font-size: 0.8rem;
      &:before{
        content:"✔️";
        position: absolute;
        left : 3px;
        font-size: 0.65rem;
      }
    }
  }
}
</style>

