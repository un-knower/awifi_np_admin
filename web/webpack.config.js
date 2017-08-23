var path = require('path');
var webpack = require('webpack');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

// 本地开发环境配置项 dev
var publicPath = '/static/np-admin/';
var proxyTarget = 'http://192.168.41.79'; // alpha数据
// var proxyTarget = 'http://localhost:8080'; // local数据
var scssLoader = 'vue-style-loader!css-loader?importLoaders=1!sass-loader!postcss-loader';

// 生产环境配置项 prod
if (process.env.NODE_ENV === 'production') {
    // publicPath = '/static/np-admin/';

    scssLoader = ExtractTextPlugin.extract({
        use: ['css-loader', 'sass-loader', 'postcss-loader'],
        fallback: 'vue-style-loader'
    });
}

module.exports = {
    entry: {
        'login': './src/login.js',
        'index': './src/index.js',
    },
    output: {
        path: path.resolve(__dirname, `.${publicPath}`), // 生成文件目录
        publicPath: publicPath, // 静态文件引用路径
        filename: 'js/[name].js'
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                options: {
                    loaders: {
                        // Since sass-loader (weirdly) has SCSS as its default parse mode, we map
                        // the "scss" and "sass" values for the lang attribute to the right configs here.
                        // other preprocessors should work out of the box, no loader config like this nessessary.
                        'scss': scssLoader,
                        'sass': 'vue-style-loader!css-loader!sass-loader?indentedSyntax'
                    }
                    // other vue-loader options go here
                }
            }, {
                test: /\.js$/,
                loader: 'babel-loader',
                exclude: /node_modules/
            }, {
                test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
                loader: 'url-loader',
                options: {
                    limit: 10000, // 10KB
                    name: 'img/[name].[ext]?[hash]'
                }
            }, {
                test: /\.scss$/,
                // loader: 'vue-style-loader!css-loader!sass-loader'
                loader: scssLoader
            }
        ]
    },
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.common.js',
            'awifiui$': path.resolve(__dirname, './src/awifiui/dist/awifiui.js'),
            'awifi': path.resolve(__dirname, './src/awifi'),
        }
    },
    devServer: {
        historyApiFallback: true,
        noInfo: true,
        proxy: {
            '/admin': {
                target: proxyTarget, //'http://192.168.41.80',
                secure: false
            },
            '/public': {
                target: proxyTarget, //'http://192.168.41.80',
                secure: false
            },
            '/api': {
                target: proxyTarget, //'http://192.168.41.80',
                secure: false
            }
        }
    },
    performance: {
        hints: false
    },
    devtool: '#source-map'
};

if (process.env.NODE_ENV === 'production') {
    module.exports.devtool = '#source-map';
    // http://vue-loader.vuejs.org/en/workflow/production.html
    module.exports.plugins = (module.exports.plugins || []).concat([
        new webpack.DefinePlugin({
            'process.env': {
                NODE_ENV: '"production"'
            }
        }),
        new webpack.optimize.UglifyJsPlugin({
            sourceMap: true,
            compress: {
                warnings: false
            }
        }),
        new webpack.LoaderOptionsPlugin({
            minimize: true
        }),
        // 输出独立文件
        new ExtractTextPlugin({
            filename: 'css/[name].css',
            disable: false,
            allChunks: true
        }),
    ])
}
