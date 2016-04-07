'use strict'

angular.module('eLeave.admin.controllers', []).controller('AdminLeaveTypesController', ['$scope', '$state', '$uibModal', 'adminLeaveTypesService', function ($scope, $state, $uibModal, adminLeaveTypesService) {
        
        $scope.getAllLeaveTypes = function () {
            adminLeaveTypesService.getLeaveTypesData().then(function (data) {
                $scope.gridOptions.data = data;
            });
        };

        $scope.remove = function (row) {
            $uibModal.open({
                templateUrl: 'modules/admin/views/partials/remove-confirmation.html',
                controller: 'ModalDeleteLeaveTypesController',
                resolve: {
                    row: function () {
                        return row;
                    },
                    dialog: {
                        message: 'Do you really want to delete this leave type?',
                        item: row.entity.leaveTypeName
                    }
                }
            }).result.then(function (row) {
                adminLeaveTypesService.removeLeaveType(row.entity.id).then(function () {
                    var index = $scope.gridOptions.data.indexOf(row.entity);
                    if (index !== -1) {
                        $scope.gridOptions.data.splice(index, 1);
                    }
                });
            });
        };

        $scope.add = function () {
            $uibModal.open({
                templateUrl: 'modules/admin/views/partials/edit-modal-sidebar.html',
                controller: 'RemoveConfirmationController'
            });
        };


        $scope.checkDaysAllowed = function (value) {
            return adminLeaveTypesService.checkDaysAllowed(value);
        };
        
        $scope.gridOptions = adminLeaveTypesService.getColumnsDefs(); 
        $scope.getAllLeaveTypes();
    }]);

angular.module('eLeave.admin.controllers').controller('RemoveConfirmationController', ['$scope', '$uibModalInstance', 'row', 'dialog', function ($scope, $uibModalInstance, row, dialog) {
        $scope.row = row;
        $scope.dialog = dialog;
        
        $scope.yes = function () {
            $uibModalInstance.close(row);
        };

        $scope.no = function () {
            $uibModalInstance.dismiss('cancel');
        };
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