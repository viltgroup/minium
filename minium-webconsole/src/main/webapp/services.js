angular.module("phonecatServices", [ "ngResource" ])
  .factory("webDriverService", function($resource) {
    return $resource("webDrivers/:varName", {});
  });