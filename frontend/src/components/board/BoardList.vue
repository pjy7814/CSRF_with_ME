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
        <b-button variant="outline-primary" @click="moveWrite()"
          >글쓰기</b-button
        >
        <b-dropdown
          id="dropdown-right"
          right
          :text="selectedFilerOption"
          class="m-2"
          size="md"
        >
          <b-dropdown-item
            v-for="curFilter in filterOptions"
            :key="curFilter"
            :value="curFilter"
            @click="clickFilterOption(curFilter)"
            >{{ curFilter }}</b-dropdown-item
          >
        </b-dropdown>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-table
          striped
          hover
          :items="articles"
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
              {{ data.item.subject }}
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
        <b-button size="sm" class="my-2 my-sm-0" @click="searchPost()"
          >Search</b-button
        >
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
      fields: [
        { key: "articleno", label: "글번호", tdClass: "tdClass" },
        { key: "subject", label: "제목", tdClass: "tdSubject" },
        { key: "userid", label: "작성자", tdClass: "tdClass" },
        { key: "regtime", label: "작성일", tdClass: "tdClass" },
        { key: "hit", label: "조회수", tdClass: "tdClass" },
      ],
      filterOptions: ["최신순", "조회순", "인기순"],
      selectedFilerOption: "필터링",
    };
  },
  computed: {
    rows() {
      return this.articles.length;
    },
  },
  created() {
    let param = {
      pg: 1,
      spp: 20,
      key: null,
      word: null,
    };
    listArticle(
      param,
      ({ data }) => {
        this.articles = data;
      },
      (error) => {
        console.log(error);
      }
    );
    this.currentBoard = this.$route.path.split("/")[1];
    if (this.currentBoard === "noticeboard") {
      this.currentBoardTitle = "공지사항 게시판";
    } else if (this.currentBoard === "shareboard") {
      this.currentBoardTitle = "공유 게시판";
    } else {
      //의도치 않은 접근! 에러 페이지 또는 메인 페이지로 강제 이동 중 택 1 선택 필요
    }
  },
  methods: {
    moveWrite() {
      this.$router.push({
        name: `${this.currentBoard}write`,
        params: {
          currentBoard: this.currentBoard,
        },
      });
    },
    viewArticle(article) {
      this.$router.push({
        name: "boardview",
        params: { articleno: article.articleno },
      });
    },
    clickFilterOption(filterOption) {
      //filterOptions에 포함된 필터 조건이며, 기존에 선택됬던 필터 조건과 다를 경우
      if (
        this.filterOptions.includes(filterOption) &&
        filterOption !== this.selectedFilerOption
      ) {
        this.selectedFilerOption = filterOption;

        //서버와 통신을 통해 필터링된 게시글을 받아온다.
      }
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
