(function () {
    'use strict';

    angular
            .module('eleave.admin.services')
            .value('ELEAVE_BASE', '/eLeave')
//          .value('ELEAVE_BASE', 'http://localhost:8084/eLeave')    
            .value('LEAVE_TYPES_ENDPOINT', '/eLeave/leaveTypes/')
//          .value('LEAVE_TYPES_ENDPOINT', 'http://localhost:8084/eLeave/leaveTypes')
            .factory('leaveTypesService', leaveTypesService)
            .config(function (errorHandlerProvider, $provide) {
                errorHandlerProvider.decorate($provide, ['leaveTypesService']);
            });

    leaveTypesService.$inject = ['ELEAVE_BASE', 'LEAVE_TYPES_ENDPOINT', '$http'];

    function leaveTypesService(ELEAVE_BASE, LEAVE_TYPES_ENDPOINT, $http) {
        var leaveTypeService = {
            getLeaveTypesData: getLeaveTypesData,
            addLeaveType: addLeaveType,
            updateLeaveType: updateLeaveType,
            removeLeaveType: removeLeaveType,
            checkDaysAllowed: checkDaysAllowed
        };

        return leaveTypeService;
        //////////////////////////////////////////

        function getLeaveTypesData() {
            return $http.get(LEAVE_TYPES_ENDPOINT).then(function (response) {
                return response.data;
            });
        }

        function addLeaveType(leaveType) {
            return $http.post(LEAVE_TYPES_ENDPOINT, leaveType).then(function (response) {
                return $http.get(ELEAVE_BASE + response.headers(['location'])).then(function (response) {
                    return response.data;
                });
            });
        }

        function updateLeaveType(leaveType) {
            return $http.put(LEAVE_TYPES_ENDPOINT, leaveType).then(function (response) {
                return response.data;
            });
        }

        function removeLeaveType(id) {
            return $http.delete(LEAVE_TYPES_ENDPOINT + id);
        }

        function checkDaysAllowed(value) {
            return (/^[0-9]+$/.test(value));
        }
    }
}());


