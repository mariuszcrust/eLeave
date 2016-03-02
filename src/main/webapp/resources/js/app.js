'use strict'

angular.module('eLeave',['ui.router', 'eLeave.leaves', 'eLeave.controllers', 'eLeave.directives', 'eLeave.filters', 'eLeave.services']);

angular.module('eLeave').run(['$state',function($state){
      $state.go('leaveTypes');
}]);



