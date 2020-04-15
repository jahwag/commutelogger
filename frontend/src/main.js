import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import msal from './plugins/msal'
import './registerServiceWorker'
import http from './plugins/axios'
import store from './store'

// Handle Azure AD redirect
msal.handleRedirectCallback((error, response) => {
  if (!!error) {
    console.log(error)
  } else {
    msal.acquireTokenSilent({ scopes: ['user.read'] }).catch(error => {
      console.log(error)
    }).then(response => {
      http.defaults.headers.common['Authorization'] = 'Bearer ' + response.idToken

      http.get('/me').then(() => {
        store.commit('refresh_me')
        router.push('/trips')
      }).catch(err => {
        // Need to create account
        router.push('/register')
      })
    })
  }
}).then()

// Routes that require authentication should not be accessible outside of session
router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(value => value.meta.requiresAuth)

  if (requiresAuth && !store.getters.isAuthenticated) {
    next('/')
  } else {
    next()
  }

})

new Vue({
  router,
  vuetify,
  store: store,
  render: h => h(App)
}).$mount('#app')
