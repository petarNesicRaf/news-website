<template>
  <category-form-component :category="category"/>
</template>

<script>
import CategoryFormComponent from "@/components/CategoryFormComponent";
export default {
  name: "EditCategory",
  components: {CategoryFormComponent,},
  data() {
    return {
      category: null,
    };
  },
  created() {
    this.fetchCategoryData();
  },
  methods:{
    fetchCategoryData(){
      const categoryId = this.$route.params.id;
      this.$axios.get(`/api/categories/${categoryId}`).then((response) => {
        this.category = response.data;
      });
    }
  }
}
</script>

<style scoped>

</style>