<template>
  <user-form-component :user="user"/>
</template>

<script>
import UserFormComponent from "@/components/UserFormComponent";
export default {
  name: "EditUser",
  components: {UserFormComponent},
  data() {
    return {
      user: null,
    };
  },
  created() {
    this.fetchUserData();
  },
  methods:{
    fetchUserData(){
      const userId = this.$route.params.id;
      const jwt = localStorage.getItem('jwt');
      const headers = {
        Authorization: `Bearer ${jwt}`,
      };
      this.$axios.get(`/api/users/${userId}`, {headers}).then((response) => {
        this.user = response.data;
      });
    }
  }

}
</script>

<style scoped>

</style>