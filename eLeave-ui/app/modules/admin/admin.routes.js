(function () {
    'use strict';
    angular.module('eLeave.admin').config(['$stateProvider', function ($stateProvider) {
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
                controller: 'LeaveTypesController',
                controllerAs: 'leaveTypesController',
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
}());


