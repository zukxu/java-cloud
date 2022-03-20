const webpack = require('webpack')
const FileManagerPlugin = require('filemanager-webpack-plugin')

module.exports = {
    publicPath: "./",
    outputDir: "dist",
    assetsDir: "static",
    indexPath: "index.html",
    filenameHashing: true,
    lintOnSave: process.env.NODE_ENV === "production",
    runtimeCompiler: false,
    productionSourceMap: true,
    integrity: false,
    configureWebpack: {
        resolve: {
            alias: {
                '@a': '@/assets',
                '@c': '@/components',
                '@v': '@/views',
                '@u': '@/utils'
            }
        },
        externals: {
            'AMap': 'AMap' // 高德地图配置
        },
    },
    css: {
        extract: false,
        sourceMap: false,
        modules: false,
        loaderOptions: {}
    },
    devServer: {
        port: process.env.VUE_APP_devServer_port,
        proxy: {
            "/api": {
                target: 'http://192.168.0.21:8090',
                // target: 'http://192.168.0.97:8090',
                // target: 'http://192.168.0.248:8090',
                // target: 'http://192.168.0.168:8090',
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    "^/api": ""
                }
            }
        },


    },

};
