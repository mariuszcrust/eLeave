'use strict'

angular.module('eLeave.leaves', ['eLeave.leaves.controllers']);

angular.module('eLeave.leaves').config(['$stateProvider', '$locationProvider', '$urlRouterProvider', function ($stateProvider, $locationProvider, $urlRouterProvider) {
        $stateProvider.state('leaveTypes', {
            url: '/leaves/leaveTypes',
            templateUrl: 'eLeave/resources/modules/leaves/views/leaveTypes.html',
            controller: 'LeaveTypesController'
        }).state('leaveBalances', {
            url: '/leaves/leaveBalances/:employeeId',
            templateUrl: 'eLeave/resources/modules/leaves/views/leaveBalances.html',
            controller: 'LeaveBalancesController'
        });
        $urlRouterProvider.otherwise('/leaves/leaveTypes');
        $locationProvider.html5Mode(true);
    }]);