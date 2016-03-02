'use strict'

angular.module('eLeave.leaves.controllers',[]).controller('LeaveTypesController',['$scope', '$location', '$state', function($scope, $location, $state){
	$scope.loadLeaveBalances = function() {
            $state.go('leaveBalances', {
                employeeId: 1
            });
        }    
}]).controller('LeaveBalancesController',['$scope', '$stateParams', function($scope, $stateParams){

    $scope.employeeId=$stateParams.employeeId;
    $scope.closePost=function(){
        $state.go('leaveTypes');
    };

}]);