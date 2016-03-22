'use strict'

var eLeaveApp = angular.module('eLeave',['ui.router', 'eLeave.home', 'eLeave.admin', 'eLeave.about', 'eLeave.controllers', 'eLeave.directives', 'eLeave.filters', 'eLeave.services']);

eLeaveApp.config(['$urlRouterProvider', function($urlRouterProvider){
        $urlRouterProvider.otherwise('/home');
        $urlRouterProvider.deferIntercept();
}]);

eLeaveApp.run(['$state', '$urlRouter',function($state, $urlRouter){
      $state.transitionTo('home');
      $urlRouter.listen();
}]);



