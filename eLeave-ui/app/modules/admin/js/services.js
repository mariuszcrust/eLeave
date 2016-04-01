'use strict'

angular.module('eleave.admin.services', []).factory('adminLeaveTypesService', ['LEAVE_TYPES_ENDPOINT', '$http', function (LEAVE_TYPES_ENDPOINT, $http) {
        return {
            getLeaveTypesData: function () {
                return $http.get(LEAVE_TYPES_ENDPOINT).then(function(response){
                    return response.data;
                });
            },
            removeLeaveType: function (id) {
                return $http.delete(LEAVE_TYPES_ENDPOINT + id);
            },
            checkDaysAllowed: function (value) {
                if (!(/^[0-9]+$/.test(value))) {
                    return "Days Allowed should be a counting number e.g. 5.";
                    
                }
            }
        };
    }]);

//angular.module('eleave.admin.services').value('LEAVE_TYPES_ENDPOINT', 'http://localhost:8084/eLeave/leaveTypes');
angular.module('eleave.admin.services').value('LEAVE_TYPES_ENDPOINT', '/eLeave/leaveTypes/');

angular.module('eleave.admin.services').factory('EmployeesService', ['EMPLOYEES_ENDPOINT', '$http', function (EMPLOYEES_ENDPOINT, $http) {
        return {
            getAll: function () {
              return $http.get(EMPLOYEES_ENDPOINT);  
            },
            
            update: function(employee, id) {
                return $http.put(EMPLOYEES_ENDPOINT + id, employee);
            },
            
            delete: function(id) {
                return $http.delete(EMPLOYEES_ENDPOINT + id);
            },
            
            create: function(employee) {
                return $http.create(EMPLOYEES_ENDPOINT, employee);
            }
        };
    }]);
angular.module('eleave.admin.services').value('EMPLOYEES_ENDPOINT', 'http://localhost:8084/eLeave/employees/');
//angular.module('eleave.admin.services').value('GET_EMPLOYEES_ENDPOINT', '/eLeave/employees');