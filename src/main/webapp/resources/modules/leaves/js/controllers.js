'use strict'

angular.module('eLeave.leaves.controllers',[]).controller('AboutController',['$scope', '$state', function($scope, $state){

    $scope.closeBalances=function(){
        $state.go('home');
    };

}]).controller('HomeController',['$scope', '$state', function($scope, $state){

            
}]);