<template>
  <div>
    <div class="d-flex align-items-center justify-content-between border p-2 mb-2">
      <div>
        <h3>{{ user.name }} {{ user.surname }}</h3>
        <h5>{{ user.email }}</h5>
        <h5>{{ user.role }}</h5>
      </div>

      <div class="d-flex gap-2">
        <button class="btn btn-primary" @click="editUser">Edit</button>
        <div class="form-check form-switch" v-if="user.role!=='ADMIN'">
          <input class="form-check-input" type="checkbox" role="switch" id="statusSwitch" v-model="status" @change="toggleStatus">
          <label class="form-check-label" for="statusSwitch">Status</label>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserComponent",
  props: {
    user: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      status: this.user.status,
    }
  },
  methods: {
    async toggleStatus() {
      try {
        const jwt = localStorage.getItem('jwt');
        const headers = {
          Authorization: `Bearer ${jwt}`,
          'Content-Type': 'text/plain',
        };
        await this.$axios.post('/api/users/status', parseInt(this.user.id), {headers});
        this.$emit('status-changed');
      } catch (error) {
        console.error('Error toggling status:', error);
      }
    },
    editUser(){
      this.$router.push({ name: 'editUser', params: { id: this.user.id } });
    },
  },
};
</script>

<style scoped>
</style>
