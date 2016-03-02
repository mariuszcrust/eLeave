'use strict'

angular.module('eLeave.leaves', ['eLeave.leaves.controllers']);

angular.module('eLeave.leaves').config(['$stateProvider', '$locationProvider', function ($stateProvider, $locationProvider) {
        $stateProvider.state('leaveBalances', {
            url: '/leaveBalances',
            templateUrl: 'resources/modules/leaves/views/leaveBalances.html',
            controller: 'LeaveBalancesController'
        });
        $stateProvider.state('leaveTypes', {
            url: '/leaveTypes',
            templateUrl: 'resources/modules/leaves/views/leaveTypes.html',
            controller: 'LeaveTypesController'
        });

        $locationProvider.html5Mode(true);
    }]);