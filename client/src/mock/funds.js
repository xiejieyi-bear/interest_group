
var default_funds_list = [{
  type: '收入',
  time: '2018-01-29',
  cause: '部门赞助',
  charge: '1000'
}, {
  type: '支出',
  time: '2018-01-29',
  cause: '买球',
  charge: '200'
}]

export default {
  getAllFunds: () => ({
    items: default_funds_list
  })
}
