<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col class="ma-0 pa-0 " lg="2" sm="3">

            <v-card :elevation="(this.$vuetify.breakpoint.smAndUp ? '1' : '0')" class="two">

                <v-card-text>

                    <v-btn block text to="/admin/users">
                        Users
                        <v-spacer/>
                        <v-icon>mdi-chevron-right</v-icon>
                    </v-btn>
                    <v-btn block text to="/admin/organizations">
                        Organizations
                        <v-spacer/>
                        <v-icon>mdi-chevron-right</v-icon>
                    </v-btn>
                    <v-btn block text to="/admin/system">
                        System
                        <v-spacer/>
                        <v-icon>mdi-chevron-right</v-icon>
                    </v-btn>

                </v-card-text>

            </v-card>

        </v-col>
    </v-row>
</template>

<script>
  export default {
    name: 'Account',

    data: () => ({
      offices: [],
    }),

    computed: {
      office: {
        get () {
          return this.$store.state.me.office
        },
        set (value) {
          this.$http.post('/me/office', {
            'office': value
          }).then(() => {
            this.$store.commit('refresh_me')
          })
        }
      }
    },

    mounted () {
      this.$http.get('/offices').then(response => {
        this.offices = response.data
      })
    },

    methods: {
      logout () {
        this.$msal.logout()
      },

      download_data () {
        console.log('Not implemented yet!')

        this.$http.get('/me/download').then(response => {
          const data = JSON.stringify(response.data, null, '\t')
          const blob = new Blob([data], { type: 'text/plain' })
          const e = document.createEvent('MouseEvents'),
            a = document.createElement('a')
          a.download = 'data.json'
          a.href = window.URL.createObjectURL(blob)
          a.dataset.downloadurl = ['text/json', a.download, a.href].join(':')
          e.initEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null)
          a.dispatchEvent(e)
        }).catch(error => {
          console.log(error)
        })
      },

    }
  }
</script>

<style scoped>
</style>