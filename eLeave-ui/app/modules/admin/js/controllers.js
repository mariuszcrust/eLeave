'use strict'

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
                controllerAs: 'removeConfirmationController',
                resolve: {
                    row: function () {
                        return row;
                    },
                    dialog: {
                        message: 'Do you really want to delete this employee ?',
                        item: row.entity.firstName
                    }
                }
            }).result.then(function (row) {
                EmployeesService.delete(row.entity.id).then(function (response, status) {
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

        $scope.edit = function (row) {
            $aside.open({
                templateUrl: 'modules/admin/views/admin-edit-employees.html',
                controller: 'EditEmployeesController',
                controllerAs: 'vm',
                placement: 'left',
                size: 'lg',
                resolve: {
                    row: function () {
                        return row;
                    },
                    dialog: {
                        title: 'Edit Employee'
                    }
                }
            }).result.then(function (entity) {
                EmployeesService.update(entity).then(function (employee) {
                    $scope.gridOptions.data.filter(function (element) {
                        return element.id === employee.id;
                    }).forEach(function (element) {
                        angular.extend(element, employee);
                    });
                });
            });
        };

        $scope.gridOptions = EmployeesService.getColumnsDefs();
        $scope.getAll();
    }]);

angular.module('eLeave.admin.controllers').config(function (formlyConfigProvider) {
    formlyConfigProvider.setType({
        name: 'repeatSection',
        templateUrl: 'repeatSection.html',
        controller: function ($scope) {
            $scope.formOptions = {formState: $scope.formState};
            $scope.addNew = addNew;

            $scope.copyFields = copyFields;


            function copyFields(fields) {
                fields = angular.copy(fields);
                return fields;
            }
            
            function clearValuesInFields(newsection) {
                newsection.id = null;
                newsection.leaveDaysAllowed = null;
                newsection.leaveDaysRemaining = null;
                newsection.leaveTypeId = null;
                newsection.leaveTypeName = null;
                newsection.validityDate = null;
            }

            function addNew() {
                $scope.model[$scope.options.key] = $scope.model[$scope.options.key] || [];
                var repeatsection = $scope.model[$scope.options.key];
                var lastSection = repeatsection[repeatsection.length - 1];
                var newsection = {};
                if (lastSection) {
                    newsection = angular.copy(lastSection);
                }
                
                clearValuesInFields(newsection);
                repeatsection.push(newsection);
            }

        }
    });
    

      formlyConfigProvider.setType({
        name: 'ui-select-multiple',
        extends: 'select',
        templateUrl: 'ui-select-multiple.html',
                controller: function ($scope) {
            $scope.formOptions = {formState: $scope.formState};

            $scope.copyFields = copyFields;


            function copyFields(fields) {
                fields = angular.copy(fields);
                return fields;
            }

        }
    });

});


/*
angular.module('eLeave.admin.controllers', ['ngSanitize', 'formly', 'formlyBootstrap', 'ui.select'], function config(formlyConfigProvider) {
  formlyConfigProvider.setType({
        name: 'ui-select-multiple',
        extends: 'select',
        templateUrl: 'ui-select-multiple.html'
    });
});
*/

angular.module('eLeave.admin.controllers').controller('EditEmployeesController', ['$scope', '$uibModalInstance', 'row', 'dialog', 'EmployeesService', 'leaveTypesService', 'UsersService', function ($scope, $uibModalInstance, row, dialog, EmployeesService, leaveTypesService, UsersService) {
        var vm = this;
        vm.dialog = dialog;
        
        vm.approversForDropDown = [];
        vm.leaveTypesForDropDown = [];
        vm.rolesForDropDown = [];
        
        function isEditMode() {
            return angular.isDefined(row.entity);
        }
        
        if(isEditMode()) {
            //vm.employee = row.entity.employee;
            getEmployeeDetails(row.entity.id);
        }
        
        function getEmployeeDetails(id) {
            EmployeesService.getById(id).then(function (response, status) {
                vm.employee = response.data;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        }

        function getApprovers() {
            return EmployeesService.getAllWithRole().then(function (response, status) {

                for (var i in response.data) {
                    vm.approversForDropDown.push({
                        approverName: response.data[i].firstName + ' ' + response.data[i].lastName,
                        approverId: response.data[i].id
                    });
                }

                return vm.approversForDropDown;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };

        function getLeaveTypes() {
            return leaveTypesService.getLeaveTypesData().then(function (data) {

                for (var i in data) {
                    vm.leaveTypesForDropDown.push({
                        leaveTypeName: data[i].leaveTypeName,
                        defaultDaysAllowed: data[i].defaultDaysAllowed,
                        leaveTypeId: data[i].id
                    });
                }

                return vm.leaveTypesForDropDown;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };
        
        function getUserRoles() {
            return UsersService.getAllRoles().then(function (response, status) {
                
                for (var i in response.data) {
                    vm.rolesForDropDown.push({
                        id: response.data[i].id,
                        roleName: response.data[i].name
                    });
                }

                return vm.rolesForDropDown;
            }, function () {
                console.log("Cannot retrieve data.");
            });
        };
        
        function getDefaultDaysAllowedByLeaveTypeId(leaveTypeId) {
            for (var i=0, iLen=vm.leaveTypesForDropDown.length; i<iLen; i++) {
                if (vm.leaveTypesForDropDown[i].leaveTypeId === leaveTypeId) 
                    return vm.leaveTypesForDropDown[i];
            }
        }

        getApprovers();
        getLeaveTypes();
        getUserRoles();

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
                    valueProp: 'approverId',
                    labelProp: 'approverName',
                    options: vm.approversForDropDown
                }
            },
            
            /*
             * {
        key: 'multipleOption',
        type: 'ui-select-multiple',
        templateOptions: {
          optionsAttr: 'bs-options',
          ngOptions: 'option[to.valueProp] as option in to.options | filter: $select.search',
          label: 'Multiple Select',
          valueProp: 'id',
          labelProp: 'label',
          placeholder: 'Select options',
          options: testData
        }
             */
            
            {
                key: 'roles',
                type: 'ui-select-multiple',
                templateOptions: {
                    optionsAttr: 'bs-options',
                    ngOptions: 'option[to.valueProp] as option in to.options | filter: $select.search',
                    label: 'Roles',
                    valueProp: 'id',
                    labelProp: 'roleName',
                    placeholder: 'roleName',
                    options: vm.rolesForDropDown
                }
            },
            {
                type: 'repeatSection',
                key: 'annualBalanceLeaves',
                templateOptions: {
                    btnText: 'Add another annual balance',
                    fields: [
                        {
                            className: 'row',
                            fieldGroup: [
                                {
                                    key: 'id',
                                    type: 'text',
                                    hideExpression: 'true'
                                },
                                {
                                    key: 'leaveTypeId',
                                    type: 'select',
                                    className: 'col-xs-6',
                                    templateOptions: {
                                        label: 'Leave Type',
                                        valueProp: 'leaveTypeId',
                                        labelProp: 'leaveTypeName',
                                        options: vm.leaveTypesForDropDown
                                    },
                                    expressionProperties: {
                                        'templateOptions.disabled' : function($viewValue, $modelValue, scope)  {

                                            //disables select when for existing annual leaves
                                            if(scope.model.id !== null) {
                                                 return 'true';
                                            } 
                                            
                                            if(scope.model.leaveTypeId !== null) {
                                                var defaultDays = getDefaultDaysAllowedByLeaveTypeId(scope.model.leaveTypeId);
                                                
                                                if(scope.model.leaveTypeId !== scope.model.previousLeaveTypeId) {
                                                    scope.model.leaveDaysRemaining = defaultDays.defaultDaysAllowed;
                                                }
          
                                                scope.model.previousLeaveTypeId = scope.model.leaveTypeId;
                                            }
                                        }
                                    }
                                },
                                {
                                    key: 'leaveDaysRemaining',
                                    type: 'input',
                                    className: 'col-xs-6',
                                    templateOptions: {
                                        label: 'Number of days',
                                        valueProp: 'leaveDaysRemaining',
                                        placeholder: 'Enter number of days'
                                    }
                                }
                            ]
                        }
                    ]
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
                controllerAs: 'removeConfirmationController',
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