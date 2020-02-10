<template>
  <modal
    name="resume-modal"
    transition="pop-out"
    :height="140"
    :width="400"
    @before-open="beforeOpen"
  >
    <div class="resume-modal">
      <v-row>
        <p class="title point-font">{{target.title}}</p>
      </v-row>
      <v-row>
        <p class="date">작성일 {{(target.createdAt+'').substring(0,10)}} &nbsp;&nbsp;&nbsp;| 마감일 {{(target.endTime+'').substring(0,10)}}</p>
      </v-row>
      <v-row>
        <buttton class="recuit-btn" @click="openRecruitDetailModal(target)">공고 보기</buttton>
        <buttton class="write-btn" @click="$router.push({ name: 'ResumeWrite', params: { id: target.id }})">자기소개서 작성</buttton>
        <buttton class="del-btn" @click="deleteButton(target.id)"><font-awesome-icon :icon="['fas', 'trash-alt']" /></buttton>
      </v-row>
    </div>
  </modal>
</template>
<script>
import {mapActions} from 'vuex'

export default {
  data() {
    return {
      target : {}
    };
  },
  methods: {
    ...mapActions(["deleteResume"]),
    openRecruitDetailModal(target){
      let company = {
        recruitId: target.recruitId,
        company_id: target.companyId
      }
      this.$modal.show("company-modal", { company:company });
      this.$modal.hide("resume-modal");

    },
    deleteButton(resume_id){
      this.deleteResume({resume_id : resume_id})
      this.$modal.hide("resume-modal");
    },
    beforeOpen(event) {
      this.target = event.params.target;
    },
  }
};
</script>
<style lang="scss">
.resume-modal {
  padding: 25px 25px;
  .title {
    padding: 0 5px;
    margin-bottom: 10px;
    font-size: 1.1rem;
  }
  .date {
    padding: 0 5px;
    margin-bottom: 15px;
    font-size: 0.8rem;
    color: #707070;
  }
  .recuit-btn,
  .write-btn,
  .del-btn {
    cursor: pointer;
    padding: 8px 5px;
    font-size: 0.8rem;
    text-align: center;
    border: 1px solid #ddd;
    border-radius: 3px;
    color: #999999;
    border: 1px solid #ddd;
    &:hover {
      background-color: #fafafa;
    }
  }
  .recuit-btn {
    width: 30%;
  }
  .write-btn {
    margin: 0 3%;
    width: 49%;
    background-color: #ffffff;
    border: solid 1px #ff6813;
    color: #ff6813;
    &:hover {
      color: #ffffff;
      background-color: #ff6813;
    }
  }
  .del-btn {
    width: 15%;
    path{
      color: #999999;
    }
  }
}
</style>