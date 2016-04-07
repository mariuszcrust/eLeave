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

angular.module('eLeave.admin.controllers').controller('EmployeesController', ['$scope', '$state', 'EmployeesService', function ($scope, $state, employeesService) {
        $scope.getAllActive = function () {
            employeesService.getAllActive().then(function (response, status) {
                $scope.gridOptions.data = response.data;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };

        $scope.remove = function (row) {
            employeesService.delete(row.entity.id).then(function () {
                var index = $scope.gridOptions.data.indexOf(row.entity);
                if (index !== -1) {
                    $scope.gridOptions.data.splice(index, 1);
                }
            });

        };

        $scope.gridOptions = employeesService.getColumnsDefs();
        $scope.getAllActive();
    }]);

angular.module('eLeave.admin.controllers').controller('HolidaysController', ['$scope', '$state', 'HolidaysService', function ($scope, $state, holidaysService) {
        $scope.getAll = function () {
            holidaysService.getAllActive().then(function (response, status) {
                self.gridOptions.data = response.data;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };

        $scope.remove = function (row) {
            holidaysService.delete(row.entity.id).then(function (response, status) {
                var index = $scope.gridOptions.data.indexOf(row.entity);
                if (index !== -1) {
                    $scope.gridOptions.data.splice(index, 1);
                }
            }, function () {
                console.log("Cannot retrieve data.");
            });

        };

        $scope.gridOptions = holidaysService.getColumnsDefs();
        $scope.getAllActive();
    }]);

angular.module('eLeave.admin.controllers').controller('AdminController', ['$scope', '$state', function ($scope, $state) {

    }]);