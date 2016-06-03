(function () {
    'use strict';

    angular.module('eLeave.home', ['eLeave.home.controllers', 'eLeave.home.services']);

    angular.module('eLeave.home.controllers', []);
    angular.module('eLeave.home.services', []);

    angular.module('eLeave.home').run(['formlyConfig', function (formlyConfig) {
            formlyConfig.extras.removeChromeAutoComplete = true;

            formlyConfig.setType({
                name: 'ui-select-single',
                extends: 'select',
                templateUrl: 'ui-select-single.html'
            });
            var attributes = [
                'date-disabled',
                'custom-class',
                'show-weeks',
                'starting-day',
                'init-date',
                'min-mode',
                'max-mode',
                'format-day',
                'format-month',
                'format-year',
                'format-day-header',
                'format-day-title',
                'format-month-title',
                'year-range',
                'shortcut-propagation',
                'datepicker-popup',
                'show-button-bar',
                'current-text',
                'clear-text',
                'close-text',
                'close-on-date-selection',
                'datepicker-append-to-body'
            ];

            var bindings = [
                'datepicker-mode',
                'min-date',
                'max-date'
            ];

            var ngModelAttrs = {};

            angular.forEach(attributes, function (attr) {
                ngModelAttrs[camelize(attr)] = {attribute: attr};
            });

            angular.forEach(bindings, function (binding) {
                ngModelAttrs[camelize(binding)] = {bound: binding};
            });

            console.log(ngModelAttrs);
            formlyConfig.setType({
                name: 'datepicker',
                templateUrl: 'datepicker.html',
                wrapper: ['bootstrapLabel', 'bootstrapHasError'],
                defaultOptions: {
                    ngModelAttrs: ngModelAttrs,
                    templateOptions: {
                        datepickerOptions: {
                            format: 'MM.dd.yyyy',
                            initDate: new Date()
                        }
                    }
                },
                controller: ['$scope', function ($scope) {
                        $scope.datepicker = {};

                        $scope.datepicker.opened = false;

                        $scope.datepicker.open = function ($event) {
                            $scope.datepicker.opened = !$scope.datepicker.opened;
                        };
                    }]
            });

            function camelize(string) {
                string = string.replace(/[\-_\s]+(.)?/g, function (match, chr) {
                    return chr ? chr.toUpperCase() : '';
                });
                // Ensure 1st char is always lowercase
                return string.replace(/^([A-Z])/, function (match, chr) {
                    return chr ? chr.toLowerCase() : '';
                });
            }

        }]);
}());


