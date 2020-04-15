<template>
    <v-dialog max-width="400" v-model="show">
        <v-card>
            <v-card-title class="headline">Add trip</v-card-title>

            <v-card-text>
                <v-container fluid>
                    <v-row align="center">
                        <v-menu max-width="290px" min-width="290px" offset-y
                                transition="scale-transition">
                            <template v-slot:activator="{ on }">
                                <v-text-field @input="validate"
                                              label="Date *"
                                              persistent-hint
                                              prepend-icon="event"
                                              readonly
                                              v-model="date"
                                              v-on="on"
                                ></v-text-field>
                            </template>
                            <v-date-picker :allowed-dates="pastAndPresent" no-title v-model="date"></v-date-picker>
                        </v-menu>
                    </v-row>

                    <div>
                        <v-select :append-icon="getIcon(transport)"
                                  :error="!!!transport.name"
                                  :items="transports"
                                  @input="validate"
                                  item-text="name"
                                  label="Transport *" return-object
                                  v-model="transport">
                        </v-select>
                    </div>
                    <div>
                        <v-text-field :error="!!!distance || distance < 1" @input="validate" hide-details
                                      label="Distance (km)" type="number"
                                      v-model="distance"/>
                    </div>
                    <div>
                        <v-text-field :error="!!!travellers || travellers < 1" @input="validate" hide-details
                                      label="Number of travellers"
                                      type="number" v-if="transport.shareable"
                                      v-model="travellers"/>
                    </div>
                </v-container>
            </v-card-text>

            <v-card-actions>
                <v-btn @click="cancel" color="success" text>Cancel</v-btn>
                <v-spacer/>
                <v-btn :disabled="!valid" @click="save" color="success" text>Save</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
  import dateformat from 'dateformat'
  import { transportIcons } from '../utils/transport-icons'

  export default {
    name: 'cl-dialog-trip',

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
    },

    watch: {
      show: function (newValue, oldValue) {
        if (newValue) {
          if (!!this.edit) {
            this.$http.get('/trips/' + this.edit).then(response => {
              // TODO implement
            })
          }
        }
      }
    },

    data: function () {
      return {
        valid: false,
        date: dateformat(new Date(), 'yyyy-mm-dd'),
        transport: {},
        distance: '',
        transports: [],
        travellers: 1,
      }
    },

    methods: {
      validate () {
        if (!!!this.transport.name) {
          this.valid = false
        }
        if (this.transport.shareable && (!!this.travellers || this.travellers < 1)) {
          this.valid = false
        }
        if (!!this.distance || this.distance < 1) {
          this.valid = false
        }

        this.valid = true
      },

      pastAndPresent (date) {
        const now = new Date(new Date(Date.now()).toDateString())
        return new Date(date) <= now
      },

      getIcon (transport) {
        transportIcons(transport)
      },

      clear () {
        this.date = dateformat(new Date(), 'yyyy-mm-dd')
        this.transport = {}
        this.distance = ''
        this.travellers = 1
      },

      cancel () {
        this.show = false
        this.clear()
      },

      save () {
        const trip = {
          transport: this.transport.name,
          date: this.date,
          distance: this.distance,
          travellers: this.travellers,
        }

        this.$http.post('/trips', trip).then(() => {
          this.show = false
          this.clear()
          this.$emit('persist')
        })
      }
    },

    created () {
      this.transports = this.$http.get('/transports').then(response => {this.transports = response.data})
    }
  }

</script>

<style scoped>
</style>