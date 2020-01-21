<template>
  <v-container class="rank-card-wrapper">
    <v-row align="center">
      <v-col v-for="(rank, index) in ranks" v-bind:key="index" class="rank-card" cols="12" sm="5" md="3" lg="2">
        <v-row class="img-wrapper">
          <div class="img-wrap">
            <img :src="require('../../assets/images/'+index+'-prize.png')" alt="" />
          </div>
        </v-row>
        <v-row>
          <p class="company">{{rank.company}}</p>
        </v-row>
        <v-row>
          <p class="description">
            {{rank.count}}명 | {{rank.remainingDays}}일 남음
          </p>
        </v-row>
        <v-row>
          <p>
            <button class="round-btn point-font">공고 확인</button>
          </p>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
export default {
    props: ['ranks'],
  data: () => ({
  }),
};
</script>
<style lang="scss">
.container.rank-card-wrapper{
  margin-top:5%;
  padding:0 50px;
  
  .rank-card{
    margin:15px;
    padding:30px 3%;
    background: #fafafa;;
    border-radius: 10px;
    box-shadow: 5px 5px 5px -6px #797979;
    .img-wrapper{
      display: block;
      text-align: center;
      .img-wrap{
        height:45px;
        img{
          height:100%;
        }
      }
    }
    p{
      width:100%; 
      text-align: center;
    }
    .company{
      margin:20px 0 5px;
      font-size: 0.95rem;
      font-weight: 600;
      text-align: center;
    }
    .description{
      color:#797979;
      font-size: 0.8rem;
      font-weight: 600;
    }
    .round-btn{
      margin-top:30px;
      padding:5px 10px;
      border-radius: 50px;
      background: #ddd;//rgba(145,156,170,1);
      color:#676767;
      font-size: 0.85rem;
      // font-weight: 600;
      &:hover{
        background: #f4d569;
          // color: #131417;
      }
    }
  }
}
</style>