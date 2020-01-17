<template>
  <v-carousel 
    cycle
    height="350"
    :show-arrows="false"
    hide-delimiter-background
  >
    <v-carousel-item
      v-for="(item,i) in items"
      :key="i"
      :src="item.src"
    ></v-carousel-item>
  </v-carousel>
</template>
<script>
  export default {
    data () {
      return {
        items: [
          {
            src: require('../../assets/banner/banner1.png'),
          },
          {
            src: require('../../assets/banner/banner2.png'),
          },
        ],
      }
    },
  }
</script>