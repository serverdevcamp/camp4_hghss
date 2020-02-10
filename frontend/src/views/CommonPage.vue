<template>
  <v-row id="recruit">
    <div
      class="main-section"
      :class="{'chat-open':$store.state.template.ChatState}"
    >
      <Calendar v-if="$route.name == 'RecruitPage'"/>
      <Resume v-if="$route.name == 'ResumePage'"/>
      <ResumeWrite v-if="$route.name =='ResumeWrite'"/>
    </div>
    <div class="chat-section" :class="{'chat-open':$store.state.template.ChatState}">
      <Chat />
    </div>
    <Detail /> 
  </v-row>
</template>

<script>
import Detail from "@/components/recruit/Detail"
import Calendar from "@/components/recruit/Calendar";
import Resume from "@/components/resume/Resume";
import Chat from "@/components/chat/Chat";
import ResumeWrite from '@/components/resume/ResumeWrite'
export default {
  name: "CommonTemplate",
  components: {
    Calendar,
    Resume,
    Chat,
    ResumeWrite,
    Detail,
  },
  data: () => ({})
};
</script>
<style lang="scss">
#recruit {
  padding: 0;
  margin: 0;
  .row,
  .col {
    padding: 0;
    margin: 0;
  }
  .main-section {
    width: 100%;
    transition: width .5s;
    &.chat-open {
      width: calc(100% - 280px);
      transition: width .5s;
    }
  }
  .chat-section{
    position: absolute;
    right:-280px;
    width:280px;
    height: calc(100vh - 50px);
    border-left: 1px solid #ddd;
    transition: right .5s;
    &.chat-open {
      right: 0;
      transition: right .5s;
    }
  }
}
</style>