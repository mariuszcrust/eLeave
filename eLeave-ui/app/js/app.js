'use strict'

var eLeaveApp = angular.module('eLeave',['ui.router', 'ui.bootstrap', 'smart-table', 'xeditable', 'ui.grid', 'ui.grid.edit', 'ncy-angular-breadcrumb', 'eLeave.home', 'eLeave.admin', 'eLeave.about', 'eLeave.controllers', 'eLeave.directives', 'eLeave.filters', 'eLeave.services']);

eLeaveApp.config(['$urlRouterProvider', function($urlRouterProvider){
        $urlRouterProvider.otherwise('/home');
        $urlRouterProvider.deferIntercept();
}]);

eLeaveApp.run(['$state', '$urlRouter', 'editableOptions', function($state, $urlRouter, editableOptions){
      editableOptions.theme = 'bs3';
      $state.transitionTo('home');
      $urlRouter.listen();
}]);



