<template>
  <div>
    <div class="container-fluid">
      <NavComponent/>
      <div class="container">
        <router-view/>
      </div>
    </div>
    <div class="position-fixed top-50 end-0 p-3" style="background-color: #e0e0e0; width: 15%; transform: translateY(-50%);">
      <ul class="list-unstyled">
        <li class="list-group-item" v-for="news in mostIntr" :key="news.id" style="margin-top: 10px" >
          <router-link class="nav-link" :to="{ name: 'singleNews', params: { id: news.id }}">{{ news.title }}</router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import NavComponent from "@/components/NavComponent";

export default {
  components: {NavComponent},
  data() {
    return {
      mostIntr: []
    };
  },
  created() {
    this.fetchMostIntr()
  },
  methods:{
    fetchMostIntr(){
      this.$axios.get(`/api/news/mostInteracted`).then((response) => {
        this.mostIntr = response.data;
      });
    }
  }
}
</script>