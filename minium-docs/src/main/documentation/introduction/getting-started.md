### Setup
```shell
git clone git://github.com/viltgroup/minium.git
cd minium
mvn install -DskipTests=true
```

### Give it a try
```javascript
browser.get("http://www.google.com/ncr");

searchbox = $(":text").withName("q");
searchbox.highlight();

searchbox.fill("minion");

searchbox.sendKeys([ Keys.ENTER ]);

wikipediaResult = $("h3 a").withText("Minion - Wikipedia, the free encyclopedia");
wikipediaResult.click();

firstParagraph = $("#mw-content-text p").first();
firstParagraph.highlight();
```

