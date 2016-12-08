module.exports = {
    entry: {
        joshua: "./app/joshua-front-page.js"
    },
    output: {
        path: "../resources/public/assets/js",
        filename: "[name].js"
    },
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