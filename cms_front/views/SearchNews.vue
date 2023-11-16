<template>
  <div>
    <ul class="list-group">
      <li class="list-group-item" v-for="news in newsList" :key="news.id"><news-component :news="news"/></li>
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
  name: "SearchNews",
  components: {NewsComponent},
  data() {
    return {
      newsList: [],
      page: 1
    };
  },
  created() {
    const searchQuery = this.$route.query.query;
    this.$router.push({ name: 'searchNews', query: { page: this.page, query:searchQuery} }).catch(()=>{});
    this.fetchSearchResults();
  },
  methods: {
    async fetchSearchResults() {
      const searchQuery = this.$route.query.query;
      const page = this.$route.query.page;

      try {
        const response = await this.$axios.get(`/api/news/search?query=${searchQuery}&page=${page}`).catch(()=>{});
        this.newsList = response.data;
      } catch (error) {
        console.error('Error fetching search results:', error);
      }
    },
    nextPage() {
      const searchQuery = this.$route.query.query;
      this.$router.push({ name: 'searchNews', query: { page: this.page+1, query:searchQuery} }).catch(()=>{});
      this.page=this.page+1
    },
    prevPage() {
      if (this.page !== 1) {
        const searchQuery = this.$route.query.query;
        this.$router.push({name: 'searchNews', query: {page: this.page - 1, query:searchQuery}}).catch(()=>{});
        this.page = this.page - 1
      }
    }
  },
  watch: {
    '$route.query.query': function(newQuery, oldQuery) {
      if (newQuery !== oldQuery) {
        this.fetchSearchResults();
      }
    },
    '$route.query.page': function(newPage, oldPage) {
      if (newPage !== oldPage) {
        this.fetchSearchResults()
      }
    },
  },
}
</script>

<style scoped>

</style>