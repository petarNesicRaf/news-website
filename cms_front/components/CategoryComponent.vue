<template>
  <div>
      <div class="d-flex align-items-center justify-content-between border p-2 mb-2">
        <div>

          <router-link class="nav-link" :to="{ name: 'newsByCategory', params: { id: category.id }, query:{page:1 }}">
            <h4>{{ category.name }}</h4>
          </router-link>

          <h6>{{ category.description }}</h6>
        </div>

        <div class="d-flex gap-2">
          <button class="btn btn-primary" @click="editCategory">Edit</button>
          <button class="btn btn-secondary" @click="deleteCategory">Delete</button>
        </div>
      </div>

  </div>
</template>

<script>
export default {
  name: "CategoryComponent",
  props: {
    category: {
      type: Object,
      required: true,
    }
  },
  methods:{
    editCategory(){
      this.$router.push({ name: 'editCategory', params: { id: this.category.id } });
    },
    async deleteCategory() {
       await this.$axios.post(`api/categories/deleteCategory`,parseInt(this.category.id), {
        headers: { 'Content-Type': 'text/plain' }
      });
      this.$emit('cat-deleted');
    }
  }

}
</script>

<style scoped>

</style>