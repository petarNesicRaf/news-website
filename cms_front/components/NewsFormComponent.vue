<template>
  <div class="container">
    <div class="row">

      <div class="col-md-6">
        <label v-if="news" for="input1" class="form-label">Current title: </label>
        <input v-model="title" type="text" class="form-control mb-2" id="input1" placeholder="News title" required>
      </div>

      <div class="col-md-6">
        <label v-if="news" for="select1" class="form-label">Current category: </label>
        <select v-model="selectedCategory" class="form-select" required>
          <option v-for="category in categories" :key="category.id" :value="category.id">{{ category.name }}</option>
        </select>
      </div>

      <div class="col-md-12">
        <label v-if="news" for="textarea1" class="form-label">Current text: </label>
        <textarea v-model="text" style="resize: none;" id="textarea1" class="form-control mb-2" rows="3" placeholder="News text" required></textarea>
      </div>

      <div class="col-md-6">
        <label v-if="news" for="input2" class="form-label">Current tags: </label>
        <input v-model="tags" type="text" class="form-control mb-2" id="input2" placeholder="News tags" required>
      </div>

      <div class="col-md-6">
        <label></label>
        <button class="btn btn-primary mb-2" @submit.prevent @click="editOrCreate">Submit</button>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "NewsFormComponent",
  props:{
    news: Object
  },
  data() {
    return {
      response: null,
      categories: [],
      selectedCategory: null,
      title: '',
      text: '',
      fetchedTags: [],
      tags: '',
      dateTime: null
    }
  },
  created() {
    this.$axios.get('/api/categories/allCategories').then((response) => {
      this.categories = response.data;
    });
  },
  methods: {
    getTags() {
      this.$axios.get(`/api/tags/getAllTagsFromNews/${this.news.id}`).then((response) => {
        this.fetchedTags = response.data;
        this.tags = this.fetchedTags.join(',');
      });
    },
    getAuthor(){
      const jwt = localStorage.getItem('jwt');
      const payload = JSON.parse(atob(jwt.split('.')[1]));
      return payload.info
    },
    editOrCreate() {
      if(this.title!=='' && this.text!=='' && this.tags!=='' && this.selectedCategory!=null){
        if (typeof this.news === 'undefined'){ //zove se sa create
          this.$axios.post('/api/news/createNews', {
            "title": this.title,
            "text": this.text,
            "dateTime":"2023-07-07",
            "author":this.getAuthor(),
            "idCategory":this.selectedCategory
          }).then((response) => {
            this.response = response.data;
            this.$axios.post('/api/tags/replaceTags',{
              "idNews": this.response.id,
              "allTags": this.tags
            })
          });
        }
        else{
          this.$axios.post('/api/news/editNews',{ //zove se sa edit
            "id":this.news.id,
            "title":this.title,
            "text":this.text,
            "dateTime":"2021-06-22",
            "author":this.news.author,
            "idCategory":this.selectedCategory,
            "readers":0
          }).then((response) => {
            this.response = response.data;
            this.$axios.post('/api/tags/replaceTags',{
              "idNews": this.news.id,
              "allTags": this.tags
            })
          });
        }
      }
    },
  },
  watch: {
    news(newNews) {
      if (newNews) {
        this.title = newNews.title;
        this.text = newNews.text;
        this.selectedCategory = newNews.idCategory;
        this.getTags()
      }
    }
  },
}
</script>

<style scoped>

</style>