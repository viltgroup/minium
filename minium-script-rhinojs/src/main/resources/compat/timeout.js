// from https://gist.github.com/nbeloglazov/9633318
var setTimeout, clearTimeout, setInterval, clearInterval;
 
(function () {
    var executor = new java.util.concurrent.Executors.newScheduledThreadPool(1);
    var counter = 1;
    var ids = {};
 
    setTimeout = function (fn,delay) {
        var id = counter++;
        var runnable = new JavaAdapter(java.lang.Runnable, {run: fn});
        ids[id] = executor.schedule(runnable, delay, 
            java.util.concurrent.TimeUnit.MILLISECONDS);
        return id;
    }
 
    clearTimeout = function (id) {
        ids[id].cancel(false);
        executor.purge();
        delete ids[id];
    }
 
    setInterval = function (fn,delay) {
        var id = counter++;
        var runnable = new JavaAdapter(java.lang.Runnable, {run: fn});
        ids[id] = executor.scheduleAtFixedRate(runnable, delay, delay, 
            java.util.concurrent.TimeUnit.MILLISECONDS);
        return id;
    }
 
    clearInterval = clearTimeout;
 
})();