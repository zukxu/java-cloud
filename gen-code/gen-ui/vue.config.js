`use strict`
const path = require('path')

function resolve(dir) {
    return path.join(__dirname, dir)
}

const title = '代码生成器'
const port =
    module.exports = {
        //部署环境和开发环境下的URL
        publicPath: process.env.NODE_ENV === "production" ? "/" : "/",
        outputDir: 'dist',
        assetsDir: 'static',
        lintOnSave: false,
        productionSourceMap: false,
        //别名
        configureWebpack: {
            resolve: {
                alias: {
                    '@': resolve('src')
                }
            }
        },
        devServer: {
            port: process.env.VUE_APP_DEV_PORT,
            open: true,
            proxy: {
                [process.env.VUE_APP_BASE_API]: {
                    target: process.env.VUE_APP_proxy_TARGET,
                    changeOrigin: true,
                    pathRewrite: {
                        ['^' + process.env.VUE_APP_BASE_API]: ''
                    }
                }
            }


        }

    }