<template>
    <div align="center" class="fill-height" justify="center">

        <v-fab-transition transition="slide-y-reverse-transition">
            <v-btn :id="($vuetify.breakpoint.mdAndUp ? 'add' : 'add-mobile')" :small="$vuetify.breakpoint.xs"
                   @click="$emit('add')" color="secondary" fab>
                <v-icon>mdi-plus</v-icon>
            </v-btn>
        </v-fab-transition>

        <v-card-text v-if="value.length === 0">
            <slot name="empty"/>
        </v-card-text>

        <slot name="header"/>

        <template v-for="item in value">
            <div v-bind:key="item[pk]">
                <slot name="default" v-bind="item"/>
            </div>
        </template>

    </div>
</template>

<script>
  export default {
    name: 'cl-table',

    props: {
      value: Array,
      pk: String,
    },

    data: () => ({
      trips: [],
      loading: false,
      options: {},
      bottom: false,
      page: 0,
    }),

  }

</script>

<style scoped>
    #add {
        position: fixed;
        top: 64px;
        right: 12px;
        z-index: 100;
    }

    #add-mobile {
        position: fixed;
        top: 64px;
        right: 12px;
        z-index: 100;
    }
</style>