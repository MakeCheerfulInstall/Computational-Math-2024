const path = require('path');

const HtmlWebpackPlugin = require("html-webpack-plugin");

const MiniCssExtractPlugin = require("mini-css-extract-plugin");

module.exports = {
    mode: "development",
    entry: './main.js',
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [MiniCssExtractPlugin.loader, "css-loader"]
            }
        ],
    },
    resolve: {
        extensions: ['.js', '.css'],
    },
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'build'),
    },
    plugins: [
        new HtmlWebpackPlugin({
            title: 'Lab 2',
            template: './index.html'
        }),
        new MiniCssExtractPlugin({
            filename:"bundle.css"
        })
    ],
    devServer: {
        static: path.join(__dirname, "build"),
        compress: true,
        port: 4000,
    },
};