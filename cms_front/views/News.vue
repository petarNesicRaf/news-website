<template>
  <div>
    <button class="btn btn-primary" @click="createNews">Create news</button>
    <ul class="list-group">
      <li class="list-group-item" v-for="news in newsList" :key="news.id"><news-component @news-deleted="fetchNewsData" :news="news"/></li>
    </ul>

    <div class="d-flex justify-content-between">
      <button class="btn btn-primary" @click="prevPage">previous</button>
      <button class="btn btn-primary" @click="nextPage">next</button>
    </div>
  </div>
</template>

<script>
import NewsComponent from "@/components/NewsComponent";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "News",
  components: { NewsComponent },
  data() {
    return {
      newsList: [],
      page: 1
    };
  },
  created() {
    this.$router.push({ name: 'news', query: { page: this.page } }).catch(()=>{});
    this.fetchNewsData();
  },
  methods: {
    createNews(){
      this.$router.push({ name: 'createNews' });
    },
    async fetchNewsData() {
      const page = this.$route.query.page;

      try {
        const response = await this.$axios.get(`/api/news/allNews?page=${page}`).catch(()=>{});
        this.newsList = response.data;
      } catch (error) {
        console.error('Error fetching search results:', error);
      }
    },
    nextPage() {
        this.$router.push({ name: 'news', query: { page: this.page+1 } }).catch(()=>{});
        this.page=this.page+1
    },
    prevPage() {
      if (this.page!==1) {
        this.$router.push({ name: 'news', query: { page: this.page-1 } }).catch(()=>{});
        this.page=this.page-1
      }
    },
  },
  watch: {
    '$route.query.page': function(newPage, oldPage) {
      if (newPage !== oldPage) {
        this.fetchNewsData();
      }
    },
  },
}
</script>

<style scoped>

</style>