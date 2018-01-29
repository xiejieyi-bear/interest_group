
var default_user_list = [{
  id: '1',
  name: 'xiejieyi',
  telphone: '62348589',
  balance: '40'
}, {
  id: '2',
  name: 'xueweidong',
  telphone: '6234855',
  balance: '2'
}]

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
  }
}
