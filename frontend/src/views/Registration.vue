<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col class="ma-0 pa-0 " lg="3" sm="3">

            <v-card :elevation="(this.$vuetify.breakpoint.smAndUp ? '1' : '0')">
                <v-toolbar color="primary" dark flat>{{this.$route.name}}</v-toolbar>

                <v-card-text>
                    <p>Almost done!</p>
                    <p>Provide the following details to complete your registration.</p>

                    <v-text-field label="Email" readonly v-model="this.email"/>

                    <v-select :items="offices" @input="valid=true" hint="Select your branch office."
                              label="Office" persistent-hint v-model="office"/>
                </v-card-text>

                <v-card-actions>
                    <v-btn :disabled="!valid" @click="save" block color="primary">Proceed</v-btn>
                </v-card-actions>

            </v-card>

        </v-col>
    </v-row>
</template>

<script>
  import store from '../store'

  export default {
    name: 'Registration',

    data: () => ({
      valid: false,
      offices: [],
      office: String,
      email: String
    }),

    created () {
      this.email = this.$msal.getAccount().userName

      this.$http.get('/offices').then(response => {
        this.offices = response.data
      })
    },

    methods: {
      save () {
        const data = {
          'office': this.office
        }

        this.$http.post('/me', data).then(() => {
          store.commit('refresh_me')
          this.$router.push('/trips')
        })
      },
    },

  }
</script>

<style scoped>
</style>