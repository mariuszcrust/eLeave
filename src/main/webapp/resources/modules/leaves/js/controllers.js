'use strict'

angular.module('eLeave.leaves.controllers',[]).controller('LeaveBalancesController',['$scope', '$state', function($scope, $state){

    $scope.closeBalances=function(){
        $state.go('leaveTypes');
    };

}]).controller('LeaveTypesController',['$scope', '$state', function($scope, $state){

            
}]);