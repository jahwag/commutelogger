<template>
    <div>
        <v-fab-transition transition="slide-y-reverse-transition">
            <v-btn :id="($vuetify.breakpoint.mdAndUp ? 'add' : 'add-mobile')" :small="$vuetify.breakpoint.xs"
                   @click="$emit('add')" color="secondary" fab v-if="mutable">
                <v-icon>mdi-plus</v-icon>
            </v-btn>
        </v-fab-transition>

        <div v-if="loading">
            <slot name="loading"/>
        </div>

        <div color="secondary" v-if="!loading && value.length === 0">
            <v-card>
                <v-toolbar color="secondary" dark dense>
                    <v-toolbar-title>
                        <slot name="no-data-header"/>
                    </v-toolbar-title>
                    <v-spacer/>
                </v-toolbar>
                <v-card-text>
                    <slot name="no-data"/>
                </v-card-text>
            </v-card>
        </div>

        <div color="secondary" style="margin-bottom: 32px" v-bind:key="item[pk]" v-for="item in value">
            <v-card>
                <v-toolbar class="secondary" dark dense>
                    <slot name="header" v-bind="item"/>
                </v-toolbar>
                <slot name="default" v-bind="item"/>
            </v-card>
        </div>

    </div>
</template>

<script>
  export default {
    name: 'cl-card-list-crud',

    props: {
      value: Array,
      pk: String,
      mutable: Boolean,
      loading: Boolean,
    },

    data: () => ({}),

  }
</script>

<style scoped>
    #add {
        position: fixed;
        top: 24px;
        right: 12px;
        z-index: 100;
    }

    #add-mobile {
        position: fixed;
        top: 56px;
        right: 12px;
        z-index: 100;
    }
</style>