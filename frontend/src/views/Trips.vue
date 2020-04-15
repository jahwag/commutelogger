<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col :class="'elevation-' + (this.$vuetify.breakpoint.xs ? '0' : '1')" class="pa-0 ma-0" lg="4" sm="5"
               v-scroll="onScroll"
               xs="12">

            <cl-dialog-trip :edit="dialogId" v-model="dialog" v-on:persist="refreshData"/>

            <cl-table pk="id" ref="table" v-model="trips" v-on:add="dialog = true">
                <template v-slot:empty>
                    <cl-loading-indicator v-if="loading"/>
                    <div v-if="trips === [] && !loading">You have not submitted any trips.</div>
                </template>

                <template v-slot:default="item">
                    <v-card class="overline" flat v-ripple="{ class: `success--text` }">

                        <v-card-text>
                            <v-row no-gutters>
                                <v-col align="left">
                                    <span>{{item.date}}</span>
                                    <v-spacer/>
                                    <span>{{item.distance}} km</span>
                                </v-col>

                                <v-col align="left">
                                    <v-icon class="ma-1">{{item.icon}}</v-icon>
                                    <v-spacer/>

                                    <span v-bind:key="n" v-for="n in item.travellers">
                                        <v-icon size="16" v-if="item.shareable">mdi-account-outline</v-icon>
                                    </span>
                                </v-col>

                                <v-col align="left">
                                    <v-rating :value="item.climateIndex" background-color="success lighten-1"
                                              color="success"
                                              dense
                                              half-increments
                                              readonly small>
                                    </v-rating>
                                    <v-spacer/>
                                    <span>{{item.co2pp}} g CO<sub>2</sub></span>
                                    <span v-if="item.shareable"> /
                                        <v-icon size="16">mdi-account-outline</v-icon>
                                    </span>
                                </v-col>
                            </v-row>
                        </v-card-text>

                        <v-divider/>
                    </v-card>
                </template>
            </cl-table>

        </v-col>
    </v-row>
</template>

<script>
  import { transportIcons } from '../utils/transport-icons'
  import ClLoadingIndicator from '../components/LoadingIndicator'
  import ClDialogTrip from '../components/DialogTrip'
  import ClTable from '../components/Table'

  export default {
    components: { ClTable, ClDialogTrip, ClLoadingIndicator },

    data: () => ({
      loading: false,
      dialogId: null,
      dialog: false,
      scrollPosY: 0,
      trips: []
    }),

    methods: {
      onScroll: function () {
        this.scrollPosY = (window.pageYOffset || document.documentElement.scrollTop) - (document.documentElement.clientTop || 0)
      }
      ,

      refreshData () {
        this.loading = true
        this.$http.get('/trips').then(response => {
          this.trips = response.data

          this.trips.forEach(trip => {
            trip.icon = transportIcons(trip.transport)
          })

          this.loading = false
        })
      }
      ,

      remove (id) {
        this.$http.delete('/trips', { data: { id: id } }).then(() => {
          this.fetch()
        }).catch(error => {
          console.log(error)
        })
      }
      ,

    }
    ,

    mounted () {
      this.refreshData()
    }
    ,
  }

</script>

<style scoped>

</style>