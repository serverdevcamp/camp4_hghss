import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import actions from './actions'
import mutations from './mutations'
import state from './state'
import axios from 'axios'

Vue.use(Vuex, axios)

export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions,
  // modules: {
  // }
})
