browser.configure()
  .interactionListeners()
    .clear()
    .add(minium.interactionListeners.onStaleElementReference().thenRetry())
  .done();

browser.get("http://www.easyjet.com/en/");

var frame = $("#tabco-iframe-1").frames();
var origin = frame.find("#acOriginAirport");
var destination = frame.find("#acDestinationAirport");
var autocompletionOpts = frame.find(".acl_results li").visible();
var monthCalendars = frame.find(".ui-datepicker-calendar").visible();
var outboundDatePicker = frame.find("#oDateCalendar").unless(monthCalendars);
var mondayCol = monthCalendars.last().find("th").withText("Mo");
var fridayCol = monthCalendars.first().find("th").withText("Fr");
var calendarDays = monthCalendars.find("td a");
var outboundDay = frame.find(".datesBoxTitle").withText("1. Select outbound date").then(calendarDays.below(mondayCol).eq(2));
var returnDay = frame.find(".datesBoxTitle").withText("2. Select return date").then(calendarDays.rightOf(".ui-state-active").below(fridayCol));
var showFlightsBtn = frame.find("#searchPodSubmitButton").unless(monthCalendars);
var flights = $("li.standard:visible");

// fill origin
origin.fill("Lisbon");
autocompletionOpts.withText("Lisbon LIS, Portugal").click();

// and destination
destination.fill("London");
autocompletionOpts.withText("London Gatwick LGW, United Kingdom").click();

// pick outbound date
outboundDatePicker.click();
outboundDay.click();

// and return date
returnDay.click();

// request flights
showFlightsBtn.click();

// ensure there are results
expect(flights).not.to.be.empty();