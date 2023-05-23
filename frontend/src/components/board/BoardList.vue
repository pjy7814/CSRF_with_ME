<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show
          ><h3>{{ currentBoardTitle }}</h3></b-alert
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-right">
        <b-button variant="outline-primary" @click="moveWrite()">글쓰기</b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-table
          striped
          hover
          :items="paginatedArticles"
          :fields="fields"
          @row-clicked="viewArticle"
        >
          <template #cell(subject)="data">
            <router-link
              :to="{
                name: 'boardview',
                params: { articleno: data.item.articleno },
              }"
            >
              {{ data.item.boardTitle }}
            </router-link>
          </template>
        </b-table>
      </b-col>
    </b-row>
    <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      size="sm"
      align="center"
      class="mt-4"
    >
    </b-pagination>

    <div class="rowEndWrapper">
      <div id="searchWrapper">
        <b-form-input
          size="sm"
          class="mr-sm-2"
          placeholder="Search"
          ref="searchKeyword"
        ></b-form-input>
        <b-button size="sm" class="my-2 my-sm-0" @click="searchPost()">Search</b-button>
      </div>
    </div>
  </b-container>
</template>

<script>
import { listArticle } from "@/api/board";

export default {
  name: "BoardList",
  data() {
    return {
      perPage: 10,
      currentPage: 1,
      currentBoardTitle: "",
      currentBoard: "",
      articles: [],
      curArticles: [],
      fields: [
        { key: "boardId", label: "글번호", tdClass: "tdClass" },
        { key: "boardTitle", label: "제목", tdClass: "tdSubject" },
        { key: "boardWriterId", label: "작성자", tdClass: "tdClass" },
        { key: "boardContent", label: "작성일", tdClass: "tdClass" },
      ],
    };
  },
  computed: {
    rows() {
      return this.articles.length;
    },
    paginatedArticles() {
      const startIndex = (this.currentPage - 1) * this.perPage;
      const endIndex = startIndex + this.perPage;
      return this.articles.slice(startIndex, endIndex);
    },
  },
  watch: {
    currentPage(newPage) {
      // 새로운 페이지를 받았을 때 실행할 작업을 여기에 작성합니다.
      this.fetchData(newPage);
    },
  },
  created() {
    let param = {
      pg: 1,
      spp: 100,
      key: null,
      word: null,
      boardType: null,
    };

    this.currentBoard = this.$route.path.split("/")[1];
    if (this.currentBoard === "noticeboard") {
      this.currentBoardTitle = "공지사항 게시판";
      param.boardType = "notice";
      listArticle(
        param,
        ({ data }) => {
          this.articles = data;
        },
        (error) => {
          console.log(error);
        }
      );
    } else if (this.currentBoard === "shareboard") {
      this.currentBoardTitle = "공유 게시판";
      param.boardType = "share";

      listArticle(
        param,
        ({ data }) => {
          this.articles = data;
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      //의도치 않은 접근! 에러 페이지 또는 메인 페이지로 강제 이동 중 택 1 선택 필요
    }
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: `${this.currentBoard}write` });
    },
    viewArticle(article) {
      this.$router.push({
        name: "boardview",
        params: { articleno: article.articleno },
      });
    },
    searchPost() {
      //키워드와 필터를 합한 검색 실시 => 상의 필요
      const currentKeyword = this.$refs.searchKeyword.$el.value;
      console.log(currentKeyword);

      if (this.selectedFilerOption) {
        //필터링 할 조건이 있을 때
      } else {
        //필터링 할 조건이 없을 때
      }
    },
    fetchData(page) {
      console.log("page:", page);
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}

.rowEndWrapper {
  display: flex;
  justify-content: flex-end;
  flex-direction: row;
  align-items: center;
}
#searchWrapper {
  display: flex;
  justify-content: center;
  flex-direction: row;
  align-items: center;
  width: 20vw;
}
</style>
