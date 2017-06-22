/*global __dirname, require, module*/

"use strict";

var webpack = require('webpack');
var path = require('path');
// var UnminifiedWebpackPlugin = require('unminified-webpack-plugin');
  
module.exports = {
  entry: {
    "jquery": './src/minium-jquery.js',
    // in the future, we may support other jquery-like libraries, like cash or zepto
    //cash: './src/minium-cash.js'
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'minium-[name].min.js',
    library: 'minium',
    libraryTarget: 'window'
  },
  resolve: {
    modules: ['node_modules'],
    extensions: ['.js']
  },
  externals: {
    window: 'window',
    global: 'global'
  },
  plugins: [
    new webpack.optimize.UglifyJsPlugin({
      // to remove any kind of comments
      // based on https://github.com/webpack/webpack/issues/1205#issuecomment-310182697
      comments: () => false,
    })
    // , new UnminifiedWebpackPlugin()
  ]
};
