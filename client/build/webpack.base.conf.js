'use strict'
const path = require('path')
const utils = require('./utils')
const config = require('../config')
const vueLoaderConfig = require('./vue-loader.conf')

function resolve(dir) {
  return path.join(__dirname, '..', dir)
}

const createLintingRule = () => ({
  test: /\.(js|vue)$/,
  loader: 'eslint-loader',
  enforce: 'pre',
  include: [resolve('src'), resolve('test')],
  options: {
    formatter: require('eslint-friendly-formatter'),
    emitWarning: !config.dev.showEslintErrorsInOverlay
  }
})

module.exports = {

  // 基础目录，绝对路径，用于从配置中解析入口起点(entry point)和 loader
  context: path.resolve(__dirname, '../'),

  // 应用程序的起点入口
  entry: {
    app: './src/main.js'
  },

  // output 位于对象最顶级键(key)，包括了一组选项，指示 webpack 如何去输出、以及在哪里输出
  output: {

    // output 目录对应一个绝对路径。
    path: config.build.assetsRoot,

    // 使用 入口名称，来赋予每个 bundle 一个唯一的名称
    // filename: "[id].bundle.js"
    // filename: "[name].[hash].bundle.js"
    // filename: "[chunkhash].bundle.js"
    filename: '[name].js',

    // 选项指定在浏览器中所引用的「此输出目录对应的公开 URL」
    // 该选项的值是以 runtime(运行时) 或 loader(载入时) 所创建的每个 URL 为前缀。
    publicPath: process.env.NODE_ENV === 'production'
      ? config.build.assetsPublicPath
      : config.dev.assetsPublicPath
  },
  // 这些选项能设置模块如何被解析
  resolve: {

    // 确定解析那些文件类型
    extensions: ['.js', '.vue', '.json'],

    // 创建 import 或 require 的别名，来确保模块引入变得更简单。
    alias: {
      'vue$': 'vue/dist/vue.esm.js',
      '@': resolve('src')
    }
  },
  module: {
    // 规则数组，当规则匹配时使用。
    rules: [
      // 扩展运算符，展开后合并
      ...(config.dev.useEslint ? [createLintingRule()] : []),
      {
        // 条件会匹配 resource。既可以提供 Rule.resource 选项，
        // 也可以使用快捷选项 Rule.test，Rule.exclude 和 Rule.include。在 Rule 条件 中查看详细。
        test: /\.vue$/,

        // 每个入口(entry)指定使用一个 loader
        // https://vue-loader.vuejs.org/zh-cn/
        // vue-loader 是一个 webpack 的 loader，将vue组件转换为JavaScript模块
        loader: 'vue-loader',

        // 可以有一个 options 属性为字符串或对象。值可以传递到 loader 中，将其理解为 loader 选项。
        options: vueLoaderConfig
      },
      {
        test: /\.js$/,
        // https://doc.webpack-china.org/loaders/babel-loader/
        // 将ES6的文件转换成js文件
        loader: 'babel-loader?cacheDirectory',
        include: [resolve('src'), resolve('test'), resolve('node_modules/webpack-dev-server/client')]
      },
      {
        test: /\.svg$/,

        // 用来根据导入的 svg 文件自动生成 symbol 标签并插入 html
        // https://cisy.me/webpack-svg-sprite/
        loader: 'svg-sprite-loader',
        include: [resolve('src/icons')],
        options: {
          symbolId: 'icon-[name]'
        }
      },
      {
        test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,

        // Loads files as base64 encoded URL
        // 将图片编程BASE64，超过一定大小，编成一个文件。
        loader: 'url-loader',
        exclude: [resolve('src/icons')],
        options: {
          limit: 10000,
          name: utils.assetsPath('img/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('media/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: utils.assetsPath('fonts/[name].[hash:7].[ext]')
        }
      }
    ]
  },
  // Polyfill或者Polyfiller，是英国Web开发者 Remy Sharp 在咖啡店蹲坑的时候拍脑袋造出来的。
  // 当时他想用一个词来形容"用JavaScript（或者Flash之类的什么鬼）来实现一些浏览器不支持的原生API
  // 用于实现浏览器并不支持的原生API的代码

  // 其中每个属性都是 Node.js 全局变量或模块的名称
  // 用来设置是否被mock
  // true: Provide a polyfill.
  // "mock": Provide a mock that implements the expected interface but has little or no functionality.
  // "empty": Provide an empty object.
  // false: Provide nothing. Code that expects this object may crash with a ReferenceError.
  node: {
    // prevent webpack from injecting useless setImmediate polyfill because Vue
    // source contains it (although only uses it if it's native).
    setImmediate: false,
    // prevent webpack from injecting mocks to Node native modules
    // that does not make sense for the client
    dgram: 'empty',
    fs: 'empty',
    net: 'empty',
    tls: 'empty',
    child_process: 'empty'
  }
}
