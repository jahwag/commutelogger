<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col class="ma-0 pa-0 " lg="3" sm="3">

            <v-card :elevation="(this.$vuetify.breakpoint.smAndUp ? '1' : '0')" class="two">
                <v-toolbar class="body-1 justify-center" dense flat prominent>

                    <v-list align="center" flat two-line>
                        <v-list-item>
                            <v-list-item-content>
                                <v-list-item-title>
                                    {{this.$store.state.me.email}}
                                </v-list-item-title>
                                <v-list-item-subtitle>
                                    {{this.$store.state.me.organization}} -
                                    {{this.$store.state.me.admin ? 'Admin' : 'User'}}
                                </v-list-item-subtitle>
                            </v-list-item-content>
                        </v-list-item>
                    </v-list>
                </v-toolbar>

                <v-card-text>
                    <v-select :items="offices" label="Office" v-model="office"/>
                </v-card-text>

                <v-btn @click="download_data" block text>
                    <p>Download personal data</p>
                    <v-spacer/>
                    <v-icon>mdi-download</v-icon>
                </v-btn>

                <v-btn @click="logout" block text>
                    <p>Sign out</p>
                    <v-spacer/>
                    <v-icon>mdi-logout</v-icon>
                </v-btn>

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

    created () {
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