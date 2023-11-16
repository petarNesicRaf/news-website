import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from "@/views/Home";
import NewsByCategory from "@/views/NewsByCategory";
import SingleNews from "@/views/SingleNews";
import SearchNews from "@/views/SearchNews";
import MostRead from "@/views/MostRead";
import NewsByTag from "@/views/NewsByTag";

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'home', component: Home},
  { path: '/mostRead', name: 'mostRead', component: MostRead},
  { path: '/category/:id', name: 'newsByCategory', component: NewsByCategory },
  { path: '/news/:id', name: 'singleNews', component: SingleNews },
  { path: '/news/search', name: 'searchNews', component: SearchNews},
  { path: '/tag/:tag', name: 'newsByTag', component: NewsByTag },
]

const router = new VueRouter({
  routes
})

export default router
