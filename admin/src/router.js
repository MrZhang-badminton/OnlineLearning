import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login"
import Admin from "./views/admin"
import Welcome from "./views/admin/welcome"
import Category from "./views/admin/category"
import Teacher from "./views/admin/teacher"
import Course from "./views/admin/course"
import Chapter from "./views/admin/chapter"
import Section from "./views/admin/section"
import File from "./views/admin/file"
import Test from "./views/test"
import User from "./views/admin/user"
import Resource from "./views/admin/resource"
import Role from "./views/admin/role"
import Member from "./views/admin/member"
import Sms from "./views/admin/sms"

Vue.use(Router)

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/login"

    }, {
        path: "",
        redirect: "/login"
    }, {
        path: "/login",
        component: Login
    }, {
        path: "/test",
        component: Test
    }, {
        path: "/",
        name: "admin",
        component: Admin,
        meta: {
            loginRequire: true
        },
        children: [{
            path: "welcome",
            name: "welcome",
            component: Welcome,
        }, {
            path: "business/course",
            name: "business/course",
            component: Course,
        }, {
            path: "business/chapter",
            name: "business/chapter",
            component: Chapter,
        }, {
            path: "business/section",
            name: "business/section",
            component: Section,
        }, {
            path: "business/category",
            name: "business/category",
            component: Category,
        }, {
            path: "business/teacher",
            name: "business/teacher",
            component: Teacher,
        }, {
            path: "business/member",
            name: "business/member",
            component: Member,
        },{
            path: "business/sms",
            name: "business/sms",
            component: Sms,
        }, {
            path: "file/file",
            name: "file/file",
            component: File,
        }, {
            path: "system/user",
            name: "system/user",
            component: User,
        }, {
            path: "system/resource",
            name: "system/resource",
            component: Resource,
        }, {
            path: "system/role",
            name: "system/role",
            component: Role,
        },]
    }]

})