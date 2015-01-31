var $       = require('minium'),
    browser = $.browser;

browser.get('https://docs.google.com/spreadsheet/ccc?key=0Al0ulrJIDCUVdHJjWnJsbG5hY3hBWFp0Vy1OQV9qQUE#gid=0');

var colC   = $('#0-grid-table-quadrantcolumn-head-section th').withText('C');
var row5   = $('.row-header-wrapper').withText('5');
var cellC5 = $('#0-grid-table-quadrantscrollable td').below(colC).rightOf(row5);

cellC5.doubleClick();

var cellInput = $('.cell-input').overlaps(cellC5);

cellInput.fill("Minium can!");

var browserCall = cellInput.apply(function (msg) {
  return msg + $(this).text();
}, [ "Minium can, and here is the proof: " ]);

cellInput.fill(browserCall);