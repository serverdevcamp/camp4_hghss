<template>
  <div id="write">
    <v-row class="menu-section">
      <v-col>
        <p>자소설 목록</p>
      </v-col>
      <v-col>
        <p>저장하기</p>
      </v-col>
      <v-col>
        <p>내보내기</p>
      </v-col>
      <v-col>
        <p>맞춤법 검사</p>
      </v-col>
      <v-col>
        <p>저장기록</p>
      </v-col>
    </v-row>
    <v-row class="write-section">
      <v-row class="write-form">
        <v-col class="write-item">
          <v-row class="title">
            <p class="point-font">회사이름 직무</p>
          </v-row>
          <v-row class="content">
            <div class="content-num">
              <p class="point-font num active">1</p>
              <p class="point-font num">2</p>
            </div>
            <div class="text-section">
              <textarea class="question">질문</textarea>
              <textarea class="answer">내용</textarea>
            </div>
            <div class="check-letter">
              <p>7/3000 (글자수, 공백포함)</p>
            </div>
          </v-row>
        </v-col>
      </v-row>
    </v-row>
  </div>
</template>
<script>
export default {
  data: () => ({
    // 이거 분리하자!!
    answer : '',
  }),
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
              &.active {
                left: -5px;
                width: 50px;
                background: $point-color;
                color: #ffffff;
                box-shadow: 0px 0px 10px #d8d8d8;
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
            p{
              font-size: 0.85rem;
            }
          }
        }
      }
    }
  }
}
</style>