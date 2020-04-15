<template>
    <v-bottom-navigation app background-color="primary" dark>

        <v-btn :ripple="false" :to="item.destination" v-bind:key="item.name"
               v-for="item in this.bottom.items" v-if="visible(item)">
            <v-icon large>{{item.icon}}</v-icon>
        </v-btn>

    </v-bottom-navigation>
</template>

<script>
  import Navigation from './navigation'

  export default {
    name: 'cl-navigation-bottom',

    data: () => ({
      bottom: Navigation.bottom
    }),

    methods: {
      visible (item) {
        if (item.admin) {
          // Administration should be performed through desktop
          return false
        }

        return true
      },

    }
  }
</script>
