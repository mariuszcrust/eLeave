'use strict'

angular.module('eLeave.about', ['eLeave.about.controllers']);

angular.module('eLeave.about').config(['$stateProvider', '$locationProvider', function ($stateProvider, $locationProvider) {
        $stateProvider.state('about', {
            url: '/about',
            templateUrl: 'modules/about/views/about.html',
            controller: 'AboutController',
            ncyBreadcrumb: {
                label: 'About'
            }
        }).state('about.mario', {
            url: '/mario',
            templateUrl: 'modules/about/views/about-mario.html',
            controller: 'MarioHomeController',
            ncyBreadcrumb: {
                label: 'Mario'
            }
        }).state('about.seba', {
            url: '/seba',
            templateUrl: 'modules/about/views/about-seba.html',
            controller: 'SebaHomeController',
            ncyBreadcrumb: {
                label: 'Seba'
            }
        });

        //$locationProvider.html5Mode(true);
    }]);


