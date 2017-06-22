var glob = require('glob');
var UglifyJS = require("uglify-js");
var fs = require('fs');
var path = require('path');

function minify(filepath) {
  var result = UglifyJS.minify(filepath, {
    screw_ie8: false
  });
  var filename = path.basename(filepath);
  fs.writeFileSync('./dist/' + filename.replace(/\.js/, '.min.js'), result.code);
  console.log(filename + ' was minified');
}

glob('./src/extensions/*.js', function (er, files) {
  files.forEach(minify);
});