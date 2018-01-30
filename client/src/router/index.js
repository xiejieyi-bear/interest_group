import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV)
// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/** note: submenu only apppear when children.length>=1
*   detail see  https://panjiachen.github.io/vue-element-admin-site/#/router-and-nav?id=sidebar
**/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if fasle ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/authredirect', component: _import('login/authredirect'), hidden: true },
  { path: '/404', component: _import('errorPage/404'), hidden: true },
  { path: '/401', component: _import('errorPage/401'), hidden: true },
  {
    path: '',
    component: Layout,
    redirect: 'join',
    children: [{
      path: 'join',
      component: _import('join/join_view'),
      name: 'join',
      meta: { title: 'join', icon: 'dashboard', noCache: true }
    }]
  },
  {
    path: '',
    component: Layout,
    redirect: 'public_funds',
    children: [{
      path: 'public_funds',
      component: _import('public_funds/funds_view'),
      name: 'public_funds',
      meta: { title: 'public_funds', icon: 'documentation', noCache: true }
    }]
  }
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '',
    component: Layout,
    redirect: 'court',
    meta: { roles: ['admin'] },
    children: [{
      path: 'court',
      component: _import('court/court_view'),
      name: 'court',
      meta: { title: 'court', icon: 'dashboard', noCache: true }
    }]
  },
  {
    path: '',
    component: Layout,
    redirect: 'user',
    meta: { roles: ['admin'] },
    children: [{
      path: 'user',
      component: _import('user/user_view'),
      name: 'user',
      meta: { title: 'user', icon: 'dashboard', noCache: true }
    }]
  },
  {
    path: '',
    component: Layout,
    redirect: 'activity',
    meta: { roles: ['admin'] },
    children: [{
      path: 'activity',
      component: _import('activity/activity_view'),
      name: 'activity',
      meta: { title: 'activity', icon: 'dashboard', noCache: true }
    }]
  },

  { path: '*', redirect: '/404', hidden: true }
]
