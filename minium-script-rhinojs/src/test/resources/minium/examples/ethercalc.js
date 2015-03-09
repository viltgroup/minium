var keys = require("minium/keys");

browser.configure()
  .interactionListeners()
    .clear()
    .add(minium.interactionListeners.onStaleElementReference().thenRetry())
  .done();

browser.get("https://www.ethercalc.org/");

var createBtn = $("#newpadbutton");

createBtn.click();

var cells = $("#te_fullgrid td");

// chrome:
//  cells.css("background-image") === "url(https://www.ethercalc.org/static/download.png)"),
// firefox:
//  cells.css("background-image") === "url(\"https://www.ethercalc.org/static/download.png\")")
var exportCell = cells.applyWebElements(function() {
	return $(this).filter(function () { return $(this).css("background-image").indexOf("download.png") !== -1 });
});

var colC   = cells.rightOf(exportCell).withText("C");
var row5   = cells.below(exportCell).withText('5');
var cellC5 = cells.below(colC).rightOf(row5);

cellC5.doubleClick();

var cellInput = $("input").above(exportCell).first();

cellInput.fill("Minium can!").sendKeys(keys.ENTER);

expect(cellC5).to.have.text("Minium can!");