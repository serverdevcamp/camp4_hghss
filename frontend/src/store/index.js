import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import authentication from './authentication'
import recruit from './recruit'
import resume from './resume'
import template from './template'

Vue.use(Vuex, axios)

export default new Vuex.Store({
  modules: {
    auth : authentication,
    recruit : recruit,
    resume : resume,
    template : template,
  }
})
