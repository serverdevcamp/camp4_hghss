<template>
  <v-row class="rank-wrapper" align="center">
    <v-col class="rank-title" cols="3">
      <v-row>
        <p class="title point-font" 
          @click="active = 1"
          :class="{active : active == 1}"
          > 즐겨찾기 TOP 5</p>
      </v-row>
      <v-row>
        <p class="title point-font" 
          @click="active = 2"
          :class="{active : active == 2}"
          >조회수 TOP 5</p>
      </v-row>
      <v-row>
        <p class="title point-font" 
          @click="active = 3"
          :class="{active : active == 3}"
          >지원수 TOP 5</p>
      </v-row>
    </v-col>
    <v-col>
      <transition name="rank-item">
        <v-row v-if="active == 1" class="item">
          <RankItem v-bind:ranks="$store.state.starRank"/>
        </v-row>
      </transition>
      <transition name="rank-item">
        <v-row v-if="active == 2" class="item">
          <RankItem v-bind:ranks="$store.state.visitedRank"/>
        </v-row>
      </transition>
      <transition name="rank-item">
        <v-row v-if="active == 3" class="item">
          <RankItem v-bind:ranks="$store.state.ApplicantRank"/>
        </v-row>
      </transition>
    </v-col>
  </v-row>
</template>
<script>
import RankItem from './RankItem'

export default {
  components :{
    RankItem
  },
  data: () => ({
    active : 1,
    
  }),
  methods: {
    
  },
};
</script>
<style lang="scss">
.row.rank-wrapper {
  height: 100%;
  margin: 0;
  .rank-title {
    .title {
      cursor: pointer;
      margin: 2vh -10px;
      padding: 3.5vh 50px 3vh 30px;
      background: rgb(244, 213, 105);
      border-top-right-radius: 50px;
      border-bottom-right-radius: 50px;
      color: #131417;
      font-size: 1.2rem;
      text-align: center;

      &.active{
        cursor: default;
        background: rgb(106, 60, 185);
        color: #ffffff; 
        transform: translateX(10px);
      }
      &:hover{
        transform: translateX(10px);
      }
    }
  }
  .item.row{
    height:100%;
  }

  .rank-item-enter-active{
    transition: all ease 1s;
    transform: translateX(0px)
  }
  .rank-item-enter {
    opacity: 0;
    transform: translateX(50px)
  }
}
</style>