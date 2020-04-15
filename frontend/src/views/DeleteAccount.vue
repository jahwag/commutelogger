<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col :class="'elevation-' + (this.$vuetify.breakpoint.xs ? '0' : '1')" lg="4" sm="5" xs="12">

            <p><b>Are you sure that you wish to proceed?</b></p>
            <p>Your account will be permanently deleted along with all your data in our database.</p>

            <v-btn block text to="/account">
                <span>Take me back</span>
            </v-btn>
            <v-btn @click="delete_account" block text>
                <span>I understand and wish to proceed</span>
            </v-btn>

        </v-col>
    </v-row>
</template>

<script>

  export default {
    methods: {
      delete_account () {
        this.$http.delete('/me').then(() => {
          this.$router.push('/')
          this.$msal.logout()
        })
      },
    }
  }

</script>

<style scoped>

</style>