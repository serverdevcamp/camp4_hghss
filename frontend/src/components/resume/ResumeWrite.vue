<template>
  <div id="write">
    <v-row class="menu-section">
      <div class="icon-btn" @click="openNewTab('/resume')">
        <font-awesome-icon icon="list" />
        <p>자소설 목록</p>
      </div>
      <div class="icon-btn" @click="save()">
        <font-awesome-icon :icon="['far', 'save']" />
        <p>저장하기</p>
      </div>
      <div class="icon-btn">
        <font-awesome-icon icon="download" />
        <p>내보내기</p>
      </div>
      <div class="round-btn">
        <p>맞춤법 검사</p>
      </div>
      <div class="round-btn" @click="showRecord()" :class="{active : recode_mode}">
        <p>저장기록</p>
      </div>
    </v-row>
    <v-row class="write-section">
      <v-row class="write-form">
        <v-col class="write-item">
          <v-row class="title">
            <p class="point-font">{{resume.title}}</p>
          </v-row>
          <v-row class="content">
            <div class="content-num">
              <p
                class="point-font num"
                v-for="index in answers.length"
                :key="index"
                :class="{active : answers[index-1].orderNum == target}"
                @click="openTab(answers[index-1].orderNum)"
              >
                {{ answers[index-1].orderNum }}
                <span
                  class="point-font number-btn"
                  v-if="delete_mode"
                  @click="deleteA(index-1)"
                >x</span>
              </p>
              <p class="num content-btn" @click="addA()">+</p>
              <p class="num content-btn" @click="delete_mode_toggle()">-</p>
            </div>

            <div
              class="text-section"
              v-for="answer in answers"
              v-if="answer.orderNum == target"
              :key="answer.orderNum"
            >
              <textarea class="question" v-model="answer.questionContent"></textarea>
              <textarea class="answer" v-model="answer.answerContent"></textarea>
              <v-row class="check-letter">
                <v-col cols="4">
                  <p>{{ answer.answerContent.length }}/{{ answer.questionLimit }} (글자수, 공백포함)</p>
                </v-col>
                <v-col>
                  <p class="letter_max_bar">
                    <span
                      :style="{ width: 'calc('+ (answer.answerContent.length) / answer.questionLimit * 100 +'%)', background:answer.questionLimit < answer.answerContent.length ?'red' : '#ff6813'}"
                    ></span>
                  </p>
                </v-col>
              </v-row>
            </div>
          </v-row>
        </v-col>
        <v-col class="recode" v-if="recode_mode">
          <v-row class="title">
            <p class="point-font">저장 기록</p>
          </v-row>
          <v-row class="content-wrap" v-if="storage_item.hasOwnProperty(target)">
            <v-col
              class="content"
              cols="12"  
              v-for="(item, index) in storage_item[target]"
              :key="index"
            >
              <!-- <div class="recode-order">저장 {{ storage_item[target].length - index}}</div> -->
              <div class="recode-order">저장 {{ index+1 }}</div>
              <div class="datetime">{{ item.datetime }}</div>
              <div class="item-content">
                <p class="question">{{ item.question}}</p>
                <p class="answer">{{ item.answer}}</p>
              </div>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-row>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "ResumeWrite",
  data: () => ({
    // 이거 분리하자!!
    resume_id: "",
    resume: {},
    answers: [],
    target: 1,
    delete_mode: false,
    recode_mode: false,
    storage_item: {}
  }),
  methods: {
    ...mapActions(["detailResume", "saveResume", "addAnswer", "deleteAnswer"]),
    showRecord() {
      this.recode_mode = !this.recode_mode;
    },
    openTab(index) {
      this.target = index;
    },
    save() {
      this.recode() 
      var today = new Date();
      this.saveResume({
        resume_id: this.resume_id,
        body: {
          title: this.resume.title,
          endTime:
            today.toISOString().split("T")[0] +
            " " +
            (today + "").substring(16, 24),
          answers: this.answers
        }
      });
    },
    addA() {
      this.addAnswer({ resume_id: this.resume_id }).then(answer => {
        this.answers.push(answer);
      });
    },
    deleteA(index) {
      if (confirm(this.answers[index].orderNum + "번 문항을 정말 삭제하시겠습니까?")) {
        this.deleteAnswer({ answer_id: this.answers[index].id });
        this.answers.splice(index, 1);
      }
    },
    delete_mode_toggle() {
      this.delete_mode = !this.delete_mode;
    },
    openNewTab(path) {
      window.open(window.location.origin + path, "_blank");
    },
    recode() {
      // 10분마다 로컬스토리지에 기록
      var datetime = new Date().toISOString().split("T");
      var storage_item = JSON.parse(localStorage.getItem(this.resume_id)) || {};

      this.answers.forEach(answer => {
        if (!storage_item.hasOwnProperty(answer.orderNum)) {
          storage_item[answer.orderNum] = [];
        }
        storage_item[answer.orderNum].push({
          question: answer.questionContent,
          answer: answer.answerContent,
          datetime: datetime[0] + " " + datetime[1].substring(0, 5)
        });
      });
      localStorage.setItem(this.resume_id, JSON.stringify(storage_item));

      // 잡기
      this.storage_item = JSON.parse(localStorage.getItem(this.resume_id)) || {}
    },
    saveAndRecode(storage_item){
      localStorage.setItem(this.resume_id, JSON.stringify(storage_item));
      // 서버에 최종본 저장
      this.save();
    }
  },
  async created() {
    this.resume_id = this.$route.params.id;
    var resume_detail = await this.detailResume({
      resume_id: this.$route.params.id
    });
    this.resume = Object.assign({}, resume_detail.resume);
    this.answers = resume_detail.answers;
    this.storage_item = JSON.parse(localStorage.getItem(this.resume_id)) || {}
    // 10분마다 로컬스토리지에 기록
    setInterval(() => {
      this.recode()
      this.save()
    }, 100000);
  }
};
</script>

<style lang="scss">
$calendar-border: #f0f0f0;
$drop-border: #bbbbbb;
$drop-box: #dddee0;
$point-color: #ff6813;
#write {
  height: calc(100%);
  overflow: hidden;
  background: $calendar-border;
  .menu-section {
    overflow: hidden;
    height: 70px;
    background: #fafafa;
    padding: 16px 30px !important;
    border-bottom: 1px solid #d8d8d8;
    .icon-btn {
      cursor: pointer;
      display: inline-block;
      padding-right: 15px;
      text-align: center;
      &:nth-child(3) {
        margin-right: 30px;
        padding-right: 40px;
        border-right: 1px solid #d8d8d8;
      }
      path {
        color: #999;
        font-size: 1.1rem;
      }
      p {
        color: #999;
        margin-top: 3px;
        font-weight: 600;
        font-size: 0.8rem;
      }
      &:hover {
        path,
        p {
          color: $point-color;
        }
      }
    }
    .round-btn {
      margin-left: 15px;
      text-align: center;
      &.active p {
        color: #ffffff;
        background: $point-color;
      }
      p {
        cursor: pointer;
        margin-top: 3px;
        padding: 7px 15px;
        font-size: 0.8rem;
        border: 1px solid #d8d8d8;
        border-radius: 30px;
        color: #999;
        font-weight: 600;
        &:hover {
          color: #ffffff;
          background: $point-color;
          border: 1px solid $point-color;
        }
      }
    }
  }
  .write-section {
    margin-bottom: 60px;
    position: relative;
    overflow-y: hidden;
    overflow-x: scroll;
    width: 100%;
    height: calc(100vh - 120px);
    .row.write-form {
      position: relative;
      margin: 60px 0;
      padding: 0 60px;
      left: calc(50% - 565px);
      min-width: 1130px;
      max-width: 1130px;

      .write-item {
        min-width: 700px;
        max-width: 700px;
        background: #ffffff;
        box-shadow: 0px 0px 5px #d8d8d8;
        .title {
          padding: 10px 20px;
          border-bottom: 1px dashed $drop-border;
          p {
            margin-top: 3px;
            color: $point-color;
            font-size: 0.95rem;
            letter-spacing: 0.03rem;
          }
        }
        .content {
          position: relative;
          padding: 0 5px;
          .content-num {
            left: -41px;
            position: absolute;
            .num {
              cursor: pointer;
              position: relative;
              width: 40px;
              height: 30px;
              margin-bottom: 7px;
              padding-top: 7px;
              background: #ffffff;
              color: $point-color;
              text-align: center;
              font-size: 0.9rem;
              box-shadow: 0px 0px 5px #d8d8d8;
              .number-btn {
                content: "x";
                position: absolute;
                width: 16px;
                height: 16px;
                top: -3px;
                left: -5px;
                padding-top: 2px;
                background: #999;
                color: #fff;
                text-align: center;
                border-radius: 50%;
                font-size: 10px;
                font-weight: 600;
              }
              &.active {
                left: -5px;
                width: 50px;
                background: $point-color;
                color: #ffffff;
                box-shadow: 0px 0px 10px #d8d8d8;
              }
              &.content-btn {
                color: #ffffff;
                background: #999;
                font-weight: 600;
                font-size: 1rem;
                padding-top: 5px;
              }
            }
          }
          .text-section {
            width: 100%;
          }
          textarea {
            resize: none;
            overflow: hidden;
            width: 100%;
            padding: 10px;
            font-size: 0.85rem;
            &:focus {
              outline: none;
            }
          }
          .question {
            height: 100px;
            border-bottom: 1px solid $drop-border;
          }
          .answer {
            height: 450px;
          }
          .check-letter {
            width: 100%;
            padding: 10px;
            border-top: 1px solid $drop-border;
            p {
              font-size: 0.8rem;
            }
            .letter_max_bar {
              overflow: hidden;
              content: "";
              position: relative;
              width: 100%;
              height: 100%;
              background: #f0f0f0;
              border-radius: 25px;
              span {
                content: "";
                position: absolute;
                height: 100%;
              }
            }
          }
        }
      }
      .recode {
        height: 640px;
        overflow: hidden;
        min-width: 300px;
        max-width: 300px;
        margin-left: 10px;
        background: #ffffff;
        box-shadow: 0px 0px 5px #d8d8d8;
        .title {
          padding: 10px 20px;
          border-bottom: 1px dashed $drop-border;
          p {
            color: $point-color;
            margin-top: 3px;
            font-size: 0.95rem;
            letter-spacing: 0.03rem;
          }
        }
        .content-wrap {
          display: inline-block;
          width: calc(100% - 30px);
          height: calc(100% - 35px);
          overflow: scroll;
          margin: 0 15px;
          .content {
            cursor: pointer;
            display: inline-block;
            padding: 15px 5px 15px;
            border-bottom: 1px solid #ddd;
            .recode-order,
            .datetime {
              display: inline-block;
              width: 100%;
            }
            .recode-order {
              font-size: 0.8rem;
            }
            .datetime {
              margin-top: 5px;
              color: #999;
              font-size: 0.75rem;
            }
            .item-content {
              margin: 10px 0;
              .question {
                font-size: 0.8rem;
                color: #999;
              }
              .answer {
                margin: 10px 0;
                font-size: 0.75rem;
              }
            }
          }
        }
      }
    }
  }
}
</style>