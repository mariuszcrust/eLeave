'use strict'

angular.module('eLeave.admin.controllers', []).controller('AdminLeaveTypesController', ['$scope', '$state', 'adminLeaveTypesService', function ($scope, $state, adminLeaveTypesService) {
        $scope.leaveTypes = [];
        $scope.getAllLeaveTypes = function () {
            adminLeaveTypesService.getLeaveTypesData().then(function (response, status) {
                $scope.leaveTypes = response.data;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };
        
        $scope.getAllLeaveTypes();
        
        $scope.removeLevaeType = function(row) {
            var index = $scope.leaveTypes.indexOf(row);
            if(index !== -1) {
                $scope.leaveTypes.splice(index, 1);
            }
        };
        
        $scope.saveLeaveType = function(data, id) {
            console.log("Id: " + id + "Data: " + data);
        };
        
        $scope.addLeaveType = function() {
           $scope.inserted = {
               id: $scope.leaveTypes.length+1,
               leaveTypeName: '',
               defaultDaysAllowed: 0,
               comment: ''
           };
           $scope.leaveTypes.push($scope.inserted);
        };
        
        $scope.checkDaysAllowed = function(value) {
            return adminLeaveTypesService.checkDaysAllowed(value);
        };
        
    }]);