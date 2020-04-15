import * as msal from '@azure/msal-browser'
import Vue from 'vue'

if (!!!process.env.VUE_APP_CLIENT_ID) {
  throw new Error('VUE_APP_CLIENT_ID must be set')
}
if (!!!process.env.VUE_APP_TENANT_ID) {
  throw new Error('VUE_APP_TENANT_ID must be set')
}
if (!!!process.env.VUE_APP_REDIRECT_URI) {
  throw new Error('VUE_APP_REDIRECT_URI must be set')
}

const instance = new msal.PublicClientApplication({
  auth: {
    clientId: process.env.VUE_APP_CLIENT_ID,
    tenantId: process.env.VUE_APP_TENANT_ID,
    redirectUri: process.env.VUE_APP_REDIRECT_URI,
    navigateToLoginRequestUrl: true
  },
  cache: {
    storeAuthStateInCookie: true
  },
})

// Make MSAL available through Vue instance
Vue.prototype.$msal = instance

export default instance
