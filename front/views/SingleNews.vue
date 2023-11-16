<template>
  <div v-if="news">
    <h4>
      {{news.title}}
    </h4>
    <h6>
      {{news.text}}
    </h6>
    <div class="row" style="margin-bottom: 50px;">
      <div class="col-md-6">{{ news.author }}</div>
      <div  class="col-md-3 d-flex flex-row align-items-center">{{ new Date(news.dateTime).toLocaleString('nl-NL') }}</div>
      <div  class="col-md-3 d-flex flex-row align-self-end">
        <i class="bi bi-hand-thumbs-up" style="margin-right: 15px; cursor:pointer" @click="likeNews"></i>
        <i class="bi bi-hand-thumbs-down" style="cursor:pointer" @click="dislikeNews"></i>
      </div>
    </div>

    <ul class="d-flex flex-wrap list-unstyled" style="display: flex; flex-wrap: wrap;">
      <li class="list-group-item" v-for="tag in tagList" :key="tag.tag">
        <tag-component :tag="tag"/>
      </li>
    </ul>

    <div>
      <h2>Read more</h2>
      <ul class="list-unstyled">
        <li class="list-group-item" v-for="news in readMore" :key="news.id">
          <router-link class="nav-link" :to="{ name: 'singleNews', params: { id: news.id }}">{{ news.title }}</router-link>
        </li>
      </ul>
    </div>

    <form>
      <div class="form-group">
        <input v-model="author" class="form-control" placeholder="Author" style="margin-bottom: 10px;" required>
        <textarea v-model="comment" class="form-control"  rows="3" style="resize: none; margin-bottom: 10px;" placeholder="Comment" required></textarea>
      </div>
      <button type="submit" class="btn btn-primary" style="margin-bottom: 50px;" @submit.prevent @click="postComment">Submit</button>
    </form>


    <div v-if="commentList.length > 0" class="row d-flex justify-content-center">
      <div class="col-md-8 col-lg-6">
        <div class="card shadow-0 border" style="background-color: #f0f2f5;">
          <ul class="list-group">
            <li class="list-group-item" v-for="comment in commentList" :key="comment.id"><comment-component :comment="comment"/></li>
          </ul>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import CommentComponent from "@/components/CommentComponent";
import TagComponent from "@/components/TagComponent";
export default {
  name: "SingleNews",
  components: {TagComponent, CommentComponent},
  data() {
    return {
      news: null,
      commentList: [],
      author: '',
      comment: '',
      visitedNewsKey: 'visitedNews',
      interactedNewsKey: 'interactedNews',
      tagList: [],
      readMore: []
    };
  },
  created() {
    this.fetchNewsData()
    this.handleNewsVisit()
  },
  watch: {
    '$route.params.id': function (newNewsId, oldNewsId) {
      if (newNewsId !== oldNewsId) {
        this.fetchNewsData();
      }
    },
  },
  methods: {
    fetchNewsData() {
      const newsId = this.$route.params.id

      this.$axios.get(`/api/news/${newsId}`).then((response) => {
        this.news = response.data;
      });
      this.fetchComments()
      this.fetchTags()
      this.fetchReadMore()
    },
    async postComment() {
      if(this.comment!=='' && this.author!==''){
        console.log(this.author + " " + this.comment)
        const commentData = {
          author: this.author,
          comment: this.comment,
          idNews: this.news.id
        };

        try {
          const response = await this.$axios.post("/api/comments/createComment", commentData);
          this.fetchComments()
          console.log("Comment created:", response.data);
        } catch (error) {
          console.error("Error creating comment:", error);
        }
      }
    },
    async fetchComments(){
      const newsId = this.$route.params.id
      this.$axios.get(`/api/comments/${newsId}`).then((response) => {
        this.commentList = response.data;
      });
    },
    async fetchTags(){
      const newsId = this.$route.params.id
      this.$axios.get(`/api/tags/getAllTagsFromNews/${newsId}`).then((response) => {
         this.tagList = response.data;
      });
    },
    async fetchReadMore(){
      const newsId = this.$route.params.id
      this.$axios.get(`/api/tags/readMore/${newsId}`).then((response) => {
        this.readMore = response.data;
      });
    },
    async handleNewsVisit() {
      const newsId = this.$route.params.id.toString();
      const visitedNews = JSON.parse(localStorage.getItem(this.visitedNewsKey)) || [];

      if (!visitedNews.includes(newsId)) {
        try {
          await this.$axios.patch(`/api/news/read`,parseInt(newsId), {
            headers: { 'Content-Type': 'text/plain' }
          });
        visitedNews.push(newsId);
        localStorage.setItem(this.visitedNewsKey, JSON.stringify(visitedNews));
        } catch (error) {
          console.error("Error incrementing views:", error);
        }
      }
    },
    async likeNews(){
      const newsId = this.$route.params.id.toString();
      const newsInteracted = JSON.parse(localStorage.getItem(this.interactedNewsKey)) || [];

      if (!newsInteracted.includes(newsId)) {
        try {
          await this.$axios.patch(`/api/news/like`,parseInt(newsId), {
            headers: { 'Content-Type': 'text/plain' }
          });
          newsInteracted.push(newsId);
          localStorage.setItem(this.interactedNewsKey, JSON.stringify(newsInteracted));
          console.log("VEST LAJKOVANA")
        } catch (error) {
          console.error("Error incrementing views:", error);
        }
      }
      else{
        console.log("VEC STE LAJKOVALI VEST")
      }
    },
    async dislikeNews(){
      const newsId = this.$route.params.id.toString();
      const newsInteracted = JSON.parse(localStorage.getItem(this.interactedNewsKey)) || [];

      if (!newsInteracted.includes(newsId)) {
        try {
          await this.$axios.patch(`/api/news/dislike`,parseInt(newsId), {
            headers: { 'Content-Type': 'text/plain' }
          });
          newsInteracted.push(newsId);
          localStorage.setItem(this.interactedNewsKey, JSON.stringify(newsInteracted));
          console.log("VEST DISLAJKOVANA")
        } catch (error) {
          console.error("Error incrementing views:", error);
        }
      }
      else{
        console.log("VEC STE DISLAJKOVALI VEST")
      }
    },
  }
}

</script>

<style scoped>

</style>