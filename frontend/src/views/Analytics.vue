<template>
    <v-row align="center" class="ma-0 pa-0" justify="center">
        <v-col lg="4" sm="5" xs="12">

            <v-card :key="competition.id" style="margin-bottom: 8px" v-for="competition in competitions">
                <bar-chart :chart-data="competition" :options="competition.options" class="charts"/>
            </v-card>

            <v-card>
                <bar-chart :chart-data="organization" :options="options" class="charts"/>
            </v-card>

        </v-col>
    </v-row>
</template>

<script>
  import BarChart from '../components/BarChart'

  export default {
    name: 'Analytics',
    components: {
      BarChart
    },

    data () {
      return {
        competitions: [],
        organization: {},
        options: {
          title: {
            display: true,
            text: [this.$store.state.me.organization, 'CO₂ emissions from commuting']
          },
          scales: {
            xAxes: [{
              type: 'time',
              distribution: 'linear',
              time: {
                unit: 'day'
              }
            }],
            yAxes: [{
              type: 'logarithmic',
              scaleLabel: {
                display: true,
                labelString: 'kg CO₂',
              },
              ticks: {
                min: 1,
                max: 1000,
                callback: function (value) {
                  return Number(value.toString())
                }
              }
            }]
          }
        }
      }
    },

    mounted () {
      let competitionsVariables = { params: { full: false } }

      this.$http.get('/competitions', competitionsVariables).then(response => {
        for (const competition of response.data) {
          if (Date.now() >= new Date(competition.begins)) {
            this.$http.get('/competitions/' + competition.id + '/co2').then(response => {
              const data = response.data
              data.title = competition.title

              const options = Object.assign({}, this.options)
              options.title.text = [competition.title, 'CO₂ emissions from commuting']
              data.options = options

              this.competitions.push(data)
            })
          }
        }
      })

      this.$http.get('/me/organization/co2').then(response => {
        this.organization = response.data
      })
    }
  }

</script>

<style scoped>
    .charts {
        height: 100%;
        width: 100%;
    }
</style>