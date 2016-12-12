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
        joshuaAdminArticleAuditor: "./app/page/admin/admin-article-auditor.js",
        joshuaAdminArticleTable: "./app/page/admin/admin-article-table.js",
        joshuaAdminFellowshipDetails: "./app/page/admin/admin-fellowship-details.js",
        joshuaAdminFellowshipTable: "./app/page/admin/admin-fellowship-table.js",
        joshuaAdminHome: "./app/page/admin/admin-home.js",
        joshuaAdminMessageTable: "./app/page/admin/admin-message-table.js",
        joshuaAdminStaticPage: "./app/page/admin/admin-static-home.js",
        joshuaAdminUserDetails: "./app/page/admin/admin-user-details.js",
        joshuaAdminUserTable: "./app/page/admin/admin-user-table.js"
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