browser.get("http://www.amazon.com/");

var searchBox = $("#twotabsearchtextbox");
var goBtn = $(":submit").withValue("Go");
var results = $(".s-access-title");
var addToCartBtn = $("#add-to-cart-button");
var confirmText = $("#confirm-text, #huc-v2-order-row-items-msg");
var cartCount = $("#nav-cart-count");


searchBox.fill("Hubsan X4");
goBtn.click();
results.click();
addToCartBtn.click();


if (confirmText.waitForExistence().is("#confirm-text")) {
	expect(confirmText).to.have.text("1 item added to Cart");
} else {
	expect(confirmText).to.have.text("Added to Cart");
}

expect(cartCount).to.have.text("1");