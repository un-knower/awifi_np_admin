var path = require('path');
var webpack = require('webpack');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

// 本地开发环境配置项 dev
var publicPath = '/static/np-admin/';
var scssLoader = 'vue-style-loader!css-loader?importLoaders=1!sass-loader!postcss-loader';

// 生产环境配置项 prod
if (process.env.NODE_ENV === 'production') {
    // publicPath = `/static/${suitCode}/`;
    scssLoader = ExtractTextPlugin.extract({
        use: ['css-loader', 'sass-loader', 'postcss-loader'],
        fallback: 'vue-style-loader'
    });
}

// __dirname 项目根目录
module.exports = {
    entry: {
        awifiui: path.resolve(__dirname, './src/awifi/hub/awifiui-css.js'),
    },
    output: {
        path: path.resolve(__dirname, `.${publicPath}`), // 生成文件目录
        publicPath: publicPath, // 静态文件引用路径
        filename: 'js/[name]-css.js'
    },
    module: {
        rules: [{
            test: /\.vue$/,
            loader: 'vue-loader',
            options: {
                loaders: {
                    // Since sass-loader (weirdly) has SCSS as its default parse mode, we map
                    // the "scss" and "sass" values for the lang attribute to the right configs here.
                    // other preprocessors should work out of the box, no loader config like this nessessary.
                    // 'scss': 'vue-style-loader!css-loader!sass-loader',
                    // 'sass': 'vue-style-loader!css-loader!sass-loader?indentedSyntax',
                    'scss': scssLoader,
                }
                // other vue-loader options go here
            }
        }, {
            test: /\.js$/,
            loader: 'babel-loader',
            exclude: /node_modules/,
        }, {
            test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
            loader: 'url-loader',
            query: {
                limit: 10000, // 10K
                name: 'img/[name].[ext]?[hash]'
            }
        }, {
            test: /\.scss$/,
            // loader: 'vue-style-loader!css-loader!sass-loader'
            loader: scssLoader
        }, {
            test: /\.css$/,
            // loader: 'vue-style-loader!css-loader!sass-loader'
            loader: scssLoader
        }, {
            test: /\.woff(\?v=\d+\.\d+\.\d+)?$/,
            loader: 'file-loader',
            options: {
                name: '[name].[ext]?[hash]'
            }
        }, {
            test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,
            loader: 'file-loader',
            options: {
                name: '[name].[ext]?[hash]'
            }
        }, {
            test: /\.eot(\?v=\d+\.\d+\.\d+)?$/,
            loader: 'file-loader',
            options: {
                name: '[name].[ext]?[hash]'
            }
        }, {
            test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
            loader: 'file-loader',
            options: {
                name: '[name].[ext]?[hash]'
            }
        }]
    },
    devServer: {
        historyApiFallback: true,
        noInfo: true,
    },
    performance: {
        hints: false
    },
    devtool: '#source-map',
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
