'use strict'

angular.module('eLeave.admin.controllers', []).controller('AdminLeaveTypesController', ['$scope', '$state', 'adminLeaveTypesService', function ($scope, $state, adminLeaveTypesService) {
        $scope.getAllLeaveTypes=function() {
            return adminLeaveTypesService.getAll();
        };
        
        $scope.leaveTypes=$scope.getAllLeaveTypes();
    }]);