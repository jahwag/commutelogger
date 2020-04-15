<template>
    <v-row align="center" class="ma-0 pa-0 fill-height" justify="center">
        <v-col sm="5" xs="12">
            <cl-dialog-competition :edit="dialogId" v-model="dialog" v-on:persist="refreshData"/>

            <cl-card-list-crud :edit="dialogId" :loading="loading" :mutable="$store.state.me.admin"
                               class="mobile-margin" pk="id"
                               v-model="competitions" v-on:add="onAdd">
                <template v-slot:loading>
                    <cl-loading-indicator/>
                </template>

                <template v-slot:no-data-header>
                    Nothing to see here
                </template>
                <template v-slot:no-data>
                    No competitions have been posted.
                </template>

                <template v-slot:header="item">
                    <v-toolbar-title>
                        {{item.title}}
                    </v-toolbar-title>
                    <v-spacer/>
                    <div class="overline">{{item.status}}</div>
                    <v-btn @click="edit(item.id)" icon v-if="$store.state.me.admin">
                        <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                </template>

                <template v-slot:default="item">
                    <v-col>
                        <v-card-text>
                            <b>Participants</b>
                            <p>{{item.participants.join(' v. ')}}</p>
                            <b>Period</b>
                            <p>{{item.begins}} &rarr; {{item.ends}}</p>
                            <span style="white-space: pre-line">{{item.description}}</span>
                        </v-card-text>
                    </v-col>
                </template>
            </cl-card-list-crud>

        </v-col>
    </v-row>
</template>

<script>
  import dateformat from 'dateformat'
  import ClDialogCompetition from '../components/DialogCompetition'
  import ClCardListCrud from '../components/CardListCrud'
  import ClLoadingIndicator from '../components/LoadingIndicator'

  export default {
    name: 'Competitions',
    components: { ClCardListCrud, ClLoadingIndicator, ClDialogCompetition },

    data: () => ({
      loading: false,
      dialog: false,
      dialogId: null,
      competitions: [],
      full: false,
    }),

    mounted () {
      this.refreshData()
    },

    methods: {
      onAdd () {
        this.dialog = true
        this.dialogId = null
      },

      edit (id) {
        this.dialog = true
        this.dialogId = id
      },

      refreshData () {
        this.loading = true

        this.$http.get('/competitions', { params: { full: this.full } }).then(response => {
          const data = response.data

          for (let j = 0; j < data.length; j++) {
            data[j] = {
              id: data[j].id,
              title: data[j].title,
              status: this.title(data[j]),
              participants: data[j].participants,
              begins: dateformat(data[j].begins.join('-'), 'yyyy-mm-dd'),
              ends: dateformat(data[j].ends.join('-'), 'yyyy-mm-dd'),
              description: data[j].description,
              color: function () {
                switch (status) {
                  case 'Ended':
                    return 'secondary darken-1'
                  case 'Ongoing':
                    return 'secondary'
                  default:
                    return 'secondary lighten-1'
                }
              },
            }
          }

          this.competitions = data
          this.loading = false
        })
      },

      title: function (competition) {
        const begins = new Date(competition.begins)
        const ends = new Date(competition.ends)
        const now = new Date(new Date(Date.now()).toDateString())

        if (now < begins) {
          return 'Upcoming'
        }

        return now > ends ? 'Ended' : 'Ongoing'
      },
    },

  }

</script>

<style scoped>
    .switch {
        position: fixed;
        top: 32px;
        left: 12px;
    }
</style>