import Mock from 'mockjs'
import loginAPI from './login'
import articleAPI from './article'
import remoteSearchAPI from './remoteSearch'
import transactionAPI from './transaction'

import courtAPI from './court'
import userAPI from './user'
import activityAPI from './activity'

// Mock.setup({
//   timeout: '350-600'
// })

// 登录相关
Mock.mock(/\/login\/login/, 'post', loginAPI.loginByUsername)
Mock.mock(/\/login\/logout/, 'post', loginAPI.logout)
Mock.mock(/\/user\/info\.*/, 'get', loginAPI.getUserInfo)

// 文章相关
Mock.mock(/\/article\/list/, 'get', articleAPI.getList)
Mock.mock(/\/article\/detail/, 'get', articleAPI.getArticle)
Mock.mock(/\/article\/pv/, 'get', articleAPI.getPv)
Mock.mock(/\/article\/create/, 'post', articleAPI.createArticle)
Mock.mock(/\/article\/update/, 'post', articleAPI.updateArticle)

// 搜索相关
Mock.mock(/\/search\/user/, 'get', remoteSearchAPI.searchUser)

// 账单相关
Mock.mock(/\/transaction\/list/, 'get', transactionAPI.getList)

// 场地相关
Mock.mock(/\/court\/join/, 'get', courtAPI.getJoin)
Mock.mock(/\/court\/all/, 'get', courtAPI.getCourtAll)
Mock.mock(/\/court\/add/, 'post', courtAPI.addCourt)
Mock.mock(/\/court\/del/, 'post', courtAPI.delCourt)

Mock.mock(/\/user\/all/, 'get', userAPI.getAllUser)
Mock.mock(/\/user\/add/, 'post', userAPI.addUser)
Mock.mock(/\/user\/del/, 'post', userAPI.delUser)
Mock.mock(/\/activity\/all/, 'get', activityAPI.getAllActivity)

export default Mock
