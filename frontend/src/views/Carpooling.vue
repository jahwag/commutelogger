<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col lg="4" sm="5" xs="12">
            <cl-dialog-carpooling :edit="dialogId" v-model="dialog" v-on:persist="refreshData"/>

            <cl-card-list-crud :loading="loading" :mutable="true" class="mobile-margin" pk="id"
                               v-model="rides" v-on:add="onAdd">
                <template v-slot:loading>
                    <cl-loading-indicator/>
                </template>

                <template v-slot:no-data-header>
                    No opportunities have been posted
                </template>
                <template v-slot:no-data>
                    <p align="center">Check back later, or be the first
                        to
                        post!</p>
                </template>

                <template v-slot:header="item">
                    <v-toolbar-title>
                        {{item.where.departure}} to {{item.where.destination}}
                    </v-toolbar-title>
                    <v-spacer/>
                    <v-btn @click="edit(item.id)" icon v-if="item.authorEmail === $store.state.me.email">
                        <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                </template>

                <template v-slot:default="item">
                    <v-card-text>

                        <v-row no-gutters>
                            <v-col>
                                <div>
                                    <v-text-field dense label="Free seats" readonly v-model="item.how.seats"/>
                                </div>
                                <div>
                                    <v-text-field dense label="Cost sharing" readonly
                                                  v-model="item.how.costSharing"/>
                                </div>
                            </v-col>
                            <v-col>
                                <div>
                                    <v-text-field dense label="Detour flexibility" readonly
                                                  v-model="item.how.detourFlexibility"/>
                                </div>
                                <div>
                                    <v-text-field dense label="Accepts detours inside tolls" readonly
                                                  v-model="item.how.tolls ? 'Yes' :'No'"/>

                                </div>
                            </v-col>
                        </v-row>

                        <v-row no-gutters>
                            <v-col>
                                <v-chip :key="day" class="ma-1" color="secondary"
                                        v-for="day in days(item)">{{day}}
                                </v-chip>
                            </v-col>
                        </v-row>

                        <v-row>
                            <v-col>
                                <span style="white-space: pre-line">{{item.comments}}</span>
                            </v-col>
                        </v-row>
                        <v-divider/>


                        <v-row no-gutters>
                            <v-col>
                                Posted by <a :href=email(item)>{{item.authorEmail}}</a>
                            </v-col>
                        </v-row>

                        <v-row no-gutters>
                            <v-col>
                                <div>{{item.authorOffice}} {{item.lastModified}}</div>
                            </v-col>
                        </v-row>

                    </v-card-text>
                </template>
            </cl-card-list-crud>

        </v-col>
    </v-row>
</template>

<script>
  import ClDialogCarpooling from '../components/DialogCarpooling'
  import ClCardListCrud from '../components/CardListCrud'

  export default {
    name: 'Carpooling',
    components: { ClCardListCrud, ClDialogCarpooling },

    data: () => ({
      rides: [],
      loading: false,
      dialogId: null,
      dialog: false,
    }),

    methods: {
      refreshData () {
        this.$http.get('/carpoolings/').then(response => {
          this.rides = response.data
        })
      },

      comments (item) {
        return !!item.comments ? item.comments.replace('\n', '\n\n') : ''
      },

      email (item) {
        return 'mailto:' + item.authorEmail
      },

      days (item) {
        const arr = []

        if (item.when.monday) {
          arr.push('Mon')
        }
        if (item.when.tuesday) {
          arr.push('Tue')
        }
        if (item.when.wednesday) {
          arr.push('Wed')
        }
        if (item.when.thursday) {
          arr.push('Thu')
        }
        if (item.when.friday) {
          arr.push('Fri')
        }

        return arr
      },

      onAdd () {
        this.dialog = true
        this.dialogId = null
      },

      edit (id) {
        this.dialog = true
        this.dialogId = id
      },
    },

    mounted () {
      this.refreshData()
    }

  }
</script>

<style scoped>
    .add {
        position: fixed;
        top: 56px;
        right: 12px;
        z-index: 100;
    }

    .add-mobile {
        position: fixed;
        top: 56px;
        right: 12px;
        z-index: 100;
    }


</style>