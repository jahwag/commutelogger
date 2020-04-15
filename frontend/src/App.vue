<template>
    <v-app>
        <v-content>
            <router-view name="toolbar"/>

            <v-container :class="(this.$vuetify.breakpoint.smAndUp ? 'middle fill-height' : '')"
                         class="ma-0 pa-0" fluid v-scroll="onScroll">
                <router-view name="content"/>
            </v-container>
        </v-content>

        <cl-btn-scroll-top :scroll="scrollPositionY"/>

        <footer>
            <cl-notification-cookies/>
        </footer>

        <router-view name="bottom_navigation" v-if="this.$vuetify.breakpoint.xs"/>
    </v-app>
</template>

<script>
  import ClBtnScrollTop from './components/ButtonScrollTop'
  import ClNotificationCookies from './components/NotificationCookies'

  export default {
    name: 'App',
    components: { ClNotificationCookies, ClBtnScrollTop },

    data: () => ({
      scrollPositionY: 0,
      installerSnackbar: true,
    }),

    methods: {
      onScroll () {
        const doc = document.documentElement
        this.scrollPositionY = (window.pageYOffset || doc.scrollTop) - (doc.clientTop || 0)
      },
    },

    created () {
      this.$store.commit('refresh_me')
    }
  }
</script>

<style>
    .middle {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
    }

    /* Hack for disabling overscroll on iOS */
    .no-overscroll {
        position: fixed;
    }

    /* Hack for disabling ripples (better support will be added in 2.3) */
    .v-ripple__container {
        display: none !important;
    }

    .mobile-margin {
        margin-top: 44px;
    }
</style>