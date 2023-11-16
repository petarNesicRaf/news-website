<template>
  <div v-if="tag">
    <ul class="list-group">
      <li class="list-group-item" v-for="news in newsList" :key="news.id"><news-component :news="news"/></li>
    </ul>
  </div>
</template>

<script>
import NewsComponent from "@/components/NewsComponent";

export default {
  name: "NewsByTag",
  components: { NewsComponent },
  data() {
    return {
      newsList: [],
      tag: null,
    };
  },
  created() {
    this.fetchNewsData();
  },
  methods: {
    fetchNewsData() {
      this.tag = this.$route.params.tag;

      this.$axios.get(`/api/tags/getAllNewsWithTag/${this.tag}`).then((response) => {
        this.newsList = response.data;
      });
    },
  },
  watch: {
    '$route.params.tag': function(newTag, oldTag) {
      if (newTag !== oldTag) {
        this.fetchNewsData();
      }
    },
  },
}
</script>

<style scoped>

</style>