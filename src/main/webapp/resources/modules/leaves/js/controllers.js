'use strict'

angular.module('eLeave.leaves.controllers', []).controller('AboutController', ['$scope', '$state', function ($scope, $state) {

        $scope.closeAbout = function () {
            $state.go('home');
        };

    }]).controller('HomeController', ['$scope', '$state', function ($scope, $state) {


    }]).controller('MarioHomeController', ['$scope', '$state', function ($scope, $state) {
        $scope.closeAboutMario = function () {
            $state.go('about');
        };

    }]).controller('SebaHomeController', ['$scope', '$state', function ($scope, $state) {
        $scope.closeAboutSeba = function () {
            $state.go('about');
        };

    }]);