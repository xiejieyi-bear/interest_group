import Cookies from 'js-cookie'

const court = {
  state: {
    time: '2018-01-27',
    location: '苏州独墅湖体育馆',
    charge: '80RMB',
    members: 'xiejieyi'
  },
  mutations: {
    SET_COURT_JOIN: (state, info) => {
      state.time = info.time;
      state.location = info.location;
      state.charge =  info.charge;
      state.members = info.members;
    },
  },
  actions: {
    setCourtJoinInfo({ commit }) {
      commit('SET_COURT_JOIN')
    },
  }
}

export default app
