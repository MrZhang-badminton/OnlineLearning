import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from "axios";

Vue.config.productionTip = false;
Vue.prototype.$ajax = axios;

/**
 * 拦截器
 */
axios.interceptors.request.use(function (config) {
    console.log("请求:", config);
    return config;
}, error => {
});
axios.interceptors.response.use(function (responese) {
    console.log("返回结果:", responese);
    return responese;
}, error => {
});

new Vue({
    router,
    render: h => h(App),
}).$mount('#app')

console.log("环境：", process.env.NODE_ENV);