<template>
  <div>
    <button class="btn btn-primary" @click="createCategory">Create category</button>
    <ul class="list-group">
      <li class="list-group-item" v-for="category in categories" :key="category.id"><category-component @cat-deleted="fetchCategories" :category="category"/></li>
    </ul>
  </div>
</template>

<script>
import CategoryComponent from "@/components/CategoryComponent";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Categories",
  components: {CategoryComponent},
  data() {
    return {
      categories: [],
    }
  },
  created() {
    this.fetchCategories()
  },
  methods:{
    createCategory(){
      this.$router.push({ name: 'createCategory' });
    },
    fetchCategories(){
      this.$axios.get('/api/categories/allCategories').then((response) => {
        this.categories = response.data;
      });
    }

  }

}
</script>

<style scoped>

</style>