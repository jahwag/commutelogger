<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col class="ma-0 pa-0 " lg="6" sm="3">

            <v-card :elevation="(this.$vuetify.breakpoint.smAndUp ? '1' : '0')" class="two">
                <v-card-title>Log</v-card-title>
                <code class="black--text elevation-0">
                    <div v-for="line in lines">
                        {{line}}
                    </div>
                </code>
            </v-card>

        </v-col>
    </v-row>
</template>

<script>

  export default {
    name: 'System',

    data: () => ({
      lines: '',
    }),

    mounted () {
      this.refresh()
    },

    methods: {
      refresh () {
        this.$http.get('/logs').then(response => {
          this.lines = response.data
        })
      },
    }
  }
</script>

<style scoped>
</style>