var webpack = require("webpack");

module.exports = {
    entry: {
        joshua: "./app/page/normal.js",
        joshuaArticle: "./app/page/article.js",
        joshuaArticleCenter: "./app/page/article-center.js",
        joshuaContact: "./app/page/contact.js",
        joshuaEditor: "./app/page/editor.js",
        joshuaForget: "./app/page/forget.js",
        joshuaHome: "./app/page/home.js",
        joshuaLogon: "./app/page/logon.js",
        joshuaUserCenter: "./app/page/user-info.js",
    },
    output: {
        path: "../resources/public/assets/js",
        filename: "[name].js"
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery"
        }),
        new webpack.optimize.CommonsChunkPlugin("joshua-common.js")
    ],
    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel',
                query: {
                    presets: ["es2015"]
                }
            }, {
                test: /\.css$/,
                loader: 'style!css'
            }, {
                test: /\.scss$/,
                loader: 'style!css!sass'
            }, {
                test: /\.(png|jpg)$/,
                loader: 'url',
                query: {
                    limit: 8192,
                    publicPath: "/assets/js/",
                    name: "[name].[ext]"
                }
            }, {
                test: /\.(ttf|eot|svg|woff(2)?)(\?[a-z0-9=&.]+)?$/,
                loader: 'file',
                query: {
                    publicPath: "/assets/js/",
                    name: "[name].[ext]"
                }
            }
        ]
    }
};