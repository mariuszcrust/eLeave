/**
 * 
 */
'use strict';
 
App.factory('EmployeeService', ['$http', '$q', function($http, $q){
           return { getAllEmployee : function() {
                    return $http.get('http://localhost:8080/eLeave/employee')
                            .then(
                                    function(response){
                                    	console.log(response.data);
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while geting employee');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
           };     
 
}]);