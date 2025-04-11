
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import 'element-plus/dist/index.css'
import axios from "axios";
import Loading from './components/Loading.vue'



const app = createApp(App)
app.component('Loading', Loading)

axios.defaults.baseURL = 'http://localhost:8080'
app.use(createPinia())
app.use(router)

app.mount('#app')
