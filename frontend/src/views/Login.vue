<template>
    <v-row align="center" class="ma-0 pa-0" justify="center" no-gutters>
        <v-col class="ma-0 pa-0 " lg="3" sm="3">

            <v-card :elevation="(this.$vuetify.breakpoint.smAndUp ? '1' : '0')">
                <div>
                    <v-toolbar color="primary" dark flat>Log in</v-toolbar>
                    <v-responsive height="50%">
                        <cl-carousel-login/>
                    </v-responsive>

                    <v-card-text>
                        <p>
                            Sign in with your social account.
                        </p>
                    </v-card-text>

                    <v-card-actions>
                        <v-btn :loading="loading" @click="login" block class="pa-2"
                               color="primary">
                            <v-icon left>mdi-microsoft-azure</v-icon>
                            Log in with Azure AD
                        </v-btn>
                    </v-card-actions>
                </div>
            </v-card>
        </v-col>
    </v-row>
</template>

<script>
  import ClCarouselLogin from '../components/CarouselLogin'

  export default {
    components: { ClCarouselLogin },

    data: () => ({
      loading: false
    }),

    methods: {
      login () {
        this.loading = true
        this.$msal.loginRedirect({
          scopes: ['user.read']
        })
      }
    },
  }
</script>

<style scoped>

</style>
