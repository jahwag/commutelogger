import Vue from 'vue'
import Vuetify from 'vuetify/lib'

Vue.use(Vuetify, {
  options: {
    customProperties: true
  }
})

// Combitech graphic theme
export default new Vuetify({
  theme: {
    themes: {
      light: {
        primary: '#064182',
        secondary: '#5479C5',
        accent: '#CD0C59',
        error: '#CD0C59',
        warning: '#FF901E', // FAB100
        info: '#5479C5', // FF901E
        success: '#65B01E',
        background: '#ffffff',
      },
    },
  },

})
