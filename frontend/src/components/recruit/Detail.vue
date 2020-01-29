<template>
  <modal
    id="company-modal"
    name="company-modal"
    transition="pop-out"
    :height="700"
    :width="700"
    @before-open="beforeOpen"
  >
    <div class="recruit-header">
      <v-row class="company">
        <v-col cols="3">
          <div class="company-logo">
            <img :src="recruit.imageFileName" :alt="recruit.companyName" />
          </div>
        </v-col>
        <v-col>
          <v-row class="company-info">
            <p class="point-font company-name">
              {{ recruit.companyName }}
              <font-awesome-icon icon="star" class="{fav: recruit.favorite}" />
            </p>
          </v-row>
          <v-row class="recruit-date">
            <p>
              {{ recruit.startTime }} ~ {{ recruit.endTime }}
              <!-- TODO : 날짜 계산 -->
            </p>
          </v-row>
          <v-row>
            <button class="link-btn">홈페이지</button>
            <button class="link-btn">채용 공고 공유</button>
          </v-row>
          <v-row class="view-point">
            <span>공고 조회 {{ recruit.viewCount }}회</span>
            <span>|</span>
            <span>즐겨찾기 {{ recruit.favoriteCount }}회</span>
          </v-row>
        </v-col>
      </v-row>
      <div class="recruit-content" v-html="recruit.content">
      </div>
    </div>
  </modal>
</template>
<script>
import config from "../../store/config";
import axios from "axios";
export default {
  data: () => ({
    company: {},
    recruit: {}
  }),
  methods: {
    beforeOpen(event) {
      this.company = event.params.company;
      this.getRecruit();
    },
    getRecruit() {
      axios({
        method: "get",
        url: config.RECRUIT_HOST + "/recruits/detail/" + this.company.recruitId,
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*"
        }
      }).then(response => {
        if (response.data.status == 200) {
          this.recruit = response.data.data;
          console.log(this.recruit);
          return true;
        }
        console.log(response.data.message);
        return false;
      });
    }
  }
};
</script>
<style lang="scss">
$calendar-border: #f0f0f0;
$calendar-day: #bbbbbb;
$calendar-title: #fafafa;
$star1: #dadada;
$star2: #f4d569;
$end: #3f4b5e;
#company-modal {
  // modal wrapper
  .v--modal {
    border-radius: 8px;
  }
  .recruit-header {
    background: $calendar-border;
    padding: 30px;
    .company {
      padding: 30px;
      border-radius: 3px;
      background: #ffffff;
      .company-logo {
        margin-right: 30px;
        height: 100%;
        position: relative;
        overflow: hidden;
        img {
          position: absolute;
          max-width: 100%;
          max-height: 100%;
          height: auto;
          width: auto;
          margin: auto;
          top: 0;
          bottom: 0;
          right: 0;
          left: 0;
        }
      }
      .company-name {
        font-size: 2rem;
        svg {
          cursor: pointer;
          font-size: 1.5rem;
          path {
            font-size: 1.5rem;
            color: $star1;
          }
          &.fav {
            path {
              color: $star2;
            }
          }
        }
      }
      .recruit-date {
        margin: 10px 0 22px;
        p {
          font-size: 0.95rem;
          color: #707070;
        }
      }
      .link-btn {
        min-width: 25%;
        margin-right: 15px;
        padding: 10px;
        color: #999999;
        font-size: 0.85rem;
        border: 1px solid #ddd;
        &:hover {
          background-color: #fafafa;
        }
      }
      .view-point {
        margin-top: 15px;
        span {
          font-size: 0.85rem;
          font-weight: 500;
          color: #707070;
          &:nth-child(2) {
            margin: 0 15px;
          }
        }
      }
    }
  }
}
</style>
