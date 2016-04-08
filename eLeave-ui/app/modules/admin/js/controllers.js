'use strict'

angular.module('eLeave.admin.controllers', []).controller('AdminLeaveTypesController', ['$scope', '$state', '$uibModal', '$aside', 'adminLeaveTypesService', function ($scope, $state, $uibModal, $aside, adminLeaveTypesService) {

        $scope.getAllLeaveTypes = function () {
            adminLeaveTypesService.getLeaveTypesData().then(function (data) {
                $scope.gridOptions.data = data;
            });
        };

        $scope.remove = function (row) {
            $uibModal.open({
                templateUrl: 'modules/admin/views/partials/remove-confirmation.html',
                controller: 'RemoveConfirmationController',
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
            $aside.open({
                templateUrl: 'modules/admin/views/admin-edit-leave-types.html',
                controller: 'EditLeaveTypeController',
                placement: 'left',
                size: 'lg',
                resolve: {
                    row: function () {
                        return {};
                    },
                    dialog: {
                        title: 'Add New Leave Type'
                    }
                }
            });
        };

        $scope.edit = function (row) {
            $aside.open({
                templateUrl: 'modules/admin/views/admin-edit-leave-types.html',
                controller: 'EditLeaveTypeController',
                placement: 'left',
                size: 'lg',
                resolve: {
                    row: function () {
                        return row;
                    },
                    dialog: {
                        title: 'Edit Leave Type'
                    }
                }
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

angular.module('eLeave.admin.controllers').controller('EditLeaveTypeController', ['$scope', '$uibModalInstance', 'row', 'dialog', function ($scope, $uibModalInstance, row, dialog) {
        $scope.dialog = dialog;
        $scope.row = row;

        $scope.apply = function () {
            $uibModalInstance.close(row);
        };

        $scope.cancel = function () {
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
        
        $scope.remove = function (row) {
            $uibModal.open({
                templateUrl: 'modules/admin/views/partials/remove-confirmation.html',
                controller: 'RemoveConfirmationController',
                resolve: {
                    row: function () {
                        return row;
                    },
                    dialog: {
                        message: 'Do you really want to delete this employee ?',
                        item: row.entity.employee.firstName
                    }
                }
            }).result.then(function (row) {
                employeesService.delete(row.entity.employee.id).then(function (response, status) {
                    var index = $scope.gridOptions.data.indexOf(row.entity);
                    if (index !== -1) {
                        $scope.gridOptions.data.splice(index, 1);
                    }
                }, function() {
                    console.log("Cannot delete.");
                });
            });
        };
        
        /*
        $scope.add = function () {
            $uibModal.open({
                templateUrl: 'modules/admin/views/partials/edit-modal-sidebar.html',
                controller: 'RemoveConfirmationController'
            });
        };

*/
        $scope.gridOptions = employeesService.getColumnsDefs();
        $scope.getAllActive();
    }]);

angular.module('eLeave.admin.controllers').controller('HolidaysController', ['$scope', '$state', '$uibModal', 'HolidaysService', function ($scope, $state, $uibModal, holidaysService) {
        $scope.getAll = function () {
            holidaysService.getAll().then(function (response, status) {
                $scope.gridOptions.data = response.data;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };

        $scope.remove = function (row) {
            $uibModal.open({
                templateUrl: 'modules/admin/views/partials/remove-confirmation.html',
                controller: 'RemoveConfirmationController',
                resolve: {
                    row: function () {
                        return row;
                    },
                    dialog: {
                        message: 'Do you really want to delete this holiday ?',
                        item: row.entity.name
                    }
                }
            }).result.then(function (row) {
                holidaysService.delete(row.entity.id).then(function (response, status) {
                    var index = $scope.gridOptions.data.indexOf(row.entity);
                    if (index !== -1) {
                        $scope.gridOptions.data.splice(index, 1);
                    }
                }, function() {
                    console.log("Cannot delete.");
                });
            });
        };

        $scope.gridOptions = holidaysService.getColumnsDefs();
        $scope.getAll();
    }]);

angular.module('eLeave.admin.controllers').controller('AdminController', ['$scope', '$state', function ($scope, $state) {

    }]);