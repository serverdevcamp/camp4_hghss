<template>
  <v-row class="rank-wrapper">
    <v-col class="rank-item" cols="4">
      <div class="item">
        <v-row class="title">
          <p class="point-font">즐겨찾기 TOP 5</p>
        </v-row>
        <v-row>
          <v-col class="content">
            <v-row v-for="(item, index) in getRank.like" :key="index" class="rank-data">
              <v-col cols="3">
                <p class="point-font order">{{ index+1 }}</p>
              </v-col>
              <v-col>
                <v-row>
                  <p class="point-font company-name">{{item.companyName}}</p>
                </v-row>
                <v-row>
                  <p class="info">{{ remainingDays(item.endTime) }}일 남음 | 즐겨찾기 {{item.count}}개</p>
                </v-row>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </div>
    </v-col>
    <v-col class="rank-item" cols="4">
      <div class="item">
        <v-row class="title">
          <p class="point-font">조회수 TOP 5</p>
        </v-row>
        <v-row>
          <v-col class="content">
            <v-row v-for="(item, index) in getRank.visit" :key="index" class="rank-data">
              <v-col cols="3">
                <p class="point-font order">{{ index+1 }}</p>
              </v-col>
              <v-col>
                <v-row>
                  <p class="point-font company-name">{{item.companyName}}</p>
                </v-row>
                <v-row>
                  <p class="info">{{ remainingDays(item.endTime) }}일 남음 | {{item.count}}명 작성</p>
                </v-row>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </div>
    </v-col>
    <v-col class="rank-item" cols="4">
      <div class="item">
        <v-row class="title">
          <p class="point-font">지원수 TOP 5</p>
        </v-row>
        <v-row>
          <v-col class="content">
            <v-row v-for="(item, index) in getRank.apply" :key="index" class="rank-data">
              <v-col cols="3">
                <p class="point-font order">{{ index+1}}</p>
              </v-col>
              <v-col>
                <v-row>
                  <p class="point-font company-name">{{item.companyName}}</p>
                </v-row>
                <v-row>
                  <p class="info">{{ remainingDays(item.endTime) }}일 남음 | {{item.count}}명 작성</p>
                </v-row>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </div>
    </v-col>
</v-row>
</template>
<script>
import { mapGetters, mapActions } from "vuex";
export default {
  data: () => ({
    active: 1
  }),
  computed: {
    ...mapGetters(["getRank"]),
  },
  methods: {
    ...mapActions(["rankAPI"]),
    remainingDays(date){
      var today = new Date()
      var split_date = date.split('-')
      var target = new Date(split_date[0],  split_date[1]-1, split_date[2])
      return parseInt((target.getTime()-today.getTime()) / (1000*60*60*24))
    }
  },
  created(){
   if(!this.getRank.hasOwnProperty('like')){
     this.rankAPI({path : 'like'})
   }
   if(!this.getRank.hasOwnProperty('visit')){
     this.rankAPI({path : 'visit'})
   }
   if(!this.getRank.hasOwnProperty('apply')){
     this.rankAPI({path : 'apply'})
   }
  }
};
</script>
<style lang="scss">
$calendar-border: #f0f0f0;
$calendar-day: #bbbbbb;
$calendar-title: #fafafa;
.row.rank-wrapper {
  height: 100%;
  padding: 20px 10%;
  margin: 0;
  .col,
  .row {
    padding: 0;
    margin: 0;
  }
  .col.rank-item {
    overflow: hidden;
    padding: 20px 3%;
    .item {
      padding: 30px 15px;
      border: 1px solid #ddd;
      border-radius: 5px;
    }
    p {
      width: 100%;
    }
    .title {
      width: 100%;
      margin-bottom: 20px;
      p {
        text-align: center;
        font-size: 1.3rem;
      }
    }
    .content {
      .rank-data{
        padding: 10px 0;
      }
      .order {
        padding-top: 10px;
        text-align: center;
        color: $calendar-day;
        font-size: 0.9rem;
        font-weight: 700;
      }
      .company-name {
        color: #555555;
        font-size: 1.05rem;
      }
      .info {
        margin-top: 5px;
        font-size: 0.75rem;
        color: #777;
        font-weight: 600;
      }
    }
  }
}
</style>