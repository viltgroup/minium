browser.get("https://jquery.com/");

function browserResponseHeaders() {
  // https://gist.github.com/mmazer/5404301
  var parseResponseHeaders = function (headerStr) {
    var headers = {};
    if (!headerStr) {
      return headers;
    }
    var headerPairs = headerStr.split('\u000d\u000a');
    for (var i = 0, len = headerPairs.length; i < len; i++) {
      var headerPair = headerPairs[i];
      var index = headerPair.indexOf('\u003a\u0020');
      if (index > 0) {
        var key = headerPair.substring(0, index);
        var val = headerPair.substring(index + 2);
        headers[key] = val;
      }
    }
    return headers;
  };
  
  if (!$(this).data("responseHeadersInProgress")) {
    var url = window.location.href;
    var that = this;
    
    $.ajax({
      type: 'GET',
      url: url,
      success: function(data, textStatus, request) {
        $(that).data("responseHeaders", parseResponseHeaders(request.getAllResponseHeaders()));
      }
    });
    $(this).data("responseHeadersInProgress", true);
  }
  
  return $(this).data("responseHeaders") ? $(this) : $();
}

var headers = $(":root").applyWebElements(browserResponseHeaders).waitForExistence().data("responseHeaders");
expect(headers["X-Pingback"] || headers["x-pingback"]).to.be("https://jquery.com/xmlrpc.php");