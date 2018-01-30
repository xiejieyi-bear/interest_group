
var default_user_list = [{
  id: '1',
  name: 'xiejieyi',
  telphone: '62348589',
  balance: '40',
  charge_total: '100',
  consume_total: '60'
}, {
  id: '2',
  name: 'xueweidong',
  telphone: '6234855',
  balance: '20',
  charge_total: '100',
  consume_total: '80'
}]

var user_charge_history = [
  {
    name: 'xiejieyi',
    time: '2017-12-20',
    charge: '100',
    type: '现金',
    desc: '现金支付'
  },
  {
    name: 'xiejieyi ',
    time: '2017-13-20',
    charge: '100',
    type: '现金',
    desc: '现金支付给谢洁意'
  }

]

var user_consume_history = [
  {
    name: 'xiejieyi',
    time: '2017-12-30',
    location: '独墅湖体育馆',
    self_num: '2',
    participate_total: '6',
    consume: '20'
  }, {
    name: 'xiejieyi',
    time: '2017-12-30',
    location: '独墅湖体育馆',
    self_num: '2',
    participate_total: '6',
    consume: '20'
  }
]

export default {

  getAllUser: () => ({
    items: default_user_list
  }),

  // body url
  addUser: data => {
    var item = JSON.parse(data.body)
    // item.id = Mock.Random.integer(2, 100)
    default_user_list.unshift(item)

    return {
      'retCode': 0,
      'items': default_user_list
    }
  },

  delUser: data => {
    var item = JSON.parse(data.body)
    // var id = item.id
    // var index = default_user_list.indexOf(item)

    default_user_list = default_user_list.filter((court) => {
      return court.id !== item.id
    })
  },
  getUserChargeHistory: data => {
    console.log(data)
    return {
      items: user_charge_history
    }
  },
  getUserConsumeHistory: data => {
    console.log(data)
    return {
      items: user_consume_history
    }
  }

}
