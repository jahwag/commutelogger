<template>
    <v-dialog max-width="500" v-model="show">
        <v-card>
            <v-toolbar color="secondary" dark>
                <v-toolbar-title>Add a ride opportunity</v-toolbar-title>
            </v-toolbar>

            <v-card-text>
                <v-row dense style="padding-top: 8px;padding-bottom: 8px">
                    <v-col class="ma-0 pa-0">
                        <v-text-field :error="!!!carpooling.where.departure"
                                      @input="validate"
                                      hide-details
                                      label="Departure *" single-line v-model="carpooling.where.departure"/>
                    </v-col>
                    <v-col class="ma-0 pa-0">
                        <v-text-field :error="!!!carpooling.where.destination"
                                      @input="validate"
                                      hide-details
                                      label="Destination *" single-line v-model="carpooling.where.destination"/>
                    </v-col>
                </v-row>

                <v-row dense style="padding-bottom: 8px">
                    <v-col class="ma-0 pa-0">
                        <v-select :error="!!!carpooling.how.detourFlexibility"
                                  :items="detourFlexibilityItems"
                                  @input="validate"
                                  hide-details
                                  label="Detour flexibility *"
                                  v-model="carpooling.how.detourFlexibility"/>
                    </v-col>
                    <v-col class="ma-0 pa-0">
                        <v-select :error="!!!carpooling.how.costSharing"
                                  :items="costSharingItems"
                                  @input="validate"
                                  hide-details
                                  label="Cost sharing *"
                                  v-model="carpooling.how.costSharing"/>
                    </v-col>
                </v-row>

                <v-checkbox class="ma-0 pa-0" dense label="Detours inside tolls"
                            v-model="carpooling.how.tolls"/>

                <v-row dense>
                    <v-col class="ma-0 pa-0">
                        <v-checkbox class="ma-0 pa-0" dense label="Mon" v-model="carpooling.when.monday"/>
                        <v-checkbox class="ma-0 pa-0" dense label="Thu" v-model="carpooling.when.thursday"/>
                    </v-col>
                    <v-col class="ma-0 pa-0">
                        <v-checkbox class="ma-0 pa-0" dense label="Tue" v-model="carpooling.when.tuesday"/>
                        <v-checkbox class="ma-0 pa-0" dense label="Fri" v-model="carpooling.when.friday"/>
                    </v-col>
                    <v-col class="ma-0 pa-0">
                        <v-checkbox class="ma-0 pa-0" dense label="Wed" v-model="carpooling.when.wednesday"/>
                    </v-col>
                </v-row>

                <v-slider label="Seats" max="7" min="1" thumb-color="secondary" thumb-label="always"
                          tick-size="4"
                          ticks="always"
                          v-model="carpooling.how.seats"/>

                <v-textarea dense label="Comments"
                            outlined rows="2"
                            v-model="carpooling.comments"/>
            </v-card-text>

            <v-card-actions class="ma-0 pa-0">
                <v-btn @click="cancel" color="success" text>Cancel</v-btn>
                <v-spacer/>
                <v-btn :disabled="!valid" @click="save" color="success" text>Save</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>

  export default {
    name: 'cl-dialog-carpooling',

    props: {
      value: Boolean,
      edit: Number,
    },

    computed: {
      show: {
        get () {
          return this.value
        },
        set (value) {
          this.$emit('input', value)
        }
      },
      dateRangeText () {
        return this.dates.join(' — ')
      },
    },

    watch: {
      show: function (newValue, oldValue) {
        if (newValue) {
          if (!!this.edit) {
            this.$http.get('/carpoolings/' + this.edit).then(response => {
              this.carpooling = response.data
              this.valid = true
            })
          }
        }
      }
    },

    data: function () {
      return {
        valid: false,
        detourFlexibilityItems: ['In a 5 min window', 'In a 10 min window', 'In a 15 min window', 'In a 30 min window'],
        tollItems: ['Never', 'Accept'],
        costSharingItems: ['No charge', 'Split on agreement'],

        carpooling: this.defaultCarpooling()
      }
    },

    methods: {
      validate () {
        const departureValid = !!this.carpooling.where.departure
        const destinationValid = !!this.carpooling.where.destination
        const detourFlexibilityValid = !!this.carpooling.how.detourFlexibility
        const costSharingValid = !!this.carpooling.how.costSharing

        this.valid = departureValid && destinationValid && detourFlexibilityValid && costSharingValid
      },

      presentAndFuture (date) {
        const now = new Date(new Date(Date.now()).toDateString())
        return new Date(date) >= now
      },

      clear () {
        this.carpooling = null
        this.carpooling = this.defaultCarpooling()

        this.validate()
      },

      defaultCarpooling () {
        return {
          where: {
            departure: '',
            destination: '',
          },
          when: {
            monday: true,
            tuesday: true,
            wednesday: true,
            thursday: true,
            friday: true,
          },
          how: {
            detourFlexibility: '',
            tolls: true,
            costSharing: '',
            seats: 4
          },
          comments: ''
        }
      },

      cancel () {
        this.show = false
        this.clear()
      },

      save () {
        const isEdit = !!this.edit

        if (isEdit) {
          this.$http.put('/carpoolings/' + this.edit, this.carpooling).then(() => {
            this.show = false
            this.clear()
            this.$emit('persist')
          })
        } else {
          this.$http.post('/carpoolings/', this.carpooling).then(() => {
            this.show = false
            this.clear()
            this.$emit('persist')
          })
        }
      },
    },

  }
</script>

<style scoped>
    .v-text-field {
        width: 100%;
    }
</style>