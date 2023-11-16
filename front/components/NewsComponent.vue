<template>
  <router-link class="nav-link" :to="{ name: 'singleNews', params: { id: news.id }}">
    <h4>
      {{news.title}}
    </h4>
    <h6>
      {{news.text}}
    </h6>
  </router-link>
</template>

<script>
export default {
  name: "NewsComponent",
  props: {
    news: {
      type: Object,
      required: true,
    }
  },
  methods: {

  },
}

</script>

<style scoped>

</style>