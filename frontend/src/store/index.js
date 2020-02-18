import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import authentication from './authentication'
import recruit from './recruit'
import resume from './resume'
import chat from './chat'
import template from './template'
import sharedMutations from 'vuex-shared-mutations';

Vue.use(Vuex, axios)

export default new Vuex.Store({
  modules: {
    auth : authentication,
    recruit : recruit,
    resume : resume,
    chat : chat,
    template : template,
  },
  plugins: [sharedMutations({predicate: ['setUserInfo', 'signout']})]
})
