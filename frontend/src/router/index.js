import Router from 'vue-router'
import Vue from 'vue'
import Account from '../views/Account'
import Organization from '../views/Analytics'
import Trips from '../views/Trips'
import DeleteAccount from '../views/DeleteAccount'
import Login from '../views/Login'
import Unavailable from '../views/Unavailable'
import PageNotFound from '../views/PageNotFound'
import Competitions from '../views/Competitions'
import Registration from '../views/Registration'
import NavigationBottom from '../components/NavigationBottom'
import NavigationToolbar from '../components/NavigationToolbar'
import Admin from '../views/admin/Admin'
import Users from '../views/admin/Users'
import Organizations from '../views/admin/Organizations'
import System from '../views/admin/System'
import Carpooling from '../views/Carpooling'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '*',
      components: {
        content: PageNotFound,
      },
      props: { toolbar: { title: '404 Page Not Found' } },
      meta: { requiresAuth: false }
    },
    {
      path: '/',
      name: 'Login',
      components: {
        content: Login,
      },
      meta: { requiresAuth: false }
    },
    {
      path: '/register',
      name: 'New account',
      components: {
        content: Registration,
      },
      meta: { requiresAuth: false }
    },
    {
      path: '/unavailable',
      name: 'Unavailable',
      components: {
        toolbar: NavigationToolbar,
        content: Unavailable,
      },
      props: { toolbar: { title: 'Subscription required' } },
      meta: { requiresAuth: false }
    },
    {
      path: '/trips',
      name: 'Trips',
      components: {
        toolbar: NavigationToolbar,
        bottom_navigation: NavigationBottom,
        content: Trips,
      },
      props: { toolbar: { title: 'Trips' } },
      meta: { requiresAuth: true }
    },
    {
      path: '/competitions',
      name: 'Competitions',
      components: {
        toolbar: NavigationToolbar,
        bottom_navigation: NavigationBottom,
        content: Competitions,
      },
      props: { toolbar: { title: 'Competitions' } },
      meta: { requiresAuth: true }
    },
    {
      path: '/carpooling',
      name: 'Carpooling',
      components: {
        toolbar: NavigationToolbar,
        bottom_navigation: NavigationBottom,
        content: Carpooling,
      },
      props: { toolbar: { title: 'Carpooling' } },
      meta: { requiresAuth: true }
    },
    {
      path: '/account',
      name: 'Account',
      components: {
        toolbar: NavigationToolbar,
        bottom_navigation: NavigationBottom,
        content: Account,
      },
      props: { toolbar: { title: 'My Account' } },
      meta: { requiresAuth: true }
    },
    {
      path: '/account/delete',
      name: 'Delete account',
      components: {
        toolbar: NavigationToolbar,
        bottom_navigation: NavigationBottom,
        content: DeleteAccount,
      },
      props: { toolbar: { title: 'Confirm delete' } },
      meta: { requiresAuth: true }
    },
    {
      path: '/analytics',
      name: 'Analytics',
      components: {
        toolbar: NavigationToolbar,
        bottom_navigation: NavigationBottom,
        content: Organization,
      },
      props: { toolbar: { title: 'Analytics' } },
      meta: { requiresAuth: true }
    },
    {
      path: '/admin',
      name: 'Admin',
      components: {
        toolbar: NavigationToolbar,
        bottom_navigation: NavigationBottom,
        content: Admin,
      },
      props: { toolbar: { title: 'Administration' } },
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/users',
      name: 'Users',
      components: {
        toolbar: NavigationToolbar,
        bottom_navigation: NavigationBottom,
        content: Users,
      },
      props: { toolbar: { title: 'Administration - Users' } },
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/organizations',
      name: 'Organizations',
      components: {
        toolbar: NavigationToolbar,
        bottom_navigation: NavigationBottom,
        content: Organizations,
      },
      props: { toolbar: { title: 'Administration - Organizations' } },
      meta: { requiresAuth: true }
    },
    {
      path: '/admin/system',
      name: 'System',
      components: {
        toolbar: NavigationToolbar,
        bottom_navigation: NavigationBottom,
        content: System,
      },
      props: { toolbar: { title: 'Administration - System' } },
      meta: { requiresAuth: true }
    },
  ]
})

export default router
