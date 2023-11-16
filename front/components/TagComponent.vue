<template>
  <div class="d-inline-block rounded" style="background-color: #e0e0e0; margin-right: 8px">
    <router-link class="nav-link" :to="{ name: 'newsByTag', params: { tag: this.tag }}">{{ this.tag }}</router-link>
  </div>
</template>

<script>
export default {
  name: "TagComponent",
  props: {
    tag: null
  },
  methods: {

  },
}
</script>

<style scoped>

</style>