<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <router-link to="/" tag="a" class="navbar-brand">RAF News</router-link>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <router-link to="/mostRead" class="nav-link">Most read</router-link>
          <li  class="nav-item" v-for="category in categories" :key="category.id" @click="selectedCategory = category">
            <router-link class="nav-link" :to="{ name: 'newsByCategory', params: { id: category.id }, query: {page: 1}}">{{ category.name }}</router-link>
          </li>
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
      selectedCategory: null,
      categories: [],
      searchQuery: ''
    }
  },
  created() {
    this.$axios.get('/api/categories/allCategories').then((response) => {
      this.categories = response.data;
    });
  },
  methods: {
    performSearch() {
      if (this.searchQuery.trim() !== '') {
        this.$router.push({ name: 'searchNews', query: { query: this.searchQuery } });
        this.searchQuery=''
      }
    },
  },
}
</script>

<style scoped>

</style>