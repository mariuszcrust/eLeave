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
        
        $scope.removeLevaeType = function(row) {
            var index = $scope.leaveTypes.indexOf(row);
            if(index !== -1) {
                $scope.leaveTypes.splice(index, 1);
            }
        };
        
    }]);