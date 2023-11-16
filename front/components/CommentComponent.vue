<template>
    <div class="card">
      <div class="card-body">
        <p>{{ comment.comment }}</p>

        <div class="d-flex justify-content-between">
          <div class="d-flex flex-row align-items-center">
            <p class="small mb-0 ms-2">{{ comment.author }}</p>
          </div>
          <div class="d-flex flex-row align-items-center">
            <i class="bi bi-hand-thumbs-up" style="margin-right: 15px; cursor:pointer" @click="likeComment(comment)"></i>
            <i class="bi bi-hand-thumbs-down" style="cursor:pointer" @click="dislikeComment(comment)"></i>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: "CommentComponent",
  data() {
    return {
      interactedCommentKey: 'interactedComment',
    }
  },
  props: {
    comment: {
      type: Object,
      required: true,
    }
  },
  methods: {
    async likeComment(com){
      const commentId=com.id.toString();
      const commentsInteracted = JSON.parse(localStorage.getItem(this.interactedCommentKey)) || [];

      if (!commentsInteracted.includes(commentId)) {
        try {
          await this.$axios.patch(`/api/comments/like`,parseInt(commentId), {
            headers: { 'Content-Type': 'text/plain' }
          });
          commentsInteracted.push(commentId);
          localStorage.setItem(this.interactedCommentKey, JSON.stringify(commentsInteracted));
          console.log("KOMENTAR LAJKOVAN")
        } catch (error) {
          console.error("Error incrementing views:", error);
        }
      }
      else{
        console.log("VEC STE LAJKOVALI KOMENTAR")
      }
    },
    async dislikeComment(com){
      const commentId=com.id.toString();
      const commentsInteracted = JSON.parse(localStorage.getItem(this.interactedCommentKey)) || [];

      if (!commentsInteracted.includes(commentId)) {
        try {
          await this.$axios.patch(`/api/comments/dislike`,parseInt(commentId), {
            headers: { 'Content-Type': 'text/plain' }
          });
          commentsInteracted.push(commentId);
          localStorage.setItem(this.interactedCommentKey, JSON.stringify(commentsInteracted));
          console.log("KOMENTAR DISLAJKOVAN")
        } catch (error) {
          console.error("Error incrementing views:", error);
        }
      }
      else{
        console.log("VEC STE DISLAJKOVALI KOMENTAR")
      }
    },
  },
}
</script>

<style scoped>

</style>