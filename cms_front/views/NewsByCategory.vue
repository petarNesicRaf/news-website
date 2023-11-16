<template>
  <div>
    <div v-if="category">
      <h4>{{category.name}}</h4>
      <ul class="list-group">
        <li class="list-group-item" v-for="news in newsList" :key="news.id"><news-component :news="news"/></li>
      </ul>
    </div>
    <div class="d-flex justify-content-between">
      <button class="btn btn-primary" @click="prevPage">previous</button>
      <button class="btn btn-primary" @click="nextPage">next</button>
    </div>
  </div>
</template>

<script>
import NewsComponent from "@/components/NewsComponent";
export default {
  name: "NewsByCategory",
  components: { NewsComponent },
  data() {
    return {
      selectedNews: null,
      newsList: [],
      category: null,
      page: 1
    };
  },
  created() {
    this.fetchNewsData();
  },
  methods: {
    fetchNewsData() {
      const categoryId = this.$route.params.id;
      const page = this.$route.query.page;

      this.$axios.get(`/api/categories/${categoryId}`).then((response) => {
        this.category = response.data;
      });

      this.$axios.get(`/api/news/getNewsByCat/${categoryId}?page=${page}`).then((response) => {
        this.newsList = response.data;
      });
    },

    nextPage() {
      this.$router.push({ name: 'newsByCategory', query: { page: this.page+1 } }).catch(()=>{});
      this.page=this.page+1
    },

    prevPage() {
      if (this.page!==1) {
        this.$router.push({ name: 'newsByCategory', query: { page: this.page-1 } }).catch(()=>{});
        this.page=this.page-1
      }
    },
  },
  watch: {
    '$route.params.id': function(newCategoryId, oldCategoryId) {
      if (newCategoryId !== oldCategoryId) {
        this.fetchNewsData();
      }
    },
    '$route.query.page': function(newPage, oldPage) {
      if (newPage !== oldPage) {
        this.fetchNewsData();
      }
    },
  },

};
</script>

<style scoped>

</style>