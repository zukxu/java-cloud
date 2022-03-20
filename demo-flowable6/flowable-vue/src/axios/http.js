import axios from "axios";
import qs from "qs";
import Vue from "vue";
// import store from 'store/index'
import router from "../router";
import {Message} from "element-ui";

axios.defaults.timeout = 30 * 1000;; // 请求超时时间
axios.defaults.baseURL = process.env.VUE_APP_BASE_API;

axios.defaults.headers.post["Content-Type"] = "application/json"
    // axios.defaults.headers.common["token"] = getCookie("token") ? getCookie("token") : ""
    // axios respone拦截器
    // axios.defaults.headers.delete["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8"; // delete请求头的设置
let loading;
axios.interceptors.request.use(
    (config) => {
        if (sessionStorage.getItem("Authorization")) {
            config.headers.Authorization = sessionStorage.getItem("Authorization");
        }
        loading = Vue.prototype.$loading({
            lock: true,
            text: "数据加载中,请等待...",
            spinner: "el-icon-loading",
            background: "rgba(0,0,0,.7)",
        });
        return config;
    },
    (error) => {
        loading.close();
        return Promise.reject(err);
    }
);
axios.interceptors.response.use(
    (response) => {
        loading.close();
        if (response.status === 200) {
            if (response.data.code === 401) {
                Message.error("未授权，请重新登录(401)");
                sessionStorage.clear();
                loading.close();
                router.push("/login");
                return false
            }
            return Promise.resolve(response);


        } else {

            Message.error(response.data.msg);
            return Promise.reject(response);
        }
    },
    (err) => {
        if (err && err.response) {
            switch (err.response.status) {
                case 302:
                    err.message = "用户信息获取超时!请重新登录";
                    Message.error(err.message);
                    sessionStorage.clear();
                    loading.close();
                    router.push("/login");
                    break;
                case 400:
                    err.message = "请求错误(400)";
                    break;
                case 401:
                    err.message = "未授权，请重新登录(401)";
                    Message.error(err.message);
                    sessionStorage.clear();
                    loading.close();
                    router.push("/login");
                    break;
                case 403:
                    err.message = "拒绝访问(403)";
                    break;
                case 404:
                    err.message = "请求出错(404)";
                    break;
                case 408:
                    err.message = "请求超时(408)";
                    break;
                case 500:
                    if (err.response.data.message) {
                        Message.error(err.response.data.message);
                    }
                    err.message = "服务器错误(500)";
                    break;
                case 501:
                    err.message = "服务未实现(501)";
                    break;
                case 502:
                    err.message = "网络错误(502)";
                    break;
                case 503:
                    err.message = "服务不可用(503)";
                    break;
                case 504:
                    err.message = "网络超时(504)";
                    break;
                case 505:
                    err.message = "HTTP版本不受支持(505)";
                    break;
                default:
                    err.message = `连接出错(${err.response.status})!`;
            }
        } else {
            err.message = "用户信息获取超时!请重新登录";
        }
        err.message = "用户信息获取超时!请重新登录";
        // Message.error(err.message);
        // sessionStorage.clear();
        loading.close();
        // router.push("/login");
        return Promise.reject(err);
    }
);
/**
 * 封装get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
function get(url, params = {}) {
    return new Promise((resolve, reject) => {

        axios
            .get(url, {
                params: params,
            })
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err);
            });
    });
    // 或者return axios.get();
}
/**
 * form方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
function form(url, params) {
    axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8"; // post请求头的设置
    return new Promise((resolve, reject) => {
        axios
            .post(url, qs.stringify(params))
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err);
            });
    });
    //  或者return axios.post();
}

/**
 * post方法，对应post请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
function post(url, postData, query = {}) {
    return axios.post(url, postData, {
        params: query,
    });
}
/**
 * delete方法，对应put请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
function Delete(url, params) {
    return new Promise((resolve, reject) => {
        axios
            .delete(url, {
                params: params,
            })
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err);
            });
    });
    //  或者return axios.post();
}

/**
 * delete方法，对应put请求
 * @param {String} url [请求的url地址]
 * @param {Object} params [请求时携带的参数]
 */
function put(url, params) {
    return new Promise((resolve, reject) => {
        axios
            .put(url, params)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err);
            });

    });
    //  或者return axios.post();
}
export {
    get,
    post,
    form,
    Delete,
    put
};