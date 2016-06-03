(function () {
    'use strict';

    angular.module('eLeave.home.controllers').controller('HomeController', HomeController);

    HomeController.$inject = ['homeService'];

    function HomeController(homeService) {
        var annualBalanceLeaves = [
            {
                leaveTypeId : 1,
                leaveTypeName : 'Annual Leave'
            },
            {
                leaveTypeId : 2,
                leaveTypeName : 'Other Leave'
            }
        ];
        var leaveRequestFields = [
            {
                key: 'leaveType',
                type: 'ui-select-single',
                templateOptions: {
                    optionsAttr: 'bs-options',
                    label: 'Annual Balance Leave',
                    valueProp: 'leaveTypeId',
                    labelProp: 'leaveTypeName',
                    placeholder: 'Select Annual Balance Leave',
                    description: 'Select Annual Balance Leave wich will be charged for your leave of absence.',
                    options: annualBalanceLeaves
                }
            },
            {
                key: 'fromDate',
                type: 'datepicker',
                templateOptions: {
                    label: 'From',
                    type: 'text',
                    datepickerPopup: 'dd-MM-yyyy'
                }
            },
            {
                key: 'toDate',
                type: 'datepicker',
                templateOptions: {
                    label: 'To',
                    type: 'text',
                    datepickerPopup: 'dd-MM-yyyy'
                }
            }
        ];
        
        getEmployeeLeaves(1);
        
        // exports
        angular.extend(this, {
            leaveRequestFields: leaveRequestFields,
            annualBalanceLeaves: annualBalanceLeaves
        });
        
        function getEmployeeLeaves(employeeId) {
            homeService.getEmployeeAnnualBalanceLeaves(employeeId).then(function(data) {
                annualBalanceLeaves = data;
            });
        }
    }

}());

