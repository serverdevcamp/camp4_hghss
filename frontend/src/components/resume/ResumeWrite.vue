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
      <div class="round-btn">
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
              :key="answer"
            >
              <textarea class="question" v-model="answer.questionContent"></textarea>
              <textarea class="answer" v-model="answer.answerContent"></textarea>
            </div>
            <div class="check-letter">
              <p>7/{{ answers.questionLimit }} (글자수, 공백포함)</p>
            </div>
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
    delete_mode: false
  }),
  methods: {
    ...mapActions(["detailResume", "saveResume", "addAnswer", "deleteAnswer"]),
    openTab(index) {
      this.target = index;
    },
    save() {
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
        this.answers.splice(index, 1);
        this.deleteAnswer({ answer_id: this.answers[index].id });
      }
    },
    delete_mode_toggle() {
      this.delete_mode = !this.delete_mode;
    },
    openNewTab(path) {
      window.open(window.location.origin + path, "_blank");
    }
  },
  async created() {
    this.resume_id = this.$route.params.id;
    var resume_detail = await this.detailResume({
      resume_id: this.$route.params.id
    });
    this.resume = Object.assign({}, resume_detail.resume);
    this.answers = resume_detail.answers;
  }
};
</script>

<style lang="scss">
$calendar-border: #f0f0f0;
$drop-border: #bbbbbb;
$drop-box: #dddee0;
$point-color: #ff6813;
#write {
  height: calc(100vh - 50px);
  overflow: hidden;
  background: $calendar-border;
  .menu-section {
    background: #fafafa;
    padding: 15px 30px !important;
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
    .row.write-form {
      position: relative;
      margin-top: 60px;
      left: calc(50% - 400px);
      min-width: 700px;
      max-width: 700px;
      .write-item {
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
              font-size: 0.85rem;
            }
          }
        }
      }
    }
  }
}
</style>