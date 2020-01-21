<template>
  <div id="home">
    <section class="image-banner">
       <ImageBanner />
    </section>
    <section class="rank">
      <Rank />
    </section>
  </div>
</template>

<script>
import ImageBanner from '@/components/hghss/ImageBanner'
import Rank from '@/components/hghss/Rank'

export default {
  name: 'HghssPage',
  components : {  
    ImageBanner,
    Rank
  },
  data: () => ({
  }),
};
</script>
<style lang="scss">
#home{
  .image-banner{
    height: 350px;
  }
  .rank{
    height: calc(100vh - 400px);
  }
}
</style>