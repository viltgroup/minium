// based on http://stackoverflow.com/a/13106698
jQuery.fn.highlight = function () {
    $(this).each(function () {
        var el = $(this);
        $("<div/>")
        .width(el.outerWidth())
        .height(el.outerHeight())
        .css({
            "position": "absolute",
            "left": el.offset().left,
            "top": el.offset().top,
            "background-color": "red",
            "opacity": ".7",
            "z-index": "9999999"
        }).appendTo('body').fadeOut(3000).queue(function () { $(this).remove(); });
    });
};
