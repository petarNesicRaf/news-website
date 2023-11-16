import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from "@/views/Home";
import Login from "@/views/Login";
import SearchNews from "@/views/SearchNews";


Vue.use(VueRouter)

const routes = [
  // { path: '/', name: 'home', component: Home, meta: {authRequired: true} },
  { path: '/login', name: 'login', component: Login },

  { path: '/', name: 'categories', meta: {authRequired: true}, component: () => import('../views/Categories') }, //sve kategorije
  { path: '/category/:id', name: 'newsByCategory', meta: {authRequired: true}, component: () => import('../views/NewsByCategory') }, //vesti odabrane kategorije
  { path: '/createCategory', name: 'createCategory', meta: {authRequired: true}, component: () => import('../views/CreateCateogry') }, //pravljenje kategorije
  { path: '/editCategory/:id', name: 'editCategory', meta: {authRequired: true}, component: () => import('../views/EditCategory') }, //editovanje kategorije

  { path: '/news', name: 'news', meta: {authRequired: true}, component: () => import('../views/News') }, //sve vesti
  { path: '/createNews', name: 'createNews', meta: {authRequired: true}, component: () => import('../views/CreateNews') }, //pravljenje vesti
  { path: '/editNews/:id', name: 'editNews', meta: {authRequired: true}, component: () => import('../views/EditNews') }, //editovanje vesti
  { path: '/news/search', name: 'searchNews', component: SearchNews}, //pretraga vesti

  { path: '/users', name: 'users', meta: {authRequired: true}, component: () => import('../views/Users') }, //svi korisnici
  { path: '/createUser', name: 'createUser', meta: {authRequired: true}, component: () => import('../views/CreateUser') }, //pravljenje korisnika
  { path: '/editUser/:id', name: 'editUser', meta: {authRequired: true}, component: () => import('../views/EditUser') }, //editovanje korisnika

]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => { //da li korisnik ima jwt i da li je taj jwt istekao
  if (to.meta.authRequired) {
    const jwt = localStorage.getItem('jwt');

    if(jwt==null){
      next({name: 'login'});
      return;
    }

    const payload = JSON.parse(atob(jwt.split('.')[1]));
    const expDate = new Date(payload.exp * 1000);
    const role = payload.role;
    const status = payload.status;

    if (!jwt) {
      next({name: 'login'});
      return;
    }

    if (expDate < new Date()) {
      next({name: 'login'});
      return;
    }

    if(parseInt(status)!==1){
      next({name: 'login'});
      return;
    }

    if(to.name==='users' && role!='ADMIN'){
      next({name: 'login'});
      return;
    }
  }

  next();
});


export default router
