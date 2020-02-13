<template>
  <div>
    <Navigator/>
    <router-view/>
  </div>
</template>

<script>
import Navigator from './components/template/Navigator'
export default {
  name: 'App',
  components : {
    Navigator,
  },
  data: () => ({
    //
  }),
};
</script>
