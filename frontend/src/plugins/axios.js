import axios from 'axios'
import Vue from 'vue'

// Use backend API as base url
axios.defaults.baseURL = '/api'

// Make axios available through the Vue instance
Vue.prototype.$http = axios

export default axios