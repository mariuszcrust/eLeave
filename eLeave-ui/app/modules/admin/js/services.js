'use strict'

angular.module('eleave.admin.services', []).factory('adminLeaveTypesService', ['GET_LEAVE_TYPES_ENDPOINT', '$http', function (GET_LEAVE_TYPES_ENDPOINT, $http) {
        return {
            getLeaveTypesData: function () {
                return $http.get(GET_LEAVE_TYPES_ENDPOINT);
            }
        };
    }]);

//angular.module('eleave.admin.services').value('GET_LEAVE_TYPES_ENDPOINT', 'http://localhost:8084/eLeave/leaveTypes');

angular.module('eleave.admin.services').value('GET_LEAVE_TYPES_ENDPOINT', '/eLeave/leaveTypes');