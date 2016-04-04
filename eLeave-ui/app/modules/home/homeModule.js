'use strict'

angular.module('eLeave.home', ['eLeave.home.controllers']);

angular.module('eLeave.home').config(['$stateProvider', '$locationProvider', function ($stateProvider, $locationProvider) {
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'modules/home/views/home.html',
            controller: 'HomeController',
            ncyBreadcrumb: {
                label: 'Home'
            }
        });
        //$locationProvider.html5Mode(true);
    }]);


