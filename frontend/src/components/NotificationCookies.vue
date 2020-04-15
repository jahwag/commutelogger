<template>
    <cookie-law buttonDecline v-on:decline="decline">
        <div slot="message">
            This website uses cookies to ensure you get the best experience on our website. <a
                href="https://cookiesandyou.com/" target="_blank">Learn More</a>.
        </div>
    </cookie-law>
</template>

<script>
  import CookieLaw from 'vue-cookie-law'

  export default {
    name: 'cl-notification-cookies',
    components: { CookieLaw },

    methods: {
      decline () {
        window.location.href = 'https://cookiesandyou.com/'
      },
    },
  }
</script>

<style scoped>

</style>