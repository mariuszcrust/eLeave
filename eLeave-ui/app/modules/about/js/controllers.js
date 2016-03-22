'use strict'

angular.module('eLeave.about.controllers', []).controller('AboutController', ['$scope', '$state', function ($scope, $state) {

        $scope.closeAbout = function () {
            $state.go('home');
        };

    }]).controller('MarioHomeController', ['$scope', '$state', function ($scope, $state) {
        $scope.closeAboutMario = function () {
            $state.go('about');
        };

    }]).controller('SebaHomeController', ['$scope', '$state', function ($scope, $state) {
        $scope.closeAboutSeba = function () {
            $state.go('about');
        };

    }]);


