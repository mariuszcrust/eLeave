(function () {
    'use strict';

    angular.module('eLeave.home.services')
            .value('ELEAVE_BASE', '/eLeave')
//          .value('ELEAVE_BASE', 'http://localhost:8084/eLeave')    
            .value('EMPLOYEE_ANNUAL_BALANCE_LEAVES_ENDPOINT', '/annualBalanceLeaves/employee/')
//          .value('LEAVE_TYPES_ENDPOINT', 'http://localhost:8084/eLeave/leaveTypes')
            .factory('homeService', homeService);

    homeService.$inject = ['$http', 'ELEAVE_BASE', 'EMPLOYEE_ANNUAL_BALANCE_LEAVES_ENDPOINT'];

    function homeService($http, ELEAVE_BASE, EMPLOYEE_ANNUAL_BALANCE_LEAVES_ENDPOINT) {
        var homeService = {
            getEmployeeAnnualBalanceLeaves : getEmployeeAnnualBalanceLeaves
        };

        return homeService;
        //////////////////////////////////////////

        function getEmployeeAnnualBalanceLeaves(employeeId) {
            var annualBalanceLeavesUri = [
                ELEAVE_BASE,
                EMPLOYEE_ANNUAL_BALANCE_LEAVES_ENDPOINT,
                employeeId
            ].join('');
            return $http.get(annualBalanceLeavesUri).then(function (response) {
                return response.data;
            });
        }
    }

}());