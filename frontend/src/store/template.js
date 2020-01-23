export default {
  state: {
    ChatState: false, // 채팅방 열림 여부
    starRank: {
      1: {
        company: "스마일게이트",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      2: {
        company: "스마일게이트",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      3: {
        company: "스마일게이트",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      4: {
        company: "스마일게이트",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      5: {
        company: "스마일게이트",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
    },
    visitedRank: {
      1: {
        company: "스마일게이트2",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      2: {
        company: "스마일게이트2",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      3: {
        company: "스마일게이트2",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      4: {
        company: "스마일게이트2",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      5: {
        company: "스마일게이트2",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
    },
    ApplicantRank: {
      1: {
        company: "스마일게이트",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      2: {
        company: "스마일게이트",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      3: {
        company: "스마일게이트",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      4: {
        company: "스마일게이트",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
      5: {
        company: "스마일게이트",
        remainingDays: 5,
        count: 500,
        companyId: '111222'
      },
    },
  },
  mutations: {},
  actions: {},
  getters: {
    getChatState(state) {
      return state.ChatState
    },
  }
}