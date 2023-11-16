<template>
  <div>
    <button class="btn btn-primary" @click="createUser">Create user</button>
    <ul class="list-group">
      <li class="list-group-item" v-for="user in users" :key="user.id"> <user-component @status-changed="fetchUsers" :user="user"/> </li>
    </ul>
  </div>
</template>

<script>
import UserComponent from "@/components/UserComponent";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Users",
  components: {UserComponent},
  data() {
    return {
      users: [],
    }
  },
  created() {
    this.fetchUsers()
  },
  methods:{
    createUser(){
      this.$router.push({ name: 'createUser' });
    },
    fetchUsers(){
      const jwt = localStorage.getItem('jwt');
      const headers = {
        Authorization: `Bearer ${jwt}`,
      };
      this.$axios.get('/api/users', { headers }).then((response) => {
        this.users = response.data;
      });
    }
  }
}
</script>

<style scoped>

</style>