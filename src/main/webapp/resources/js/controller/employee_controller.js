/**
 * 
 */
'use strict';
 
App.controller('EmployeeController', ['$scope', 'EmployeeService', function($scope, EmployeeService) {
          var self = this;
          self.employee={id:null,firstName:'',lastName:'',email:''};
          self.employees=[];
               
          self.getAllEmployee = function(){
              EmployeeService.getAllEmployee()
                  .then(
                               function(d) {
                                    self.employees = d;
                               },
                                function(errResponse){
                                    console.error('Error while getting employees');
                                }
                       );
          };
          
          self.getAllEmployee();
}]);