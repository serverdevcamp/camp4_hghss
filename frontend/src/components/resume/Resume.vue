<template>
  <section id="resume">
    <v-row class="resume-header">
      <v-col class="title point-font">2020 상반기 자기소개서 🌸</v-col>
      <v-col>
        <!-- 버튼 넣을 곳 -->
      </v-col>
    </v-row>
    <v-row class="drag-drop-section">
      <v-col class="resume-items-1">
        <v-row>
          <p class="title point-font">자기소개 작성 지원</p>
        </v-row>
        <v-row>
          <p class="subtitle point-font">작성 중</p>
          <div
            id="box1-1"
            class="drop-box"
            v-on:drop="drop($event)"
            v-on:dragover="allowDrop($event)"
          >
            <!-- CARD -->
            <div
              class="resume-card"
              draggable="true"
              v-on:dragstart="drag($event)"
              v-for="(resume, index) in getResume['1']['1']"
              :key="index"
              :id="resume.recruitId+'_'+resume.id"
            >
              <div class="card" v-on:click="showModal(resume)">
                <div class="company">{{resume.title}}</div>
                <div class="date" v-if="new Date(resume.endTime) > today">
                  <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                  {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                  {{ resume.endTime.substring(11,16)}}
                </div>
                <div class="date-alert" v-else>
                  <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                </div>
              </div>
            </div>
          </div>
        </v-row>
        <v-row>
          <p class="subtitle point-font">제출완료</p>
          <div
            id="box1-2"
            class="drop-box"
            v-on:drop="drop($event)"
            v-on:dragover="allowDrop($event)"
          >
            <!-- CARD -->
            <div
              class="resume-card"
              draggable="true"
              v-on:dragstart="drag($event)"
              v-for="(resume, index) in getResume['1']['2']"
              :key="index"
              :id="resume.recruitId+'_'+resume.id"
            >
              <div class="card" v-on:click="showModal(resume)">
                <div class="company">{{resume.title}}</div>
                <div class="date" v-if="new Date(resume.endTime) > today">
                  <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                  {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                  {{ resume.endTime.substring(11,16)}}
                </div>
                <div class="date-alert" v-else>
                  <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                </div>
              </div>
            </div>
          </div>
        </v-row>
        <v-row>
          <p class="subtitle point-font">미제출</p>
          <div
            id="box1-3"
            class="drop-box"
            v-on:drop="drop($event)"
            v-on:dragover="allowDrop($event)"
          >
            <!-- CARD -->
            <div
              class="resume-card"
              draggable="true"
              v-on:dragstart="drag($event)"
              v-for="(resume, index) in getResume['1']['3']"
              :key="index"
              :id="resume.recruitId+'_'+resume.id"
            >
              <div class="card" v-on:click="showModal(resume)">
                <div class="company">{{resume.title}}</div>
                <div class="date" v-if="new Date(resume.endTime) > today">
                  <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                  {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                  {{ resume.endTime.substring(11,16)}}
                </div>
                <div class="date-alert" v-else>
                  <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                </div>
              </div>
            </div>
          </div>
        </v-row>
      </v-col>

      <v-col class="resume-items-2">
        <v-row>
          <c-col class="col-items">
            <v-row>
              <p class="title point-font">서류전형</p>
            </v-row>
            <v-row>
              <p class="subtitle point-font">합격</p>
              <div
                id="box2-1"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              >
                <!-- CARD -->
                <div
                  class="resume-card"
                  draggable="true"
                  v-on:dragstart="drag($event)"
                  v-for="(resume, index) in getResume['2']['1']"
                  :key="index"
                  :id="resume.recruitId+'_'+resume.id"
                >
                  <div class="card" v-on:click="showModal(resume)">
                    <div class="company">{{resume.title}}</div>
                    <div class="date" v-if="new Date(resume.endTime) > today">
                      <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                      {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                      {{ resume.endTime.substring(11,16)}}
                    </div>
                    <div class="date-alert" v-else>
                      <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                    </div>
                  </div>
                </div>
              </div>
            </v-row>
            <v-row>
              <p class="subtitle point-font">불합격</p>
              <div
                id="box2-2"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              >
                <!-- CARD -->
                <div
                  class="resume-card"
                  draggable="true"
                  v-on:dragstart="drag($event)"
                  v-for="(resume, index) in getResume['2']['2']"
                  :key="index"
                  :id="resume.recruitId+'_'+resume.id"
                >
                  <div class="card" v-on:click="showModal(resume)">
                    <div class="company">{{resume.title}}</div>
                    <div class="date" v-if="new Date(resume.endTime) > today">
                      <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                      {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                      {{ resume.endTime.substring(11,16)}}
                    </div>
                    <div class="date-alert" v-else>
                      <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                    </div>
                  </div>
                </div>
              </div>
            </v-row>
          </c-col>
          <c-col class="col-items">
            <v-row>
              <p class="title point-font">1차 전형</p>
            </v-row>
            <v-row>
              <p class="subtitle point-font">합격</p>
              <div
                id="box3-1"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              >
                <!-- CARD -->
                <div
                  class="resume-card"
                  draggable="true"
                  v-on:dragstart="drag($event)"
                  v-for="(resume, index) in getResume['3']['1']"
                  :key="index"
                  :id="resume.recruitId+'_'+resume.id"
                >
                  <div class="card" v-on:click="showModal(resume)">
                    <div class="company">{{resume.title}}</div>
                    <div class="date" v-if="new Date(resume.endTime) > today">
                      <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                      {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                      {{ resume.endTime.substring(11,16)}}
                    </div>
                    <div class="date-alert" v-else>
                      <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                    </div>
                  </div>
                </div>
              </div>
            </v-row>
            <v-row>
              <p class="subtitle point-font">불합격</p>
              <div
                id="box3-2"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              >
                <!-- CARD -->
                <div
                  class="resume-card"
                  draggable="true"
                  v-on:dragstart="drag($event)"
                  v-for="(resume, index) in getResume['3']['2']"
                  :key="index"
                  :id="resume.recruitId+'_'+resume.id"
                >
                  <div class="card" v-on:click="showModal(resume)">
                    <div class="company">{{resume.title}}</div>
                    <div class="date" v-if="new Date(resume.endTime) > today">
                      <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                      {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                      {{ resume.endTime.substring(11,16)}}
                    </div>
                    <div class="date-alert" v-else>
                      <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                    </div>
                  </div>
                </div>
              </div>
            </v-row>
          </c-col>
          <c-col class="col-items">
            <v-row>
              <p class="title point-font">2차 전형</p>
            </v-row>
            <v-row>
              <p class="subtitle point-font">합격</p>
              <div
                id="box4-1"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              >
                <!-- CARD -->
                <div
                  class="resume-card"
                  draggable="true"
                  v-on:dragstart="drag($event)"
                  v-for="(resume, index) in getResume['4']['1']"
                  :key="index"
                  :id="resume.recruitId+'_'+resume.id"
                >
                  <div class="card" v-on:click="showModal(resume)">
                    <div class="company">{{resume.title}}</div>
                    <div class="date" v-if="new Date(resume.endTime) > today">
                      <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                      {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                      {{ resume.endTime.substring(11,16)}}
                    </div>
                    <div class="date-alert" v-else>
                      <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                    </div>
                  </div>
                </div>
              </div>
            </v-row>
            <v-row>
              <p class="subtitle point-font">불합격</p>
              <div
                id="box4-2"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              >
                <!-- CARD -->
                <div
                  class="resume-card"
                  draggable="true"
                  v-on:dragstart="drag($event)"
                  v-for="(resume, index) in getResume['4']['2']"
                  :key="index"
                  :id="resume.recruitId+'_'+resume.id"
                >
                  <div class="card" v-on:click="showModal(resume)">
                    <div class="company">{{resume.title}}</div>
                    <div class="date" v-if="new Date(resume.endTime) > today">
                      <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                      {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                      {{ resume.endTime.substring(11,16)}}
                    </div>
                    <div class="date-alert" v-else>
                      <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                    </div>
                  </div>
                </div>
              </div>
            </v-row>
          </c-col>
        </v-row>
      </v-col>

      <v-col class="resume-items-3">
        <v-row>
          <p class="title point-font">3차 전형</p>
        </v-row>
        <v-row>
          <p class="subtitle point-font">합격</p>
          <div
            id="box5-1"
            class="drop-box"
            v-on:drop="drop($event)"
            v-on:dragover="allowDrop($event)"
          >
            <!-- CARD -->
            <div
              class="resume-card"
              draggable="true"
              v-on:dragstart="drag($event)"
              v-for="(resume, index) in getResume['5']['1']"
              :key="index"
              :id="resume.recruitId+'_'+resume.id"
            >
              <div class="card" v-on:click="showModal(resume)">
                <div class="company">{{resume.title}}</div>
                <div class="date" v-if="new Date(resume.endTime) > today">
                  <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                  {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                  {{ resume.endTime.substring(11,16)}}
                </div>
                <div class="date-alert" v-else>
                  <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                </div>
              </div>
            </div>
          </div>
        </v-row>
        <v-row>
          <p class="subtitle point-font">불합격</p>
          <div
            id="box5-2"
            class="drop-box"
            v-on:drop="drop($event)"
            v-on:dragover="allowDrop($event)"
          >
            <!-- CARD -->
            <div
              class="resume-card"
              draggable="true"
              v-on:dragstart="drag($event)"
              v-for="(resume, index) in getResume['5']['2']"
              :key="index"
              :id="resume.recruitId+'_'+resume.id"
            >
              <div class="card" v-on:click="showModal(resume)">
                <div class="company">{{resume.title}}</div>
                <div class="date" v-if="new Date(resume.endTime) > today">
                  <span class="d-day">{{ getDDay(resume.endTime) }}</span>
                  {{ resume.endTime.substring(0,10).replace(/-/gi,'.') }}
                  {{ resume.endTime.substring(11,16)}}
                </div>
                <div class="date-alert" v-else>
                  <font-awesome-icon icon="bell" /> 기간이 지났습니다.
                </div>
              </div>
            </div>
          </div>
        </v-row>
      </v-col>
    </v-row>
    <ResumeModal />
  </section>
</template>
<script>
import ResumeModal from "./ResumeModal";
import { mapGetters, mapActions } from "vuex";
export default {
  components: {
    ResumeModal
  },
  data: () => ({
    today: new Date(),
    // before_col: "",
    // before_row: ""
  }),
  computed: {
    ...mapGetters(["getResume"])
  },
  methods: {
    ...mapActions(["resumeListAPI", "moveResume"]),
    drag(e) {
      e.dataTransfer.setData("text", e.target.id);
      // for (var i in e.path) {
      //   if (e.path[i].classList.contains("drop-box")) {
      //     var col_row = (e.path[i].id + "").substring(3).split("-");
      //     this.before_col = col_row[0];
      //     this.before_row = col_row[1];
      //   }
      // }
    },
    drop(e) {
      e.preventDefault();
      for (var i in e.path) {
        if (e.path[i].classList.contains("drop-box")) {
          var data = e.dataTransfer.getData("text");
          // 움직임 표시
          var resume_id = data.split("_")[1];
          var col_row = (e.path[i].id + "").substring(3).split("-");

          this.moveResume({
            resume_id: resume_id,
            col: col_row[0],
            row: col_row[1],
            // before_col: this.before_col,
            // before_row: this.before_row
          });

          // resumeid를 row, col
          e.path[i].appendChild(document.getElementById(data));
          break;
        }
      }
    },
    allowDrop(e) {
      e.preventDefault();
    },
    showModal(target) {
      this.$modal.show("resume-modal", { target: target });
    },
    getDDay(endDate) {
      var end_date = new Date(endDate);
      var between = end_date - this.today;
      if (between < 0) return "기간이 지났습니다.";

      var d_day = between / (24 * 3600 * 1000);
      if (d_day < 1) {
        return "D-" + parseInt(d_day * 24) + "시";
      }
      return "D-" + parseInt(d_day) + "일";
    }
  },
  mounted() {
    this.resumeListAPI();
  }
};
</script>
<style lang="scss">
$calendar-border: #f0f0f0;
$drop-border: #bbbbbb;
$drop-box: #dddee0;
#resume {
  height: calc(100vh - 50px);
  overflow: hidden;
  background: $calendar-border;
  .row.resume-header {
    padding: 40px 30px 30px;
    .title {
      font-size: 1.5rem;
      letter-spacing: 0.05rem;
    }
  }
  .row.drag-drop-section {
    display: inline-block;
    width: 100%;
    padding: 0 15px;
    .col.resume-items-1,
    .col.resume-items-2,
    .col.resume-items-3 {
      float: left;
      display: inline-block;
      padding: 0 10px 10px;
      background: $drop-box;
      border-radius: 3px;
    }
    .col.resume-items-1 {
      margin: 0 15px;
      width: calc((100% - 60px) / 12 * 4);
      .resume-card {
        width: 50%;
      }
    }
    .col.resume-items-2 {
      width: calc((100% - 60px) / 12 * 6);
      padding: 0 5px 10px;
      .resume-card {
        width: 100%;
      }
    }
    .col.resume-items-3 {
      margin: 0 15px;
      width: calc((100% - 60px) / 12 * 2);
      .resume-card {
        width: 100%;
      }
    }
    .col-items {
      width: calc(100% / 3);
      padding: 0 5px;
    }
    p.title,
    p.subtitle {
      width: 100%;
      padding: 13px 0;
    }
    p.title {
      text-align: center;
      border-bottom: 1.3px solid $drop-border;
    }
    p.subtitle {
      padding-left: 10px;
      font-size: 0.95rem;
      color: #777777;
    }
    // drop-box
    #box2-1,
    #box2-2,
    #box3-1,
    #box3-2,
    #box4-1,
    #box4-2,
    #box5-1,
    #box5-2 {
      .date,
      .date-alert {
        display: none;
      }
    }
    #box1-2, #box1-3{
      .date-alert {
        display: none;
      }
    }
    .drop-box {
      width: 100%;
      min-height: 10vh;
      padding: 0 10px;
      .resume-card {
        overflow: hidden;
        display: inline-block;
        .card {
          cursor: pointer;
          margin: 3px;
          padding: 10px;
          background: #ffffff;
          border-radius: 3px;
          box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.16);
          .company {
            font-size: 0.82rem;
            font-weight: 500;
          }
          .date {
            margin-top: 8px;
            color: #777777;
            font-size: 0.7rem;
            font-weight: 600;
            .d-day {
              padding: 3px 5px;
              margin-right: 5px;
              display: inline-block;
              background: #777777;
              color: #ffffff;
              font-size: 0.65rem;
              border-radius: 3px;
            }
          }
          .date-alert {
            margin-top: 8px;
            padding: 2px 0;
            color: rgb(255, 104, 19);
            font-size: 0.7rem;
            font-weight: 600;
            path {
              color: rgb(255, 104, 19);
            }
          }
        }
      }
    }
  }
}
</style>

