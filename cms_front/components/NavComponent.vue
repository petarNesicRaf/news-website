<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">

      <form v-if="user" class="form-inline my-2 my-lg-0 d-flex flex-nowrap">
        <a class="navbar-brand">{{ user }}</a>
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit" @submit.prevent @click="logOut">Log out</button>
      </form>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <router-link to="/" class="nav-link">Categories</router-link>
          <router-link to="/news" class="nav-link">News</router-link>
          <router-link v-if="isAdmin" to="/users" class="nav-link">Users</router-link>
        </ul>
      </div>

    </div>

    <form class="form-inline my-2 my-lg-0 d-flex flex-nowrap">
      <input v-model="searchQuery" class="form-control mr-sm-2" type="search" placeholder="Search news">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit" @submit.prevent @click="performSearch">Search</button>
    </form>

  </nav>
</template>

<script>
export default {
  name: "NavComponent",
  data() {
    return {
      searchQuery: '',
      user: JSON.parse(atob(localStorage.getItem('jwt').split('.')[1])).info
    }
  },
  props: {
    role: String, // Define the prop and its type
  },
  created(){

  },
  methods: {
    performSearch() {
      if (this.searchQuery.trim() !== '') {
        this.$router.push({ name: 'searchNews', query: { query: this.searchQuery, page:1 } }).catch(()=>{});
        this.searchQuery=''
      }
    },
    logOut(){
      localStorage.removeItem('jwt');
      window.location.reload();
    }
  },
  computed: {
    isAdmin() {
      const jwt = localStorage.getItem('jwt');
      if (jwt) {
        const payload = JSON.parse(atob(jwt.split('.')[1]));
        return payload.role === 'ADMIN';
      }
      return false;
    },
  },
}
</script>

<style scoped>

</style>