<template>
  <v-row id="recruit">
    <v-col cols="10">
      <Calendar/>
    </v-col>
    <v-col></v-col>
  </v-row>
</template>

<script>
import Calendar from '@/components/recruit/Calendar'
export default {
  name: "HghssPage",
  components: {
    Calendar
  },
  data: () => ({}),
};
</script>
<style lang="scss">
#recruit{
  .row, .col{
    padding: 0;
    margin:0;
  }
}
</style>