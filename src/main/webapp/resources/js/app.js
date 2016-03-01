/**
 * 
 */
'use strict';

var App = angular.module('myApp',[]);

App.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'index.html',
		controller : 'index'
	}).when('/login', {
		templateUrl : 'resources/login.html',
		controller : 'navigation'
	}).otherwise('/');
}).controller('home', function($scope, $http) {
	$http.get('/resource/').success(function(data) {
		$scope.greeting = data;
	})
}).controller('navigation', function() {
});