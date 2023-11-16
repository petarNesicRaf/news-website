<template>
  <div>
    <form @submit.prevent="submitForm">
      <div class="mb-3">
        <label>Name</label>
        <input v-model="name" type="text" class="form-control" required>
      </div>
      <div class="mb-3">
        <label>Surname</label>
        <input v-model="surname" type="text" class="form-control" required>
      </div>
      <div class="mb-3">
        <label>Email</label>
        <input v-model="email" type="email" class="form-control" required>
      </div>
      <div class="mb-3">
        <label>Role</label>
        <select v-model="role" class="form-select" required>
          <option value="ADMIN">ADMIN</option>
          <option value="CONTENT_CREATOR">CONTENT CREATOR</option>
        </select>
      </div>
      <div class="mb-3">
        <label>Password</label>
        <input v-model="password" class="form-control" required>
      </div>
      <div class="mb-3">
        <label>Confirm password</label>
        <input v-model="confirmPassword" class="form-control" required>
      </div>
      <button type="submit" class="btn btn-primary" @click="editOrCreate">Submit</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "UserFormComponent",
  props:{
    user: Object
  },
  data() {
    return {
      response: null,
      name: '',
      surname: '',
      email: '',
      role: 'ADMIN',
      password: '',
      confirmPassword: '',
    };
  },
  methods:{
    editOrCreate(){
      if(this.name!=='' && this.surname!=='' && this.email!=='' && this.password!=='' && this.confirmPassword!==''){
        if(this.password===this.confirmPassword){
          const jwt = localStorage.getItem('jwt');
          const headers = {
            Authorization: `Bearer ${jwt}`,
          };

          if (typeof this.user === 'undefined') { //nije prosledjena kategorija znaci zove se sa create
            this.$axios.post('/api/users/create', {
              "email" : this.email,
              "name" : this.name,
              "surname" : this.surname,
              "role" : this.role,
              "status" : 1,
              "password" : this.password
            }, {headers}).then((response) => {
              this.response = response.data;
            });
          } else { //prosledjena je zove se sa edita
            this.$axios.post('/api/users/editUser', {
              "email" : this.email,
              "name" : this.name,
              "surname" : this.surname,
              "role" : this.role,
              "password" : this.password,
              "id": this.user.id
            },{headers}).then((response) => {
              this.response = response.data;
            });
          }
        }
      }
    }
  },
  watch: {
    user(newUser) {
      if (newUser) {
        this.name = newUser.name;
        this.surname=newUser.surname;
        this.email=newUser.email;
        this.role=newUser.role;
      }
    }
  },
}
</script>

<style scoped>

</style>