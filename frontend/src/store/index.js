import Vue from 'vue'
import Vuex from 'vuex'
import http from './../plugins/axios'
import msal from '../plugins/msal'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    me: {
      email: '',
      organization: '',
      office: '',
      admin: false,
    }
  },
  mutations: {
    refresh_me (state) {
      http.get('/me').then(response => {
        state.me.email = response.data.email
        state.me.office = response.data.office
        state.me.organization = response.data.organization
        state.me.admin = response.data.admin
      })
    }
  },
  getters: {
    isAuthenticated () {
      return !!msal.getAccount()
    }
  }
})

export default store