<template>
  <div class="pt-5">
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="username">Username</label>
        <input v-model="email" type="text" class="form-control" id="username" aria-describedby="usernameHelp" placeholder="Enter username" required>
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input v-model="password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" required>
      </div>
      <button type="submit" class="btn btn-primary mt-2">Submit</button>
    </form>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      email: '',
      password: '',
    }
  },
  methods: {
    login() {
      if(this.email!=='' && this.password!==''){
        this.$axios.post('/api/users/login', {
          email: this.email,
          password: this.password,
        }).then(response => {
          localStorage.setItem('jwt', response.data.jwt)
          this.$router.push({name: 'home'});
          window.location.reload();
        })
      }
    }
  },
}
</script>

<style scoped>

</style>