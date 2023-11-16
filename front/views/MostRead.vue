<template>
  <div>
    <ul class="list-group">
      <li class="list-group-item" v-for="news in newsList" :key="news.id"><news-component :news="news"/></li>
    </ul>
  </div>
</template>

<script>

import NewsComponent from "@/components/NewsComponent";

export default {
  name: "MostRead",
  components: {NewsComponent},
  data() {
    return {
      selectedNews: null,
      newsList: []
    }
  },
  created() {
    this.$axios.get('/api/news/mostRead').then((response) => {
      this.newsList = response.data;
    });
  },
}
</script>

<style scoped>

</style>