module.exports = function ($) {
  return function(expr, args) {
    // dynamically create a function to evaluate expr if it is not a function
    var evaluator = typeof expr === 'function' ? expr : new Function("$", "args", " return (" +  expr + ")");
    
    var result = evaluator($, args);
    var type = 'null';

    if (result !== null && result !== undefined) {
      if (result instanceof $) {
        // a special case is when we only have one web element to return.
        // In that case we return it directly.
        if (result.length === 1) return result.get(0);

        // if it is a $ object, convert it to a normal array
        result = result.get();
        type = 'array';
      } else if ($.isArray(result) || (typeof result !== 'number' && typeof result !== 'boolean' && typeof result !== 'string')) {
        result = JSON.stringify(result);
        type = 'json';
      } else {
        type = typeof result;
      }
    }
    return type === 'array' ?  [ type ].concat(result) : [ type, result ];
  };
};