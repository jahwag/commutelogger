<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col :class="'elevation-' + (this.$vuetify.breakpoint.xs ? '0' : '1')" lg="4" sm="5" xs="12">
            <div class=" text-center">The page you have requested could not be found</div>

            <v-btn block color="primary" to="/">Take me home</v-btn>
        </v-col>
    </v-row>
</template>

<script>

  export default {
    name: 'PageNotFound',
  }
</script>

<style scoped>

</style>