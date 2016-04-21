(function () {
    'use strict';
    
    angular
            .module('eLeave.admin.controllers')
            .controller('EditLeaveTypeController', EditLeaveTypeController);

    EditLeaveTypeController.$inject = ['$uibModalInstance','row', 'dialog', 'leaveTypesService'];

    function EditLeaveTypeController($uibModalInstance, row, dialog, leaveTypesService) {
        var leaveTypeFields = [
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
                        return leaveTypesService.checkDaysAllowed(value);
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

        // exports
        angular.extend(this, {
            leaveTypeFields: leaveTypeFields,
            dialog: dialog,
            leaveType: row.entity,
            apply: apply,
            cancel: cancel
        });

        function apply(leaveType) {
            $uibModalInstance.close(leaveType);
        }

        function cancel() {
            $uibModalInstance.dismiss('cancel');
        }
    }
}());


