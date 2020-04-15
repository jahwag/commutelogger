<template>
    <div>
        <v-dialog load max-width="400" v-model="show">
            <v-card>
                <v-card-title class="headline">{{!!this.edit ? 'Edit competition' : 'Add competition'}}</v-card-title>

                <v-card-text>
                    <v-menu @input="validate" max-width="290px" offset-y
                            transition="scale-transition">
                        <template v-slot:activator="{ on }">
                            <v-text-field :error="!dateValid"
                                          label="Period *" persistent-hint
                                          prepend-icon="event"
                                          readonly v-model="dateRangeText" v-on="on"
                            ></v-text-field>
                        </template>
                        <v-date-picker :allowed-dates="presentAndFuture" range v-model="dates"/>
                    </v-menu>

                    <v-text-field :error="!titleValid"
                                  @input="validate"
                                  dense label="Title" prepend-icon="mdi-note-outline"
                                  v-model="title"/>

                    <v-combobox :error="!participantsValid"
                                :error-messages="!participantsValid ? 'Must be at least two' : ''"
                                :items="offices"
                                @input="validate"
                                chips
                                deletable-chips
                                label="Participants *" multiple
                                prepend-icon="mdi-office-building" small-chips
                                v-model="participants"/>

                    <v-textarea dense label="Description" outlined
                                prepend-icon="mdi-note-text-outline" rows="4"
                                v-model="description"/>
                </v-card-text>

                <v-card-actions>
                    <v-btn @click="close" color="success" text>Cancel</v-btn>
                    <v-spacer/>
                    <v-btn @click="remove" color="success" text v-if="!!this.edit">Delete</v-btn>
                    <v-spacer/>
                    <v-btn :disabled="!participantsValid || !dateValid" @click="save" color="success" text>Save
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
  import dateformat from 'dateformat'

  export default {
    name: 'cl-dialog-competition',

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
            this.$http.get('/competitions/' + this.edit).then(response => {
              this.dates = [dateformat(response.data.begins, 'yyyy-mm-dd'), dateformat(response.data.ends, 'yyyy-mm-dd')]
              this.title = response.data.title
              this.participants = response.data.participants
              this.description = response.data.description
              this.validate()
            })
          }
        }
      }
    },

    data: () => ({
      dateValid: false,
      participantsValid: false,
      titleValid: false,
      title: '',
      dates: [],
      participants: [],
      description: '',
      offices: []
    }),

    methods: {
      presentAndFuture (date) {
        const now = new Date(new Date(Date.now()).toDateString())
        return new Date(date) >= now
      },

      validate () {
        // date
        if (!!this.dates[0] && !!this.dates[1]) {
          const begins = new Date(this.dates[0])
          const ends = new Date(this.dates[1])

          this.dateValid = ends >= begins
        } else {
          this.dateValid = false
        }

        // participants
        this.participantsValid = this.participants.length >= 2

        this.titleValid = !!this.title
      },

      clear () {
        this.dates = [dateformat(new Date(), 'yyyy-mm-dd'), dateformat(new Date(), 'yyyy-mm-dd')]
        this.title = ''
        this.participants = []
        this.description = ''
      },

      close () {
        this.show = false
        this.clear()
      },

      remove () {
        this.$http.delete('/competitions/' + this.edit).then(() => {
          this.show = false
          this.clear()
          this.$emit('persist')
        })
      },

      save () {
        const competition = {
          begins: this.dates[0],
          ends: this.dates[1],
          title: this.title,
          participants: this.participants,
          description: this.description,
        }

        if (!!this.edit) {
          this.$http.put('/competitions/' + this.edit, competition).then(() => {
            this.show = false
            this.clear()
            this.$emit('persist')
          })
        } else {
          this.$http.post('/competitions', competition).then(() => {
            this.show = false
            this.clear()
            this.$emit('persist')
          })
        }
      },
    }
    ,

    created () {
      this.$http.get('/offices').then(response => {
        this.offices = response.data
      })
    }
    ,
  }

</script>

<style scoped>
</style>