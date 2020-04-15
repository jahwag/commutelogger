<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col :class="'elevation-' + (this.$vuetify.breakpoint.xs ? '0' : '1')" lg="4" sm="5" xs="12">

            <p>Sorry, this service is not available to your organization.</p></v-col>

    </v-row>
</template>

<script>
  export default {
    name: 'Organization'
  }
</script>

<style scoped>

</style>