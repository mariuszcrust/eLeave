'use strict'

angular.module('eLeave.admin.controllers', []).controller('AdminLeaveTypesController', ['$scope', '$state', 'adminLeaveTypesService', function ($scope, $state, adminLeaveTypesService) {
        
        $scope.getAllLeaveTypes = function () {
            adminLeaveTypesService.getLeaveTypesData().then(function (data) {
                $scope.gridOptions.data = data;
            });
        };

        $scope.remove = function (row) {
            adminLeaveTypesService.removeLeaveType(row.entity.id).then(function () {
                var index = $scope.gridOptions.data.indexOf(row.entity);
                if (index !== -1) {
                    $scope.gridOptions.data.splice(index, 1);
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
        
        $scope.gridOptions = adminLeaveTypesService.getColumnsDefs(); 
        $scope.getAllLeaveTypes();
    }]);

angular.module('eLeave.admin.controllers').controller('EmployeesController', ['$scope', '$state', 'EmployeesService', function ($scope, $state, EmployeesService) {
        var self = this;
        self.employee = {id: null, firstName: '', lastName: '', email: ''};
        self.employees = [];

        self.getAllActiveEmployees = function () {
            EmployeesService.getAllActive().then(function (response, status) {
                self.employees = response.data;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };

        self.update = function (employee, id) {
            EmployeesService.update(employee, id).then(function (response, status) {
                return response.data;
            }, function () {
                console.log("Exception occured during update");
            });
        };

        self.delete = function (id) {
            EmployeesService.delete(id).then(function (response, status) {
                self.getAllActiveEmployees();
            }, function () {
                console.log("Exception occured during delete");
            });
        };

        self.create = function (employee) {
            EmployeesService.create(employee).then(function (response, status) {
                return response.data;
            }, function () {
                console.log("Exception occured during create");
            });
        };

        self.getAllActiveEmployees();

    }]);

angular.module('eLeave.admin.controllers').controller('AdminController', ['$scope', '$state', function ($scope, $state) {

    }]);