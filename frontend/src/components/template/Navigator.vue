<template>
<div id="navigator">
    <div class="nav-section">
        <div class="point-font nav-home">합격하소서 🙏</div>
        <div class="nav-btn">채용 공고</div>
        <div class="nav-btn">자기소개서</div>
    </div>
    <div class="nav-section">
        <div class="nav-btn">로그인</div>
    </div>
</div>
</template>
<script>
  export default {
    data () {
      return {
      }
    },
  }
</script>
<style lang="scss">
#navigator{
    height: 50px;
    .nav-section, .nav-home, .nav-btn{
        display: inline-block;
    }
    .nav-section{
        padding: 0 30px;
        &:nth-child(1){
            width:70%;
        }
        &:nth-child(2){
            text-align: right;
            width:30%;
        }
    }
    .nav-home{
        cursor: pointer;
        padding: 8px 20px 0 0;
        font-size: 1.2rem;
    }
    .nav-btn{
        cursor: pointer;
        padding: 15px 10px 0;
        font-size: 0.85rem;
        font-weight: 600;
    }

}
</style>