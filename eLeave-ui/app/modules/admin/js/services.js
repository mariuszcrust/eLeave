'use strict'

angular.module('eleave.admin.services', []).factory('adminLeaveTypesService', ['GET_LEAVE_TYPES_ENDPOINT', '$http', function (GET_LEAVE_TYPES_ENDPOINT, $http) {
        return {
            getLeaveTypesData: function () {
                return $http.get(GET_LEAVE_TYPES_ENDPOINT);
            },
            
            checkDaysAllowed: function (value) {
                if(!(/^[0-9]+$/.test(value))) {
                    return "Days Allowed should be positive integer number e.g. 5.";
                }
            }
        };
    }]);

//angular.module('eleave.admin.services').value('GET_LEAVE_TYPES_ENDPOINT', 'http://localhost:8084/eLeave/leaveTypes');

angular.module('eleave.admin.services').value('GET_LEAVE_TYPES_ENDPOINT', '/eLeave/leaveTypes');