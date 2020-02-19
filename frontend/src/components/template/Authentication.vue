<template>
  <modal
    name="account-modal"
    transition="pop-out"
    :height="380"
    :width="300"
    @before-open="beforeOpen"
  >
    <div class="modal-content">
      <div class="title point-font">{{ action[target] }} 🙏</div>
      <div class="form-group">
        <form autocomplete="false">
          <input id="n-email" type="text" placeholder="Email" v-model="user.email" @blur="checkEmail" />
          <input id="n-password" type="password" placeholder="Password" v-model="user.password" />
          <input
            v-if="target == 1"
            id="n-password2"
            type="password"
            placeholder="Password"
            v-model="password2"
          />
        </form>
      </div>
      <div class="modal-btns">
        <button v-if="target == 0" class="large-btn hghss-btn point-font" @click="signin">SIGN IN</button>
        <button v-if="target == 1" class="large-btn hghss-btn point-font" @click="signup">REGISTER</button>
        <button
          class="large-btn hghss-btn point-font"
          v-if="target == 0"
          @click="moveToPath('password/reset')"
        >비밀번호 찾기</button>
        <!-- social -->
        <button class="large-btn naver-btn" @click="naverLogin" >
          connect with
          <span class="point-font">naver</span>
        </button>
        <button class="large-btn facebook-btn">
          connect with
          <span class="point-font">facebook</span>
        </button>
      </div>
    </div>
  </modal>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      target: 0,
      action: ["SIGN IN", "REGISTER"],
      user: {
        email: "",
        password: ""
      },
      password2: "",
      naverLoginUrl: "https://nid.naver.com/oauth2.0/authorize",
      responseType: "code",
      clientId: "YS36qtMJTpVLzoYAK_up",
      redirectUri: "http://localhost:8080/oauth2",
      state: "123"
    };
  },
  created() {
    this.naverLoginUrl += "?response_type=" + this.responseType;
    this.naverLoginUrl += "&client_id=" + this.clientId;
    this.naverLoginUrl += "&redirect_uri=" + this.redirectUri;
    this.naverLoginUrl += "&state=" + this.state;
  },
  computed: {
    ...mapGetters(['getUser','is_user_login'])
  },
  methods: {
    async signin() {
      let signinSuccess = await this.$store.dispatch("signin", this.user);
      if(signinSuccess) this.$modal.hide('account-modal');
    },
    async signup() {
      if (this.user.password === this.password2 && this.password !== '') {
        let signupSuccess = await this.$store.dispatch("signup", this.user);
        if(signupSuccess) this.$modal.hide('account-modal');
      }else {
        alert('비밀번호가 일치하지 않습니다.');
      }
    },
    checkEmail() {
      let emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      if(this.user.email !== '' && !emailRule.test(this.user.email)){
        alert('이메일 형식에 맞지 않습니다.');
      }
    },
    beforeOpen(event) {
      // 데이터 초기화
      this.user.email = "";
      this.user.password = "";
      this.password2 = "";

      this.target = event.params.target;
    },
    moveToPath(path) {
      this.$router.push(path);
      this.$modal.hide("account-modal");
    },
    naverLogin() {
      window.open(this.naverLoginUrl);
      this.$modal.hide("account-modal");
    }
  }
};
</script>
<style lang="scss">
.v--modal {
  border-radius: 10px;
}

.modal-content {
  .title {
    margin: 20px 0;
    font-size: 1.3rem;
    text-align: center;
  }

  .form-group {
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

.modal-btns {
  margin-top: 30px;
  padding: 0 20px;

  .large-btn {
    cursor: pointer;
    width: 100%;
    margin-top: 8px;
    padding: 10px;
    background: #ffffff;
    border-radius: 4px;
    border: 1px solid #dddedf;
    font-size: 0.85rem;
    letter-spacing: 1px;

    &:hover,
    &:hover span {
      color: #ffffff !important;
    }
  }

  $hghss-color: #8b8c8d;
  $facebook_color: #3880ff;
  $naver_color: #03cf5d;

  .hghss-btn {
    border-color: $hghss-color;
    color: $hghss-color;

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
      background: $naver_color;
    }
  }
}
</style>