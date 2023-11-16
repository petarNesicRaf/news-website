<template>
  <news-form-component :news="news"/>
</template>

<script>
import NewsFormComponent from "@/components/NewsFormComponent";
export default {
  name: "EditNews",
  components: {NewsFormComponent},
  data() {
    return {
      news: null,
    };
  },
  created() {
    this.fetchNewsData();
  },
  methods:{
    fetchNewsData(){
      const newsId = this.$route.params.id;
      this.$axios.get(`/api/news/${newsId}`).then((response) => {
        this.news = response.data;
      });
    }
  }
}
</script>

<style scoped>

</style>