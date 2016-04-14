(function () {
    'use strict';

    angular
            .module('eLeave.admin.controllers')
            .controller('LeaveTypesController', LeaveTypesController);

    LeaveTypesController.$inject = ['$uibModal', '$aside', 'leaveTypesService'];

    function LeaveTypesController($uibModal, $aside, leaveTypesService) {
        var gridOptions = {
            appScopeProvider: this,
            enableSorting: false,
            enableColumnMenus: false,
            enableHorizontalScrollbar: 0,
            enableVerticalScrollbar: 0,
            columnDefs: [
                {name: 'name', field: 'leaveTypeName'},
                {name: 'daysAllowed', field: 'defaultDaysAllowed'},
                {name: 'comment', field: 'comment'},
                {name: '', field: 'id', cellTemplate: 'modules/admin/views/partials/action-buttons.html', cellClass: 'action-buttons'}
            ]
        };

        // exports
        angular.extend(this, {
            gridOptions: gridOptions,
            add: add,
            edit: edit,
            remove: remove
        });
        
        getAllLeaveTypes();

        function remove(row) {
            $uibModal.open({
                templateUrl: 'modules/admin/views/partials/remove-confirmation.html',
                controller: 'RemoveConfirmationController',
                controllerAs: 'removeConfirmationController',
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
                leaveTypesService.removeLeaveType(row.entity.id).then(function () {
                    var index = gridOptions.data.indexOf(row.entity);
                    if (index !== -1) {
                        gridOptions.data.splice(index, 1);
                    }
                });
            });
        }

        function add() {
            $aside.open({
                templateUrl: 'modules/admin/views/admin-edit-leave-type.html',
                controller: 'EditLeaveTypeController',
                controllerAs: 'editLeaveTypeController',
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
                leaveTypesService.addLeaveType(entity).then(function (leaveType) {
                    gridOptions.data.push(leaveType);
                });
            });
        }

        function edit(row) {
            $aside.open({
                templateUrl: 'modules/admin/views/admin-edit-leave-type.html',
                controller: 'EditLeaveTypeController',
                controllerAs: 'editLeaveTypeController',
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
                leaveTypesService.updateLeaveType(entity).then(function (leaveType) {
                    gridOptions.data.filter(function (element) {
                        return element.id === leaveType.id;
                    }).forEach(function (element) {
                        angular.extend(element, leaveType);
                    });
                });
            });
        }

        function getAllLeaveTypes() {
            leaveTypesService.getLeaveTypesData().then(function (data) {
                gridOptions.data = data;
            });
        }
    }

}());