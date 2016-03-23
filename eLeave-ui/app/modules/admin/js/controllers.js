'use strict'

angular.module('eLeave.admin.controllers', []).controller('AdminLeaveTypesController', ['$scope', '$state', 'adminLeaveTypesService', function ($scope, $state, adminLeaveTypesService) {
        $scope.getAllLeaveTypes = function () {
            adminLeaveTypesService.getLeaveTypesData().then(function (response, status) {
                $scope.leaveTypes = response.data;
            }, function () {
                console.log("Cannot retrieve data.");
                $scope.leaveTypes = {};
            });
        };
        
        $scope.getAllLeaveTypes();
        
    }]);