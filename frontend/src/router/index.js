import Vue from "vue";
import VueRouter from "vue-router";
import AppMain from "@/views/AppMain";
import AppDestinationInfo from "@/views/AppDestinationInfo";
import AppError from "@/views/AppError";
import store from "@/store";

Vue.use(VueRouter);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthMember = async (to, from, next) => {
  const checkMemberInfo = store.getters["memberStore/checkMemberInfo"];
  const checkToken = store.getters["memberStore/checkToken"];
  if (!checkToken || checkMemberInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    router.push({ name: "login" });
  } else {
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "main",
    component: AppMain,
  },
  {
    path: "/destination",
    name: "destination",
    component: AppDestinationInfo,
  },
  {
    path: "/member",
    name: "member",
    component: () =>
      import(/* webpackChunkName: "member" */ "@/views/AppMember"),
    children: [
      {
        path: "join",
        name: "join",
        component: () =>
          import(
            /* webpackChunkName: "member" */ "@/components/member/memberRegister"
          ),
      },
      {
        path: "login",
        name: "login",
        component: () =>
          import(
            /* webpackChunkName: "member" */ "@/components/member/memberLogin"
          ),
      },
      {
        path: "mypage",
        name: "mypage",
        beforeEnter: onlyAuthMember,
        component: () =>
          import(
            /* webpackChunkName: "member" */ "@/components/member/memberMyPage"
          ),
      },
    ],
  },
  {
    path: "/noticeboard",
    name: "noticeboard",
    component: () =>
      import(/* webpackChunkName: "board" */ "@/views/AppNoticeBoard"),
    redirect: "/noticeboard/list",
    children: [
      {
        path: "list",
        name: "noticeboardlist",
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/board/BoardList"
          ),
      },
      {
        path: "write",
        name: "noticeboardwrite",
        beforeEnter: onlyAuthMember,
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/board/BoardWrite"
          ),
      },
      {
        path: "view/:articleno",
        name: "noticeboardview",
        beforeEnter: onlyAuthMember,
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/board/BoardView"
          ),
      },
      {
        path: "modify",
        name: "noticeboardmodify",
        beforeEnter: onlyAuthMember,
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/board/BoardModify"
          ),
      },
      {
        path: "delete/:articleno",
        name: "noticeboarddelete",
        beforeEnter: onlyAuthMember,
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/board/BoardDelete"
          ),
      },
    ],
  },
  {
    path: "/shareboard",
    name: "shareboard",
    component: () =>
      import(/* webpackChunkName: "board" */ "@/views/AppShareBoard"),
    redirect: "/shareboard/list",
    children: [
      {
        path: "list",
        name: "shareboardlist",
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/board/BoardList"
          ),
      },
      {
        path: "write",
        name: "shareboardwrite",
        beforeEnter: onlyAuthMember,
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/board/BoardWrite"
          ),
      },
      {
        path: "view/:articleno",
        name: "shareboardview",
        beforeEnter: onlyAuthMember,
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/board/BoardView"
          ),
      },
      {
        path: "modify",
        name: "shareboardmodify",
        beforeEnter: onlyAuthMember,
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/board/BoardModify"
          ),
      },
      {
        path: "delete/:articleno",
        name: "shareboarddelete",
        beforeEnter: onlyAuthMember,
        component: () =>
          import(
            /* webpackChunkName: "board" */ "@/components/board/BoardDelete"
          ),
      },
    ],
  },
  {
    path: "/error",
    name: "error",
    props: true,
    component: AppError,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
