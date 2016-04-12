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
                controllerAs: 'vm',
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
            }).result.then(function (entity) {
                adminLeaveTypesService.addLeaveType(entity).then(function (leaveType) {
                    $scope.gridOptions.data.push(leaveType);
                });
            });
        };

        $scope.edit = function (row) {
            $aside.open({
                templateUrl: 'modules/admin/views/admin-edit-leave-types.html',
                controller: 'EditLeaveTypeController',
                controllerAs: 'vm',
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
            }).result.then(function (entity) {
                adminLeaveTypesService.updateLeaveType(entity).then(function (leaveType) {
                    $scope.gridOptions.data.filter(function (element) {
                        return element.id === leaveType.id;
                    }).forEach(function (element) {
                        angular.extend(element, leaveType);
                    });
                });
            });
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

angular.module('eLeave.admin.controllers').controller('EditLeaveTypeController', ['$scope', '$uibModalInstance', 'row', 'dialog', 'adminLeaveTypesService', function ($scope, $uibModalInstance, row, dialog, adminLeaveTypesService) {
        var vm = this;
        vm.dialog = dialog;
        vm.leaveType = row.entity;
        vm.leaveTypeFields = [
            {
                key: 'leaveTypeName',
                type: 'input',
                templateOptions: {
                    type: 'text',
                    label: 'Leave Type Name',
                    placeholder: 'Enter Leave Type Name',
                    required: true,
                    minlength: 2
                }
            },
            {
                key: 'defaultDaysAllowed',
                type: 'input',
                templateOptions: {
                    type: 'number',
                    label: 'Days Allowed',
                    placeholder: 'Enter Days Allowed',
                    required: true,
                    min: 1,
                    max: 50
                },
                validators: {
                    daysAllowed: function ($viewValue, $modelValue) {
                        var value = $modelValue || $viewValue;
                        return adminLeaveTypesService.checkDaysAllowed(value);
                    }
                }
            },
            {
                key: 'comment',
                type: 'textarea',
                templateOptions: {
                    label: 'Comment',
                    placeholder: 'Enter Comment',
                    required: false
                }
            }
        ];

        vm.apply = function () {
            $uibModalInstance.close(vm.leaveType);
        };

        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }]);

angular.module('eLeave.admin.controllers').controller('EmployeesController', ['$scope', '$state', '$uibModal', '$aside', 'EmployeesService', function ($scope, $state, $uibModal, $aside, EmployeesService) {
        $scope.getAll = function () {
            EmployeesService.getAll().then(function (response, status) {
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
                        message: 'Do you really want to delete this employee ?',
                        item: row.entity.employee.firstName
                    }
                }
            }).result.then(function (row) {
                EmployeesService.delete(row.entity.employee.id).then(function (response, status) {
                    var index = $scope.gridOptions.data.indexOf(row.entity);
                    if (index !== -1) {
                        $scope.gridOptions.data.splice(index, 1);
                    }
                }, function () {
                    console.log("Cannot delete.");
                });
            });
        };

        $scope.add = function () {
            $aside.open({
                templateUrl: 'modules/admin/views/admin-edit-employees.html',
                controller: 'EditEmployeesController',
                controllerAs: 'vm',
                placement: 'left',
                size: 'lg',
                resolve: {
                    row: function () {
                        return {};
                    },
                    dialog: {
                        title: 'Add New Employee'
                    }
                }
            }).result.then(function (entity) {
                EmployeesService.add(entity).then(function (employee) {
                    $scope.gridOptions.data.push(employee);
                });
            });
        };

        $scope.gridOptions = EmployeesService.getColumnsDefs();
        $scope.getAll();
    }]);

angular.module('eLeave.admin.controllers').controller('EditEmployeesController', ['$scope', '$uibModalInstance', 'row', 'dialog', 'EmployeesService', 'adminLeaveTypesService', function ($scope, $uibModalInstance, row, dialog, EmployeesService, adminLeaveTypesService) {
        var vm = this;
        vm.dialog = dialog;
        vm.employee = row.employee;
        vm.approversForDropDown = [];
        vm.leaveTypesForDropDown = [];

        function getApprovers() {
            return EmployeesService.getAllWithRole().then(function (response, status) {

                for (var i in response.data) {
                    vm.approversForDropDown.push({
                        name: response.data[i].firstName + ' ' + response.data[i].lastName,
                        value: response.data[i].id
                    });
                }

                return vm.approversForDropDown;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };

        function getLeaveTypes() {
            adminLeaveTypesService.getLeaveTypesData().then(function (data) {
                for (var i in data) {
                    vm.leaveTypesForDropDown.push({
                        name: response.data[i].leaveTypeName,
                        value: response.data[i].id
                    });
                }
                return vm.leaveTypesForDropDown;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };

        getApprovers();
        getLeaveTypes();

        vm.employeeFields = [
            {
                key: 'firstName',
                type: 'input',
                templateOptions: {
                    type: 'text',
                    label: 'Employee Name',
                    placeholder: 'Enter Employee First Name',
                    required: true,
                    minlength: 2
                }
            },
            {
                key: 'lastName',
                type: 'input',
                templateOptions: {
                    type: 'text',
                    label: 'Employee Last Name',
                    placeholder: 'Enter Employee Last Name',
                    required: true,
                    min: 1,
                    max: 50
                }
            },
            {
                key: 'email',
                type: 'input',
                templateOptions: {
                    type: 'text',
                    label: 'Employee Email',
                    placeholder: 'Enter Email',
                    required: true,
                    min: 1,
                    max: 50
                }
            },
            {
                key: 'approverId',
                type: 'select',
                templateOptions: {
                    label: 'Approver',
                    options: vm.approversForDropDown
                }
            }
        ];

        vm.apply = function () {
            $uibModalInstance.close(vm.employee);
        };

        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
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
                }, function () {
                    console.log("Cannot delete.");
                });
            });
        };

        $scope.gridOptions = holidaysService.getColumnsDefs();
        $scope.getAll();
    }]);

angular.module('eLeave.admin.controllers').controller('AdminController', ['$scope', '$state', function ($scope, $state) {

    }]);