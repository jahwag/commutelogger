<template>
    <div id="loading-indicator">
        <v-progress-circular color="accent" indeterminate/>
        <p class="overline">Loading</p>
    </div>

</template>

<script>
  export default {
    name: 'cl-loading-indicator',
  }
</script>

<style scoped>
    #loading-indicator {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        text-align: center;
    }
</style>