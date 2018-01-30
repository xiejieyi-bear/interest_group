import Mock from 'mockjs'

var default_court_list = [{
  id: '1',
  name: '独墅湖体育馆',
  addr: '星湖街',
  charge: 40,
  telphone: '62348589',
  is_membership: true,
  balance: '1000'

}, {
  id: '2',
  name: '九帮体育馆',
  addr: '职业技术学院',
  charge: 45,
  telphone: 'no',
  balance: '1000',
  is_membership: true
}]

var charge_hisotry = [
  {
    name: '独墅湖体育馆',
    time: '2017-12-30',
    charge: '1000',
    desc: '初始充值1000元',
    type: '充值'
  },
  {
    name: '独墅湖体育馆',
    time: '2018-01-30',
    charge: '1000',
    desc: '充值1000元',
    type: '充值'

  },
  {
    name: '独墅湖体育馆',
    time: '2018-01-30',
    charge: '-40',
    desc: '场地费用40',
    type: '消费'
  }
]

export default {

  getJoin: () => ({
    time: '2018-01-29',
    location: '苏州科技城文体中心',
    charge: 100,
    members: ['xiejieyi', 'xueweidong', 'shirongrong', 'hanqing', 'zhoujun', 'lihuili'],
    participate_total: 8
  }),

  getCourtAll: () => ({
    items: default_court_list
  }),

  // body url
  addCourt: data => {
    var item = JSON.parse(data.body)
    item.id = Mock.Random.integer(2, 100)
    default_court_list.unshift(item)

    return {
      'retCode': 0,
      'items': default_court_list
    }
  },

  delCourt: data => {
    var item = JSON.parse(data.body)
    // var id = item.id
    // var index = default_court_list.indexOf(item)

    default_court_list = default_court_list.filter((court) => {
      return court.id !== item.id
    })
  },

  getCourtChargeHistory: data => {
    console.log(data.url)
    return {
      items: charge_hisotry
    }
  }
}
