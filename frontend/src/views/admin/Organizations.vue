<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col class="ma-0 pa-0 " lg="6" sm="3">

            <v-card :elevation="(this.$vuetify.breakpoint.smAndUp ? '1' : '0')" class="two">

                <v-data-table :headers="headers" :items="items">
                    <template v-slot:item.offices="{ item }">
                        <v-chip :key="office.id" @click:close="removeOffice(office)" close
                                v-for="office in item.offices"
                                x-small>{{office.name}}
                        </v-chip>
                    </template>

                    <template v-slot:item.actions="{ item }">
                        <v-icon @click="edit(item)" class="mr-2" disabled small>
                            mdi-pencil
                        </v-icon>
                        <v-icon @click="remove(item)" small>
                            mdi-delete
                        </v-icon>
                    </template>

                </v-data-table>

                <v-alert color="warning" dark dense v-if="error">{{errorText}}</v-alert>

            </v-card>

        </v-col>
    </v-row>
</template>

<script>
  import dateformat from 'dateformat'

  export default {
    name: 'Organizations',

    data: () => ({
      error: false,
      errorText: '',
      headers: [
        {
          text: 'Id',
          align: 'start',
          value: 'id',
        },
        {
          text: 'Name',
          align: 'start',
          value: 'name',
        },
        {
          text: 'Domain',
          align: 'start',
          value: 'domain',
        },
        {
          text: 'Offices',
          align: 'start',
          value: 'offices',
        },
        {
          text: 'Actions',
          align: 'start',
          value: 'actions',
        },
      ],
      items: [],
    }),

    mounted () {
      this.refresh()
    },

    methods: {
      refresh () {
        this.$http.get('/organizations').then(response => {
          this.items = response.data
        })
      },

      date (date) {
        return dateformat(new Date(date), 'yyyy-mm-dd')
      },

      edit (item) {
        // TODO implement
        this.error = false
        this.errorText = ''
      },

      remove (item) {
        this.$http.delete('/organizations/' + item.id).then(response => {
          this.refresh()
          this.error = false
        }).catch(error => {
          this.error = true
          this.errorText = error.response.data.message
        })
      },

      removeOffice (office) {
        this.$http.delete('/offices/' + office.id).then(response => {
          this.refresh()
          this.error = false
        }).catch(error => {
          this.error = true
          this.errorText = error.response.data.message
        })
      }
    }

  }
</script>

<style scoped>
</style>