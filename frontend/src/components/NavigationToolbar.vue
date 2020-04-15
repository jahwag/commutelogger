<template>

    <v-app-bar app color="primary" dark dense fixed id="toolbar">
        <v-toolbar-title>{{title}}</v-toolbar-title>

        <v-spacer/>

        <div v-if="this.$vuetify.breakpoint.smAndUp">
            <v-btn :to="item.destination" icon v-bind:key="item.name" v-for="item in this.bottom.items"
                   v-if="visible(item)">
                <v-icon>{{item.icon}}</v-icon>
            </v-btn>
        </div>
    </v-app-bar>

</template>

<script>
  import Navigation from './navigation'

  export default {
    name: 'cl-navigation-toolbar',
    props: {
      title: String,
    },

    data: () => ({
      bottom: Navigation.bottom,
    }),

    methods: {
      visible (item) {
        if (item.admin) {
          if (this.$store.state.me.admin) {
            return true
          }
          return false
        }

        return true
      },

    }

  }
</script>

<style scoped>

</style>