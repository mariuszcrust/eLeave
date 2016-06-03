(function () {
    'use strict';

    angular.module('eLeave', [
        'ui.router',
        'ui.bootstrap',
        'ngAside',
        'formly',
        'formlyBootstrap',
        'ui.grid',
        'ui.grid.edit',
        'ncy-angular-breadcrumb',
        'eLeave.home',
        'eLeave.admin',
        'eLeave.about',
        'eLeave.controllers',
        'eLeave.directives',
        'eLeave.filters',
        'eLeave.services'
    ]);

    angular.module('eLeave').config(['$urlRouterProvider', function ($urlRouterProvider) {
            $urlRouterProvider.otherwise('/home');
            $urlRouterProvider.deferIntercept();
        }]);

    angular.module('eLeave').run(['$state', '$urlRouter', function ($state, $urlRouter) {
            $state.transitionTo('home');
            $urlRouter.listen();
        }]);

}());



