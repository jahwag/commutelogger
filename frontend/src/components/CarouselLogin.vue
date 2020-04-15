<template>
    <v-carousel :show-arrows="false" cycle height="300px">
        <v-carousel-item :key="i" v-for="(slide, i) in slides">
            <v-sheet :color="colors[i]" height="300px" tile width="100%">
                <v-row align="center">
                    <v-col align="center">
                        <v-icon :size="slide.size" class="pa-2" v-if="slide.type === 'icon'">{{slide.icon}}</v-icon>
                        <p class="display-3">{{ slide.title }}</p>
                        <p class="body-1">{{ slide.text }}</p>
                    </v-col>
                </v-row>
            </v-sheet>
        </v-carousel-item>
    </v-carousel>
</template>

<script>

  export default {
    name: 'cl-carousel-login',
    data: () => ({
      colors: [
        'success',
        'success',
        'success',
      ],
      slides: [
        {
          type: 'icon',
          icon: 'mdi-leaf',
          size: 64,
          title: 'Trips',
          text: 'Track the environmental impact of your commuting.'
        },
        {
          type: 'icon',
          icon: 'mdi-office-building',
          size: 64,
          title: 'Compare',
          text: 'View and compare the environmental impact from commuting for your branch office or organization.'
        },
        {
          type: 'icon',
          icon: 'mdi-car-2-plus',
          size: 64,
          title: 'Ridesharing',
          text: 'Find co-workers to share a ride with.'
        },
      ],
    }), components: {}

  }
</script>

<style scoped>
</style>