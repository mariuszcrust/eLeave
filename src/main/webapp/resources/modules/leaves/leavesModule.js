'use strict'

angular.module('eLeave.leaves', ['eLeave.leaves.controllers']);

angular.module('eLeave.leaves').config(['$stateProvider', '$locationProvider', function ($stateProvider, $locationProvider) {
        $stateProvider.state('about', {
            url: '/about',
            templateUrl: 'resources/modules/leaves/views/about.html',
            controller: 'AboutController'
        });
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'resources/modules/leaves/views/home.html',
            controller: 'HomeController'
        });

        $locationProvider.html5Mode(true);
    }]);