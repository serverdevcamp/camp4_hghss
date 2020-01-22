<template>
  <modal
    name="account-modal"
    transition="pop-out"
    :height="380"
    :width="300"
    @before-open="beforeOpen"
  >
    <div class="modal-content">
      <div class="title point-font">{{ action[target] }}  🙏</div>
      <div class="form-group">
        <form autocomplete="false">
          <input id="n-email" type="text" placeholder="Email" v-model="email"/>
          <input id="n-password" type="password" placeholder="Password" v-model="passwd"/>
          <input v-if="target == 1" id="n-password2" type="password" placeholder="Password" v-model="passwd2"/>
        </form>
      </div>
      <div class="modal-btns">
          <button class="large-btn hghss-btn point-font">{{ action[target] }}</button>
          <button class="large-btn hghss-btn point-font" v-if="target == 0" @click="moveToPath('password/reset')">비밀번호 찾기</button>
          <!-- social -->
          <button class="large-btn naver-btn">connect with <span class="point-font">naver</span></button>
          <button class="large-btn facebook-btn">connect with <span class="point-font">facebook</span></button>
      </div>
    </div>
  </modal>
</template>
<script>
export default {
  data() {
    return {
      target: 0,
      action: ["SIGN IN", "REGISTER"],
      email: "",
      passwd: "",
      passwd2: "",
    };
  },
  methods: {
    beforeOpen(event) {
      // 데이터 초기화
      this.email = ''
      this.passwd = ''
      this.passwd2 = ''
    
      this.target = event.params.target;
    },
    moveToPath(path){
      this.$router.push(path)
      this.$modal.hide('account-modal')
    }
  },
};
</script>
<style lang="scss">
.v--modal {
    border-radius: 10px;
}
.modal-content{
  .title{
    margin: 20px 0;
    font-size: 1.3rem;
    text-align: center;
  }
  .form-group{
    padding: 0 20px;
    input[type="password"],
    input[type="text"] {
      display: block;
      box-sizing: border-box;
      margin: 8px 0;
      width: 100%;
      font-size: 0.85rem;
      line-height: 2;
      border: 0;
      border-bottom: 1px solid #dddedf;
      padding: 4px 8px;
      font-family: inherit;
      transition: 0.5s all;
      outline: none;
    }

  }
}
.modal-btns{
  margin-top: 30px;
  padding: 0 20px;
  .large-btn  {
    cursor: pointer;
    width: 100%;
    margin-top: 8px;
    padding: 10px;
    background: #ffffff;
    border-radius: 4px;
    border: 1px solid #dddedf;
    font-size: 0.85rem;
    letter-spacing: 1px;
    &:hover, &:hover span {
      color:#ffffff !important;
    }
  }
  $hghss-color: #8b8c8d;
  $facebook_color: #3880ff;
  $naver_color: #03cf5d;
  .hghss-btn {
    border-color: $hghss-color;
    color:$hghss-color;
    &:hover {
      border-color: $hghss-color;
      background: $hghss-color;
    }
  }
  .facebook-btn {
    border-color: $facebook_color;
    color: $facebook_color;
    span {
      color: $facebook_color;
    }
    &:hover {
      border-color: $facebook_color;
      background: $facebook_color;
    }
  }
  .naver-btn {
    border-color: $naver_color;
    color: $naver_color;
    span {
      color: $naver_color;
    }
    &:hover {
      border-color: $naver_color;
      background: $naver_color
    }
  }
}
</style>