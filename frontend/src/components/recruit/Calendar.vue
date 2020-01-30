<template>
  <section id="calendar">
    <v-row class="month-header">
      <v-col></v-col>
      <v-col cols="5">
        <p class="point-font">
          <span class="before-month-btn" @click="beforeMonth()">
            <font-awesome-icon icon="chevron-left" />
          </span>
          {{ year }}년 {{ month+1 }}월
          <span class="after-month-btn" @click="afterMonth()">
            <font-awesome-icon icon="chevron-right" />
          </span>
        </p>
      </v-col>
      <v-col>
        <input v-model="search" class="search-company" type="text" placeholder="기업명을 입력하세요." />
      </v-col>
    </v-row>
    <v-row class="weeks-header">
      <v-col v-for="day in days" :key="day">
        <p class="point-font">{{ day }}</p>
      </v-col>
    </v-row>
    <div class="calendar-scroll">
      <v-row v-for="week in weeks" :key="week" class="week-wrapper">
        <v-col v-for="day in 7" :key="day" class="wrap day-wrapper">
          <v-row class="title-wrapper">
            <p class="title point-font">{{ getCalendarDate(7*(week-1)+day-1).substring(8,10)}}</p>
          </v-row>
          <div class="company-wrapper">
            <v-row
              v-if="company.companyName.indexOf(search) >= 0"
              class="company"
              v-for="company in recruit[getCalendarDate(7*(week-1)+day-1)].start"
              :key="company.recruitId"
            >
              <div @click="showCompanyModal(company)" class="infos">
                <span class="info-start-icon point-font">시</span>
                <span class="company-name">{{ company.companyName}}</span>
              </div>
              <span class="star-btn">
                <font-awesome-icon
                  icon="star"
                  v-if="company.favorite"
                  class="fav"
                  @click="likeOrUnlike(1, company)"
                />
                <font-awesome-icon icon="star" v-else @click="likeOrUnlike(0, company)" />
              </span>
            </v-row>
            <v-row
              v-if="company.companyName.indexOf(search) >= 0"
              class="company"
              v-for="company in recruit[getCalendarDate(7*(week-1)+day-1)].end"
              :key="company.recruitId"
            >
              <div @click="showCompanyModal(company)" class="infos">
                <span class="info-start-icon point-font">시</span>
                <span class="company-name">{{ company.companyName}}</span>
              </div>
              <span class="star-btn">
                <font-awesome-icon
                  icon="star"
                  v-if="company.favorite"
                  class="fav"
                  @click="likeOrUnlike(1, company)"
                />
                <font-awesome-icon icon="star" v-else @click="likeOrUnlike(0, company)" />
              </span>
            </v-row>
          </div>
        </v-col>
      </v-row>
    </div>
    <Detail />
  </section>
</template>
<script>
import Detail from "./Detail";
import { mapGetters, mapActions } from "vuex";

export default {
  components: {
    Detail
  },
  data: () => ({
    months: [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December"
    ],
    days: ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"],
    weeks: "",
    today: new Date(),
    year: "",
    month: "",
    date: "",
    startDate: "",
    endtDate: "",
    recruit: {},
    search: "",
    time: "-00:00:00.000"
  }),
  computed: {
    ...mapGetters(["getCalendar"])
  },
  methods: {
    ...mapActions(["calendarAPI", "likeToggle"]),
    async likeOrUnlike(action, company) {
      var favorite = await this.likeToggle({
        recruit_id: company.recruitId,
        action: action
      });
      company.favorite = favorite;
    },
    showCompanyModal(company) {
      this.$modal.show("company-modal", { company: company });
    },
    // 해당 년, 월의 캘린더 시작일, 끝일 구하기
    createCalendar(year, month) {
      var start = new Date(year, month, 1);
      var end = new Date(year, month + 1, 0);

      this.startDate =
        start.getDay() == 0 ? start : new Date(year, month, 1 - start.getDay());
      this.endDate =
        end.getDay() == 6 ? end : new Date(year, month + 1, 6 - end.getDay());

      this.weeks =
        (parseInt((this.endDate - this.startDate) / (24 * 60 * 60 * 1000)) +
          1) /
        7;

      this.getCalenderRecruit(this.weeks * 7);
    },
    getCalenderRecruit(days) {
      let is_end = 1;
      var date = new Date(this.startDate);
      var strDate = date.toISOString().substring(0, 10);
      this.recruit[strDate] = {};
      this.recruit[strDate].start = [];
      this.recruit[strDate].end = [];

      for (let d = 1; d < days; d++) {
        is_end += 1;
        date.setDate(date.getDate() + 1);
        strDate = date.toISOString().substring(0, 10);
        this.recruit[strDate] = {};
        this.recruit[strDate].start = [];
        this.recruit[strDate].end = [];

        if (is_end == days) {
          this.getRecruit();
        }
      }
    },
    getRecruit() {
      this.getCalendar.forEach(company => {
        let start = company.startTime.substring(0, 10);
        let end = company.endTime.substring(0, 10);
        if (this.recruit.hasOwnProperty(start)) {
          this.recruit[company.startTime.substring(0, 10)].start.push(company);
        }
        if (this.recruit.hasOwnProperty(end)) {
          this.recruit[company.endTime.substring(0, 10)].end.push(company);
        }
      });
    },
    getCalendarDate(days) {
      var calendarDate = new Date(this.startDate.valueOf());
      calendarDate.setDate(calendarDate.getDate() + days);
      return calendarDate.toISOString().substring(0, 10);
    },
    beforeMonth() {
      if (this.month == 0) {
        this.year -= 1;
        this.month = 11;
      } else {
        this.month -= 1;
      }
      this.makeCalendarPage();
    },
    afterMonth() {
      if (this.month == 11) {
        this.year += 1;
        this.month = 0;
      } else {
        this.month += 1;
      }
      this.makeCalendarPage();
    },
    makeCalendarPage() {
      if (this.getCalendar.length == 0) {
        // 최소 2달 전 값부터 가져오기
        var startDate =
          new Date(this.year, this.month - 2, 1)
            .toISOString()
            .substring(0, 10) + this.time;
        var endDate =
          new Date(this.year, this.month + 2, 1)
            .toISOString()
            .substring(0, 10) + this.time;

        this.calendarAPI({
          startTime: startDate,
          endTime: endDate
        }).then(e => {
          console.log(e);
          this.createCalendar(this.year, this.month);
        });
      } else {
        this.createCalendar(this.year, this.month);
      }
    }
  },
  created() {
    this.year = this.today.getFullYear();
    this.month = this.today.getMonth();
    this.date = this.today.getDate();

    this.makeCalendarPage();
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

#calendar {
  width: 100%;
  height: calc(100vh - 50px);
  margin: 0;
  padding: 0;
  overflow: hidden;
  .row,
  .col {
    width: 100%;
    margin: 0;
    padding: 0;
  }
  .month-header {
    background: $calendar-border;
    p {
      padding: 20px 0 15px;
      text-align: center;
      font-size: 1.5rem;
      letter-spacing: 0.05rem;
    }
    .before-month-btn,
    .after-month-btn {
      cursor: pointer;
      display: inline-block;
      padding: 5px 10px;
      margin: 0 15px;
      border-radius: 50%;
      text-align: center;
      &:hover {
        background: rgba(1, 1, 1, 0.1);
      }
    }
    .search-company {
      float: right;
      width: 60%;
      max-width: 250px;
      margin: 20px 10%;
      padding: 5px 10px;
      background: #ffffff;
      border: 1px solid $calendar-day;
      border-radius: 50px;
      font-size: 0.85rem;
    }
  }
  .weeks-header {
    background: $calendar-day;
    p {
      width: 100%;
      padding: 5px 0;
      color: #ffffff;
      text-align: center;
      font-size: 0.85rem;
    }
  }
  .calendar-scroll {
    height: calc(100vh - 145px);
    overflow: scroll;
  }
  .row.week-wrapper {
    min-height: 25%;
    border-bottom: 1px solid $calendar-border;
    &:nth-child(1) {
      border-top: 1px solid $calendar-border;
    }
    .day-wrapper {
      border-left: 1px solid $calendar-border;
      .title-wrapper {
        padding: 5px 0;
        background: $calendar-title;
        border-bottom: 1px solid $calendar-border;
        .title {
          width: 100%;
          color: #777777;
          text-align: center;
          font-size: 0.85rem;
        }
      }
      .company-wrapper {
        padding: 5px 0;
      }
      .company {
        cursor: pointer;
        display: block;
        &:hover {
          background: $calendar-title;
        }
        padding: 3px 5px;
        width: 100%;
        .infos {
          display: inline-block;
          width: calc(100% - 18px);
        }
        span {
          display: inline-block;
        }
        .info-start-icon,
        .info-end-icon {
          padding: 3px 4px 2px;
          margin-right: 3px;
          border-radius: 3px;
          font-size: 0.7rem;
        }
        .info-start-icon {
          background: $star2;
        }
        .info-end-icon {
          background: $end;
          color: #ffffff;
        }
        .company-name {
          width:calc(100% - 28px);
          margin: 0 3px;
          padding-top: 3px;
          line-height: 1rem;
          font-size: 0.85rem;
          overflow: hidden;
          white-space: nowrap;
          // text-overflow: ellipsis;
        }
        .star-btn {
          cursor: pointer;
          float: right;
          padding-top: 3px;
          font-size: 0.85rem;
          path {
            color: $star1;
          }
          .fav path {
            color: $star2;
          }
        }
      }
    }
  }
}
</style>