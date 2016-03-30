'use strict'

angular.module('eLeave.admin', ['eLeave.admin.controllers', 'eleave.admin.services']);

angular.module('eLeave.admin').config(['$stateProvider', '$locationProvider', function ($stateProvider, $locationProvider) {
       $stateProvider.state('leaveTypes', {
            url: '/admin/leaveTypes',
            templateUrl: 'modules/admin/views/leaveTypes.html',
            controller: 'AdminLeaveTypesController'
        }).state('employees', {
            url: '/admin/employees',
            templateUrl: 'modules/admin/views/employees.html',
            controller: 'EmployeesController'
        });
    }]);