(function () {
    'use strict';

    angular.module('eLeave.home.controllers').controller('HomeController', HomeController);

    HomeController.$inject = ['homeService'];

    function HomeController(homeService) {
        var annualBalanceLeaves = [
            {
                leaveTypeId: 3,
                leaveTypeName: 'Annual Leave',
                leaveDaysRemaining: 20
            },
            {
                leaveTypeId: 4,
                leaveTypeName: 'Other Leave',
                leaveDaysRemaining: 12
            }
        ];

        var model = {};

        var leaveRequestFields = [
            {
                className: 'row',
                fieldGroup: [
                    {
                        className: 'col-xs-8',
                        key: 'leaveTypeId',
                        type: 'ui-select-single',
                        templateOptions: {
                            optionsAttr: 'bs-options',
                            label: 'Annual Balance Leave',
                            onChange: displayBalance,
                            valueProp: 'leaveTypeId',
                            labelProp: 'leaveTypeName',
                            placeholder: 'Select Annual Balance Leave',
                            description: 'Select Annual Balance Leave wich will be charged for your leave of absence.',
                            options: annualBalanceLeaves
                        }
                    },
                    {
                        className: 'col-xs-2',
                        key: 'leaveBalance',
                        type: 'readonly-input',
                        templateOptions: {
                            label: 'Balance'
                        }
                    }
                ]
            },
            {
                className: 'row',
                fieldGroup: [
                    {
                        className: 'col-xs-4',
                        key: 'fromDate',
                        type: 'datepicker',
                        templateOptions: {
                            label: 'From',
                            type: 'text',
                            datepickerPopup: 'dd-MM-yyyy'
                        }
                    },
                    {
                        className: 'col-xs-4',
                        key: 'toDate',
                        type: 'datepicker',
                        templateOptions: {
                            label: 'To',
                            type: 'text',
                            datepickerPopup: 'dd-MM-yyyy'
                        }
                    },
                    {
                        className: 'col-xs-2',
                        key: 'takenDays',
                        type: 'readonly-input',
                        templateOptions: {
                            label: 'Days'
                        }
                    },
                    {
                        className: 'col-xs-2',
                        type: 'button',
                        templateOptions: {
                            label: 'Calculate days',
                            text: 'Calculate',
                            btnType: 'info',
                            onClick: '',
                            description: 'Calculate leave days.'
                        } 
                    }
                ]
            }
        ];

        getEmployeeLeaves(1);

        // exports
        angular.extend(this, {
            leaveRequestFields: leaveRequestFields,
            annualBalanceLeaves: annualBalanceLeaves,
            model: model,
            displayBalance: displayBalance
        });

        function getEmployeeLeaves(employeeId) {
            homeService.getEmployeeAnnualBalanceLeaves(employeeId).then(function (data) {
                annualBalanceLeaves = data;
            });
        }

        function displayBalance() {
            var annualBalanceLeave = annualBalanceLeaves.find(function(element){
                return element.leaveTypeId === model.leaveTypeId;
            });
            model.leaveBalance = annualBalanceLeave ? annualBalanceLeave.leaveDaysRemaining : 0;
        }
    }

}());

