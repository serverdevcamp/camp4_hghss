import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faStar } from '@fortawesome/free-solid-svg-icons'
import { faUserCircle as farUserCircle } from '@fortawesome/free-regular-svg-icons'
// Modal
import VModal from 'vue-js-modal'

Vue.use(VModal)
library.add(farUserCircle,faStar)
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App),
}).$mount('#app')
