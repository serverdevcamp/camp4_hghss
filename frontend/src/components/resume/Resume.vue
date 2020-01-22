<template>
  <section id="resume">
    <v-row class="resume-header">
      <v-col class="title point-font">2020 상반기 자기소개서 🌸</v-col>
      <v-col>여기는 버튼들</v-col>
    </v-row>
    <v-row class="drag-drop-section">
      <v-col class="resume-items-1">
        <v-row>
          <p class="title point-font">자기소개 작성 지원</p>
        </v-row>
        <v-row>
          <p class="subtitle point-font">작성 중</p>
          <div id="1-1" class="drop-box" v-on:drop="drop($event)" v-on:dragover="allowDrop($event)">
            <!-- card id는 company id로 -->
            <div class="resume-card" draggable="true" v-on:dragstart="drag($event)" id="d1">
              <div class="card">
                <div class="company">샘플 자기소개서</div>
                <div class="date">
                  <span class="d-day">D-8일</span> ~ 2020.01.23 10:00
                </div>
              </div>
            </div>
             <div class="resume-card" draggable="true" v-on:dragstart="drag($event)" id="d2">
              <div class="card">
                <div class="company">샘플 자기소개서</div>
                <div class="date">
                  <span class="d-day">D-8일</span> ~ 2020.01.23 10:00
                </div>
              </div>
            </div>
          </div>
        </v-row>
        <v-row>
          <p class="subtitle point-font">제출완료</p>
          <div id="1-2" class="drop-box" v-on:drop="drop($event)" v-on:dragover="allowDrop($event)"></div>
        </v-row>
        <v-row>
          <p class="subtitle point-font">미제출</p>
          <div id="1-3" class="drop-box" v-on:drop="drop($event)" v-on:dragover="allowDrop($event)"></div>
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
                id="2-1"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              ></div>
            </v-row>
            <v-row>
              <p class="subtitle point-font">불합격</p>
              <div
                id="2-2"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              ></div>
            </v-row>
          </c-col>
          <c-col class="col-items">
            <v-row>
              <p class="title point-font">1차 전형</p>
            </v-row>
            <v-row>
              <p class="subtitle point-font">합격</p>
              <div
                id="3-1"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              ></div>
            </v-row>
            <v-row>
              <p class="subtitle point-font">불합격</p>
              <div
                id="3-2"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              ></div>
            </v-row>
          </c-col>
          <c-col class="col-items">
            <v-row>
              <p class="title point-font">2차 전형</p>
            </v-row>
            <v-row>
              <p class="subtitle point-font">합격</p>
              <div
                id="4-1"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              ></div>
            </v-row>
            <v-row>
              <p class="subtitle point-font">불합격</p>
              <div
                id="4-2"
                class="drop-box"
                v-on:drop="drop($event)"
                v-on:dragover="allowDrop($event)"
              ></div>
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
            id="5-1"
            class="drop-box"
            v-on:drop="drop($event)"
            v-on:dragover="allowDrop($event)"
          ></div>
        </v-row>
        <v-row>
          <p class="subtitle point-font">불합격</p>
          <div
            id="5-2"
            class="drop-box"
            v-on:drop="drop($event)"
            v-on:dragover="allowDrop($event)"
          ></div>
        </v-row>
      </v-col>
    </v-row>
  </section>
</template>
<script>
export default {
  data: () => ({}),
  methods: {
    drag(e) {
      e.dataTransfer.setData("text", e.target.id);
    },
    drop(e) {
      e.preventDefault();
      var data = e.dataTransfer.getData("text");
      e.target.appendChild(document.getElementById(data));
    },
    allowDrop(e) {
      e.preventDefault();
    }
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
    .col.resume-items-1,  .col.resume-items-2, .col.resume-items-3{
      float: left;
      display: inline-block;
      padding: 0 10px 10px;
      background: $drop-box;
      border-radius: 3px;
    }
    .col.resume-items-1{
      margin: 0 15px;
      width: calc( (100% - 60px) / 12 * 4 );
      .resume-card {
        width: 50%;
      }
    }
    .col.resume-items-2{
      width: calc((100% - 60px) / 12 * 6 );
      padding: 0 5px 10px;
      .resume-card {
        width: 100%;
      }
    }
    .col.resume-items-3{
      margin: 0 15px;
      width: calc((100% - 60px) / 12 * 2 );
      .resume-card {
        width: 100%;
      }
      
    }
    .col-items{
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
    .drop-box {
      width: 100%;
      min-height: 10vh;
      padding: 0 10px;
      .resume-card {
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
              display: inline-block;
              background: #777777;
              color: #ffffff;
              font-size: 0.65rem;
              border-radius: 3px;
            }
          }
        }
      }
    }
  }
}
</style>

