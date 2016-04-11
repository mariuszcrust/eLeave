'use strict'

angular.module('eleave.admin.services', []).factory('adminLeaveTypesService', ['ELEAVE_BASE', 'LEAVE_TYPES_ENDPOINT', '$http', function (ELEAVE_BASE, LEAVE_TYPES_ENDPOINT, $http) {
        return {
            getLeaveTypesData: function () {
                return $http.get(LEAVE_TYPES_ENDPOINT).then(function (response) {
                    return response.data;
                });
            },
            addLeaveType: function (leaveType) {
                return $http.post(LEAVE_TYPES_ENDPOINT, leaveType).then(function (response) {
                    return $http.get(ELEAVE_BASE + response.headers(['location'])).then(function(response){
                        return response.data;
                    });
                });
            },
            updateLeaveType: function(leaveType) {
                return $http.put(LEAVE_TYPES_ENDPOINT, leaveType).then(function (response) {
                    return response.data;
                });
            },
            removeLeaveType: function (id) {
                return $http.delete(LEAVE_TYPES_ENDPOINT + id);
            },
            checkDaysAllowed: function (value) {
                return (/^[0-9]+$/.test(value));             
            },
            getColumnsDefs: function () {
                return {
                    enableSorting: false,
                    enableColumnMenus : false,
                    enableHorizontalScrollbar: 0,
                    enableVerticalScrollbar: 0,
                    columnDefs: [
                        {name: 'name', field: 'leaveTypeName'},
                        {name: 'daysAllowed', field: 'defaultDaysAllowed'},
                        {name: 'comment',  field: 'comment'},
                        {name: '', field: 'id' , cellTemplate: 'modules/admin/views/partials/action-buttons.html', cellClass: 'action-buttons'}
                    ]
                };
            }
        };
    }]);

//angular.module('eleave.admin.services').value('LEAVE_TYPES_ENDPOINT', 'http://localhost:8084/eLeave/leaveTypes');
angular.module('eleave.admin.services').value('LEAVE_TYPES_ENDPOINT', '/eLeave/leaveTypes/');
angular.module('eleave.admin.services').value('ELEAVE_BASE', '/eLeave');
angular.module('eleave.admin.services').factory('EmployeesService', ['ELEAVE_BASE', 'EMPLOYEES_ENDPOINT', '$http', function (ELEAVE_BASE, EMPLOYEES_ENDPOINT, $http) {
        return {
            getAll: function () {
              return $http.get(EMPLOYEES_ENDPOINT + "?onlyActive=true");  
            },
            update: function (employee, id) {
                return $http.put(EMPLOYEES_ENDPOINT + id, employee);
            },
            delete: function (id) {
                return $http.delete(EMPLOYEES_ENDPOINT + id);
            },
            getAllWithRole: function() {
                return $http.get('http://localhost:8084/eLeave/employees/roles?role=APPROVER');
            },
            add: function (leaveType) {
                return $http.post(EMPLOYEES_ENDPOINT, leaveType).then(function (response) {
                    return $http.get(ELEAVE_BASE + response.headers(['location'])).then(function(response){
                        return response.data;
                    });
                });
            },
            getColumnsDefs: function () {
                return {
                    enableSorting: false,
                    enableColumnMenus : false,
                    enableHorizontalScrollbar: 0,
                    enableVerticalScrollbar: 0,
                    columnDefs: [
                        {name: 'First name', field: 'employee.firstName'},
                        {name: 'Last name', field: 'employee.lastName'},
                        {name: 'email',  field: 'employee.email'},
                        {name: '', field: 'id' , cellTemplate: 'modules/admin/views/partials/action-buttons.html', cellClass: 'action-buttons'}
                    ]
                };
            }
        };
    }]);
angular.module('eleave.admin.services').value('EMPLOYEES_ENDPOINT', 'http://localhost:8084/eLeave/employees/');
//angular.module('eleave.admin.services').value('GET_EMPLOYEES_ENDPOINT', '/eLeave/employees');

angular.module('eleave.admin.services').factory('HolidaysService', ['HOLIDAYS_ENDPOINT', '$http', function (HOLIDAYS_ENDPOINT, $http) {
        return {
            getAll: function () {
                return $http.get(HOLIDAYS_ENDPOINT);
            },
            delete: function (id) {
                return $http.delete(HOLIDAYS_ENDPOINT + id);
            },
            getColumnsDefs: function () {
                return {
                    enableSorting: false,
                    enableColumnMenus : false,
                    enableHorizontalScrollbar: 0,
                    enableVerticalScrollbar: 0,
                    columnDefs: [
                        {name: 'name', field: 'name'},
                        {name: 'comment', field: 'comment'},
                        {name: 'date', field: 'date'},
                        {name: 'year',  field: 'year'},
                        {name: '', field: 'id' , cellTemplate: 'modules/admin/views/partials/action-buttons.html', cellClass: 'action-buttons'}
                    ]
                };
            }
        };
}]);

angular.module('eleave.admin.services').value('HOLIDAYS_ENDPOINT', 'http://localhost:8084/eLeave/holidays/');
//angular.module('eleave.admin.services').value('HOLIDAYS_ENDPOINT', '/eLeave/holidays');