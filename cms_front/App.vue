<template>
  <div class="container-fluid">
    <NavComponent/>
    <div class="container">
      <router-view/>
    </div>
  </div>
</template>

<script>
import NavComponent from "@/components/NavComponent";

export default {
  components: {NavComponent},
}
</script>
