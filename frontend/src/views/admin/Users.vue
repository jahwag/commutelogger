<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col class="ma-0 pa-0 " lg="6" sm="3">

            <v-card :elevation="(this.$vuetify.breakpoint.smAndUp ? '1' : '0')" class="two">

                <v-data-table :headers="headers" :items="items">
                    <template v-slot:item.admin="{ item }">
                        <v-simple-checkbox disabled v-model="item.admin"/>
                    </template>

                    <template v-slot:item.created="{ item }">
                        {{date(item.created)}}
                    </template>

                    <template v-slot:item.enabled="{ item }">
                        <v-simple-checkbox disabled v-model="item.enabled"/>
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
    name: 'Users',

    data: () => ({
      error: false,
      errorText: '',
      headers: [
        {
          text: 'Email',
          align: 'start',
          sortable: false,
          value: 'email',
        },
        {
          text: 'Organization',
          align: 'start',
          sortable: false,
          value: 'organization',
        },
        {
          text: 'Office',
          align: 'start',
          value: 'office',
        },
        {
          text: 'Admin',
          align: 'start',
          value: 'admin',
        },
        {
          text: 'Created',
          align: 'start',
          value: 'created',
        },
        {
          text: 'Enabled',
          align: 'start',
          value: 'enabled',
        },
        {
          text: 'Actions',
          align: 'start',
          value: 'actions'
        },
      ],
      items: [],
    }),

    mounted () {
      this.refresh()
    },

    methods: {
      refresh () {
        this.$http.get('/accounts').then(response => {
          this.items = response.data
        })
      },

      date (date) {
        return dateformat(new Date(date), 'yyyy-mm-dd')
      },

      edit (item) {
        // TODO implement
        this.error = false
      },

      remove (item) {
        this.$http.delete('/accounts/' + item.email).then(response => {
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