<template>
  <div id="navigator">
    <div class="nav-section">
      <div class="point-font nav-home" @click="$router.push('/')">합격하소서 🙏</div>
      <div class="nav-btn" @click="$router.push({ name: 'RecruitPage'})">채용 공고</div>
      <div class="nav-btn" @click="$router.push({ name: 'ResumePage'})">자기소개서</div>
      <div class="nav-btn" @click="$router.push({ name: 'ResumeWrite', params: { id: 123 }})">더미</div>

    </div>
    <div class="nav-section">
      <div class="user-section">
        <p class="nav-btn icon-btn" @click="user_menu = !user_menu">
          <font-awesome-icon :icon="['far', 'user-circle']" />
        </p>
        <div v-if="user_menu && !$store.state.auth.user.email" class="user-menu">
          <div class="sign-in-btn btn" @click="showModal(0)">SIGN IN</div>
          <div class="sign-up-btn btn" @click="showModal(1)">REGISTER</div>
        </div>
        <div v-else-if="user_menu && $store.state.auth.user.email" class="user-menu">
          <div class="accounts btn">{{ $store.state.auth.user.email }}</div>
          <div class="sign-out-btn btn" @click="signout">LOGOUT</div>
        </div>
      </div>
      <div class="chat-section" v-if="$route.name != 'HghssPage'">
        <p class="nav-btn icon-btn" @click="$store.state.template.ChatState = !$store.state.template.ChatState">
          <font-awesome-icon :icon="['far', 'comment-dots']" />
        </p>
      </div>
    </div>
    <Authentication />
  </div>
</template>
<script>
import Authentication from "./Authentication";
import { mapActions } from 'vuex';

export default {
  components: {
    Authentication
  },
  data() {
    return {
      user_menu: false
    };
  },
  methods: {
    ...mapActions (['signout']),
    showModal(target) {
      this.user_menu = false;
      this.$modal.show("account-modal", { target: target });
    }
  }
};
</script>
<style lang="scss">
#navigator {
  height: 50px;

  .nav-section,
  .nav-home,
  .nav-btn {
    display: inline-block;
  }

  .nav-section {
    padding: 0 30px;

    &:nth-child(1) {
      width: 70%;
    }

    &:nth-child(2) {
      text-align: right;
      width: 30%;
    }
  }

  .nav-home {
    cursor: pointer;
    padding: 8px 20px 0 0;
    font-size: 1.3rem;
  }

  .nav-btn {
    cursor: pointer;
    padding: 15px 10px 0;
    font-size: 0.9rem;
    font-weight: 600;
  }

  .icon-btn {
    padding: 10px;
    font-size: 1.2rem;

    &:hover {
      background: rgba(1, 1, 1, 0.1);
      border-radius: 3px;
    }
  }

  .user-section,
  .chat-section {
    display: inline-block;
    margin-left: 5px;
  }

  .user-section {
    position: relative;
  }

  .user-menu {
    z-index: 99;
    position: absolute;
    right: 0;
    top: 45px;
    background: #ffffff;
    border-bottom-left-radius: 5px;
    border-bottom-right-radius: 5px;

    .btn {
      font-size: 0.8rem;
      font-weight: 500;
      padding: 15px 20px;
      text-align: center;
    }

    .accounts {
      border-bottom: 1px solid rgba(1, 1, 1, 0.1);
    }

    .sign-in-btn,
    .sign-up-btn,
    .sign-out-btn {
      cursor: pointer;

      &:hover {
        background: rgba(1, 1, 1, 0.1);
      }
    }
  }
}
</style>