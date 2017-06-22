// adapted from node_modules/jquery/src/jquery.js
define( [
  // we only need some jquery modules for minium core
  "jquery/src/core",
	"jquery/src/selector",
	"jquery/src/traversing",
	"jquery/src/callbacks",
	"jquery/src/deferred",
	"jquery/src/core/ready",
	"jquery/src/support",
	"jquery/src/data",
	"jquery/src/queue",
	"jquery/src/queue/delay",
	"jquery/src/attributes",
	"jquery/src/event",
	"jquery/src/event/alias",
	"jquery/src/manipulation",
	"jquery/src/manipulation/_evalUrl",
	"jquery/src/wrap",
	"jquery/src/css",
	"jquery/src/css/hiddenVisibleSelectors",
	"jquery/src/serialize",
	"jquery/src/ajax",
	"jquery/src/ajax/xhr",
	"jquery/src/ajax/script",
	"jquery/src/ajax/jsonp",
	"jquery/src/ajax/load",
	"jquery/src/event/ajax",
	"jquery/src/effects",
	"jquery/src/effects/animatedSelector",
	"jquery/src/offset",
	"jquery/src/dimensions",
	"jquery/src/deprecated"
], function( jQuery ) {

"use strict";

// if for some reason some other library calls noConflict...
jQuery.noConflict = function () {
  return jQuery;
}

return {
  $: jQuery,
	evalExpression : require("./utils/evalExpression")(jQuery),
  loadStyles : require("./utils/loadStyles")(jQuery)
};

} );
