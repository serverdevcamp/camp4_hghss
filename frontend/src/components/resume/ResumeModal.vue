<template>
  <modal
    name="resume-modal"
    transition="pop-out"
    :height="380"
    :width="300"
    @before-open="beforeOpen"
  >
    <div class="modal-content">
      <div class="title point-font"> 응 난 제목</div>

    </div>
  </modal>
</template>
<script>
export default {
  data() {
    return {
    };
  },
  methods: {
    beforeOpen(event) {
      this.target = event.params.target;
    }
  }
};
</script>
<style lang="scss">

</style>