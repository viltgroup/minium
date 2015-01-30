var $  = require('minium'),
    by = require('minium').by;

wd.get('https://docs.google.com/spreadsheet/ccc?key=0Al0ulrJIDCUVdHJjWnJsbG5hY3hBWFp0Vy1OQV9qQUE#gid=0');

var colC   = by.cssSelector('#0-grid-table-quadrantcolumn-head-section th').withText('C');
var row5   = by.cssSelector('.row-header-wrapper').withText('5');
var cellC5 = by.cssSelector('#0-grid-table-quadrantscrollable td').below(colC).rightOf(row5);

cellC5.doubleClick();

var cellInput = by.cssSelector('.cell-input').overlaps(cellC5);

function myFn() {
  return 'hello world!';
};

cellInput.fill('Minium can, and here is the proof: ' + cellInput.call(myFn));