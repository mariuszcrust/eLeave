'use strict'

angular.module('eLeave.admin.controllers', []).controller('AdminLeaveTypesController', ['$scope', '$state', 'adminLeaveTypesService', function ($scope, $state, adminLeaveTypesService) {
        $scope.leaveTypes = [];
        $scope.getAllLeaveTypes = function () {
            adminLeaveTypesService.getLeaveTypesData().then(function (response) {
                $scope.leaveTypes = response.data;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };

        $scope.getAllLeaveTypes();

        $scope.removeLevaeType = function (row) {
            adminLeaveTypesService.removeLeaveType(row.id).then(function () {
                var index = $scope.leaveTypes.indexOf(row);
                if (index !== -1) {
                    $scope.leaveTypes.splice(index, 1);
                }
            });

        };

        $scope.saveLeaveType = function (data, id) {
            console.log("Id: " + id + "Data: " + data);
        };

        $scope.addLeaveType = function () {
            $scope.inserted = {
                id: $scope.leaveTypes.length + 1,
                leaveTypeName: '',
                defaultDaysAllowed: 0,
                comment: ''
            };
            $scope.leaveTypes.push($scope.inserted);
        };

        $scope.checkDaysAllowed = function (value) {
            return adminLeaveTypesService.checkDaysAllowed(value);
        };

    }]);

angular.module('eLeave.admin.controllers', []).controller('EmployeesController', ['$scope', '$state', 'EmployeesService', function ($scope, $state, EmployeesService) {
        var self = this;
        self.employee={id:null, firstName:'', lastName:'', email:''};
        self.employees = [];
        
        self.getAllEmployees = function() {
            EmployeesService.getAll().then(function (response, status) {
                self.employees = response.data;
            }, function() {
                console.log("Cannot retrieve data.");
            });
        };
        
        self.update = function(employee, id) {
            EmployeesService.update(employee, id).then(function (response, status) {
                return response.data;
            }, function() {
                console.log("Exception occured during update");
            });
        };
        
        self.delete = function(id) {
            EmployeesService.delete(id).then(function (response, status) {
                return response.data;
            }, function() {
                console.log("Exception occured during delete");
            });
        };
        
        self.create = function(employee) {
            EmployeesService.create(employee).then(function (response, status) {
                return response.data;
            }, function() {
                console.log("Exception occured during create");
            });
        };
        
        self.getAllEmployees();
        
}]);