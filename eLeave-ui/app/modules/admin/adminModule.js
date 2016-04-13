'use strict'

angular.module('eLeave.admin', ['eLeave.services', 'eLeave.admin.controllers', 'eleave.admin.services']);

angular.module('eLeave.admin').config(['$stateProvider', '$locationProvider', function ($stateProvider, $locationProvider) {
        $stateProvider.state('admin', {
            url: '/admin',
            templateUrl: 'modules/admin/views/admin.html',
            controller: 'AdminController',
            ncyBreadcrumb: {
                label: 'Administration'
            }
        }).state('admin.leaveTypes', {
            url: '/leaveTypes',
            templateUrl: 'modules/admin/views/admin-leave-types.html',
            controller: 'AdminLeaveTypesController',
            ncyBreadcrumb: {
                label: 'Leave Types'
            }
        }).state('admin.employees', {
            url: '/employees',
            templateUrl: 'modules/admin/views/admin-employees.html',
            controller: 'EmployeesController',
            ncyBreadcrumb: {
                label: 'Employees'
            }
        }).state('admin.holidays', {
            url: '/admin/holidays',
            templateUrl: 'modules/admin/views/admin-holidays.html',
            controller: 'HolidaysController',
            ncyBreadcrumb: {
                label: 'Holidays'
            }
        });
    }]);