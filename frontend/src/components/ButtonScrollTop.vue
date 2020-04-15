<template>
    <v-fab-transition transition="slide-y-reverse-transition" v-if="$vuetify.breakpoint.xs">
        <v-btn @click="$vuetify.goTo('#app')"
               color="accent" dark fab
               id="scrolltop-mobile" small v-if="scroll > 100">
            <v-icon>keyboard_arrow_up</v-icon>
        </v-btn>
    </v-fab-transition>
</template>

<script>
  export default {
    name: 'cl-btn-scroll-top',

    props: {
      scroll: Number,
    },

    data: () => ({
      fab: false,
    }),
  }
</script>

<style scoped>
    #scrolltop-mobile {
        position: fixed;
        bottom: 64px;
        right: 12px;
    }
</style>