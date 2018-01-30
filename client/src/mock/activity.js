
var default_activity_list = [{
  begin_time: '2018-01-28 18:30',
  duration: '30',
  court_name: '独墅湖体育馆',
  participate_total: '6',
  charge: '40',
  participate_users: ['xiejieyi', 'xueweidong', 'shirongrong', 'hanqing', 'zhoujun', 'lihuili'].toString()
}, {
  begin_time: '2018-01-29 19:00',
  duration: '30',
  court_name: '独墅湖体育馆',
  participate_total: '6',
  charge: '40',
  participate_users: ['xiejieyi', 'xueweidong', 'shirongrong', 'hanqing', 'zhoujun', 'lihuili'].toString()
}]

export default {
  getAllActivity: () => ({
    items: default_activity_list
  }),

  cancelActivity: () => ({
    // console.log('111')
    retcode: 0
  }),
  createActivity: () => ({
    retcode: 0
  }),
  joinActivity: () => ({
    retcode: 0
  })
}
