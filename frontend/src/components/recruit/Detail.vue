<template>
  <modal
    id="company-modal"
    name="company-modal"
    transition="pop-out"
    :width="750"
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
              <font-awesome-icon
                icon="star"
                v-if="recruit.favorite"
                class="fav"
                @click="likeOrUnlike(1)"
              />
              <font-awesome-icon icon="star" v-else @click="likeOrUnlike(0)" />
            </p>
          </v-row>
          <v-row class="recruit-date">
            <p>
              {{ startTime}} 
              ~ {{ endTime }}
              <!-- TODO : 날짜 계산 -->
            </p>
          </v-row>
          <v-row>
            <button class="link-btn" @click="moveUrl(recruit.employmentPageUrl)">홈페이지</button>
            <button class="link-btn">채용 공고 공유</button>
            <button class="add-chat" @click="openChatRoom()">+ 채팅 구독</button>
          </v-row>
          <v-row class="view-point">
            <span>공고 조회 {{ recruit.viewCount }}회</span>
            <span>|</span>
            <span>즐겨찾기 {{ recruit.favoriteCount }}회</span>
          </v-row>
        </v-col>
      </v-row>
      <v-row class="positions">
        <v-row v-for="(employment, index) in recruit.employments" :key="index" class="position">
          <v-col cols="2" class="d-position">{{division[employment.division] }}</v-col>
          <v-col cols="5" class="d-content">{{ employment.field }}</v-col>
          <!-- TODO 몇명 -->
          <v-col cols="2" class="d-cnt">1000명 작성</v-col>
          <v-col cols="3" class="d-btn">
            <button class="resume-btn" @click="writeResume(recruit, employment)">자기소개서 쓰기</button>
            <!-- 자기소개서 질문 목록-->
            <div class="question-hover">
              <v-row v-for="(question, index) in employment.resumeQuestion" :key="index">
                <p class="question">{{ question.questionContent }}</p>
                <p class="limit">({{ question.questionLimit }}자)</p>
              </v-row>
            </div>
          </v-col>
        </v-row>
      </v-row>
    </div>
    <div class="recruit-content" v-html="recruit.content"></div>
  </modal>
</template>
<script>
import config from "../../store/config";
import axios from "axios";
import { mapActions } from "vuex";
export default {
  data: () => ({
    division: [
      "잘못된 포지션입니다.",
      "신입",
      "경력",
      "인턴",
      "계약직",
      "신입/경력",
      "신입/인턴"
    ],
    company: {},
    recruit: {},
    startTime : '',
    endTime: '',
  }),
  methods: {
    ...mapActions(["likeToggle", "addChat", "createResume"]),
    async likeOrUnlike(action) {
      var favorite = await this.likeToggle({
        recruit_id: this.company.recruitId,
        action: action
      });
      this.recruit.favorite = favorite;
      this.company.favorite = favorite;
    },
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
          var s_date = this.recruit.startTime.split("-")
          var e_date = this.recruit.endTime.split("-")

          this.startTime = s_date.slice(0,3).join("-") + " " + s_date.slice(3).join(":")
          this.endTime = e_date.slice(0,3).join("-") + " " + e_date.slice(3).join(":")
          
          return true;
        }
        console.log(response.data.message);
        return false;
      });
    },
    moveUrl(url) {
      window.open(url, "_blank");
    },
    openChatRoom() {
      // 여기손좀
      this.addChat({
        company_id: this.company.company_id,
        company: this.recruit.companyName,
        logo_url: this.recruit.imageFileName
      });
      this.$modal.hide("company-modal");
    },
    writeResume(recruit, employment) {
      if(confirm("자기소개서를 작성하시겠습니까?")){
        var answers = [];
        employment.resumeQuestion.forEach(q => {
          answers.push({
            question_content: q.question_content,
            answer_content: "",
            question_limit: q.question_limit
          });
        });
        var date_split = recruit.endTime.split("-")
        var body = {
          title: recruit.companyName + " " + employment.field,
          endTime: date_split.slice(0,3).join("-") + " " + date_split.slice(3).join(":")+":00",
          answers: answers,
        };
        this.createResume({
          position_id: employment.positionId,
          body: body,
        });
      }
    }
  },
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
  .v--modal-background-click {
    position: absolute;
    overflow: scroll;
    height: 100vh;
  }
  .v--modal {
    height: auto !important;
    top: 100px !important;
    margin-bottom: 100px;
    overflow: hidden;
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
      .link-btn,
      .add-chat {
        min-width: 25%;
        margin-right: 15px;
        padding: 10px;
        font-size: 0.85rem;
      }
      .link-btn {
        color: #999999;
        border: 1px solid #ddd;
        &:hover {
          background-color: #fafafa;
        }
      }
      .add-chat {
        background-color: #ffffff;
        outline: solid 1px #ff6813;
        color: #ff6813;
        &:hover {
          background-color: #ff6813;
          color: #ffffff;
          font-weight: 600;
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
    .positions {
      position: relative;
      margin-top: 20px;
      border-radius: 3px;
      background: #ffffff;
      .position {
        width: 100%;
        border-top: 1px solid #ddd;
        text-align: center;
        &:nth-child(1) {
          border: 0;
        }
        .col {
          padding: 15px 0;
          font-size: 0.9rem;
          font-weight: 500;
          letter-spacing: 0.03rem;
          color: #707070;
        }
        .d-content{
          padding-left: 15px;
          padding-right: 15px;
          text-align: left;
        }
        .d-btn {
          position: relative;
          padding: 0;
          padding-left: 20px;

          .resume-btn {
            height: 100%;
            width: 100%;
            padding: auto 0;
            font-size: 0.85rem;
            background-color: #ffffff;
            outline: solid 1px #ff6813;
            color: #ff6813;
          }
          .question-hover {
            display: none;
            .question,
            .limit {
              display: block;
              width: 100%;
              text-align: left;
              letter-spacing: 0;
            }
          }
          &:hover {
            .question-hover {
              display: block;
              position: absolute;
              background-color: #ffffff;
              border: solid 1px #ccc;
              top: 0;
              left: -481px;
              width: 500px;
              padding: 5px 20px;
              padding-left: 40px;
              .row {
                margin: 15px 0;
              }
              .question {
                margin-bottom: 5px;
                position: relative;
                font-size: 0.8rem;
                &::before {
                  content: "✔️";
                  position: absolute;
                  left: -20px;
                  font-size: 0.7rem;
                }
              }
              .limit {
                font-size: 0.7rem;
                color: #ff6813;
              }
            }
          }
        }
        &:hover {
          background-color: #f5f5f5;
          .resume-btn {
            background: #ff6813;
            color: #ffffff;
            font-weight: 600;
          }
        }
      }
    }
  }
  .recruit-content {
    overflow: hidden;
    p {
      margin: 0;
    }
    img {
      max-width: 100% !important;
    }
  }
}
</style>
