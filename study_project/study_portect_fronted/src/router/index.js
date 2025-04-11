import { createRouter, createWebHistory } from 'vue-router'
import {useStore} from "@/stores";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'welcome',
      component: () => import('@/views/WelcomeView.vue'),
      children: [
        {
          path: '',
          name: 'welcome-login',
          component: () => import('@/components/welcome/LoginPage.vue')
        },{
          path: 'register',
          name: 'welcome-register',
          component: () => import('@/components/welcome/RegisterPage.vue')
        },{
          path: 'forget',
          name: 'welcome-forget',
          component: () => import('@/components/welcome/ForgetPage.vue')
        }
      ]
    },
    {
      path:'/index',
      name:'index',
      component: () => import('@/views/IndexView.vue')
    },
    {
      path:'/publish-works',
      name:'publish-works',
      component: () => import('@/views/sub-views/PublishWorks.vue')
    },
    {
      path:'/my-works',
      name:'my-works',
      component: () => import('@/views/sub-views/MyWorks.vue')
    },
    {
      path:'/copyright-test',
      name:'copyright-test',
      component: () => import('@/views/sub-views/CopyrightTest.vue')
    },
    {
      path:'/purchase-record',
      name:'purchase-record',
      component: () => import('@/views/sub-views/PurchaseRecord.vue')
    },
    {
      path:'/picture-info',
      name:'picture-info',
      component: () => import('@/views/sub-views/PictureInfo.vue')
    },
  ]
})

router.beforeEach((to, from, next) => {
  const store = useStore()
  if(store.auth.user != null && to.name.startsWith('welcome-')) {
    next('/index')
  } else if(store.auth.user == null && to.fullPath.startsWith('/index')) {
    next('/')
  } else if(to.matched.length === 0){
    next('/index')
  } else {
    next()
  }
})
export default router
