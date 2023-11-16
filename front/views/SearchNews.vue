<template>
  <ul class="list-group">
    <li class="list-group-item" v-for="news in newsList" :key="news.id"><news-component :news="news"/></li>
  </ul>
</template>

<script>
import NewsComponent from "@/components/NewsComponent";
export default {
  name: "SearchNews",
  components: {NewsComponent},
  data() {
    return {
      newsList: [],
    };
  },
  created() {
    this.fetchSearchResults();
  },
  methods: {
    async fetchSearchResults() {
      const searchQuery = this.$route.query.query;

      try {
        const response = await this.$axios.get(`/api/news/search?query=${searchQuery}`);
        this.newsList = response.data;
      } catch (error) {
        console.error('Error fetching search results:', error);
      }
    },
  },
  watch: {
    '$route.query.query': function(newQuery, oldQuery) {
      if (newQuery !== oldQuery) {
        this.fetchSearchResults();
      }
    },
  },
}
</script>

<style scoped>

</style>