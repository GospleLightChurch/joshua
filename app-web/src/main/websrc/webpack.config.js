module.exports = {
    entry: {
        joshua: "./app/joshua-front-page.js",
        joshuaAdmin: "./app/joshua-admin-page.js"
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
            },
            {
                test: /\.scss/,
                loader: 'style!css!sass'
            }
        ]
    }
};