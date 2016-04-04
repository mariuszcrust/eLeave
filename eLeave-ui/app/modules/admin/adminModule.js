'use strict'

angular.module('eLeave.admin', ['eLeave.admin.controllers', 'eleave.admin.services']);

angular.module('eLeave.admin').config(['$stateProvider', '$locationProvider', function ($stateProvider, $locationProvider) {
        $stateProvider.state('admin', {
            url: '/admin',
            templateUrl: 'modules/admin/views/admin.html',
            controller: 'AdminController',
            ncyBreadcrumb: {
                label: 'Administration'
            }
        }).state('admin.leaveTypes', {
            url: '/admin/leaveTypes',
            templateUrl: 'modules/admin/views/admin-leave-types.html',
            controller: 'AdminLeaveTypesController',
            ncyBreadcrumb: {
                label: 'Leave Types'
            }
        }).state('admin.employees', {
            url: '/admin/employees',
            templateUrl: 'modules/admin/views/admin-employees.html',
            controller: 'EmployeesController',
            ncyBreadcrumb: {
                label: 'Employees'
            }
        });
    }]);