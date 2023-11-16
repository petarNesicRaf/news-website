<template>
  <div>
    <form>
      <div class="mb-3">
        <label v-if="category" for="input1" class="form-label">Current name: {{ category.name }}</label>
        <input v-model="name" type="text" class="form-control" id="input1" placeholder="Category name" required>
      </div>
      <div class="mb-3">
        <label v-if="category" for="input2" class="form-label">Current description: {{ category.description }}</label>
        <input v-model="description" type="text" class="form-control" id="input2" placeholder="Category description" required>
      </div>
      <button type="submit" class="btn btn-primary" @submit.prevent @click="editOrCreate">Submit</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "CategoryFormComponent",
  props:{
    category: Object
  },
  data() {
    return {
      response: null,
      name: '',
      description: '',
    };
  },
  methods:{
    editOrCreate(){
      if(this.name!=='' && this.description!==''){
        if (typeof this.category === 'undefined') { //nije prosledjena kategorija znaci zove se sa create
          this.$axios.post('/api/categories/createCategory', {
            "name": this.name,
            "description": this.description
          }).then((response) => {
            this.response = response.data;
          });
        } else { //prosledjena je zove se sa edita
          this.$axios.post('/api/categories/editCategory', {
            "id": this.category.id,
            "name": this.name,
            "description": this.description
          }).then((response) => {
            this.response = response.data;
          });
        }
      }
    }
  },
  watch: {
    category(newCategory) {
      if (newCategory) {
        this.name = newCategory.name;
        this.description = newCategory.description;
      }
    }
  },
}
</script>

<style scoped>

</style>