<template>
  <div>
    <div class="d-flex align-items-center justify-content-between border p-2 mb-2">
      <div>
        <a :href="'http://localhost:8081/?#/news/' + news.id" target="_blank" class="text-decoration-none text-black">
          <h4>{{ news.title }}</h4>
        </a>

        <h6>{{ news.text }}</h6>
      </div>

      <div class="d-flex gap-2">
        <button class="btn btn-primary" @click="editNews">Edit</button>
        <button class="btn btn-secondary" @click="deleteNews">Delete</button>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "NewsComponent",
  props: {
    news: {
      type: Object,
      required: true,
    }
  },
  methods:{
    editNews(){
      this.$router.push({ name: 'editNews', params: { id: this.news.id } });
    },
    async deleteNews() {
      await this.$axios.post(`api/news/deleteNews`,parseInt(this.news.id), {
        headers: { 'Content-Type': 'text/plain' }
      });
      this.$emit('news-deleted');
    }
  }
}
</script>

<style scoped>

</style>