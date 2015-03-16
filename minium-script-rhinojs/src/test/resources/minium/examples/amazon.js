var offsets = require("minium/offsets");

browser.get("http://www.amazon.com/");

var searchBox = $("#twotabsearchtextbox");
var goBtn = $(":submit").withValue("Go");
var results = $(".s-access-title");
var addToCartBtn = $("#add-to-cart-button");
var confirmText = $("#confirm-text");
var cartCount = $("#nav-cart-count");

searchBox.fill("Hubsan X4");
goBtn.click();
results.click(offsets.at("center center-25%"));
addToCartBtn.click();

expect(confirmText).to.have.text("1 item added to Cart");
expect(cartCount).to.have.text("1");