/**
 * Error handler implelmented based on Paco van der Linden solution.
 * Copyright (c) 2015 Paco van der Linden 
 */
(function () {
    'use strict';
    
    angular.module('eLeave.services', []);
    angular.module('eLeave.services').value('version', 'V1.0');
    angular.module('eLeave.services').constant('httpErrors', {
        0: 'The server is unreachable.',
        404: 'The requested data or service cannot be found.',
        500: 'Unknown errors occurred at the server.'
    }).provider('errorHandler', function () {

        function decorateFunction($injector, obj, func) {
            return angular.extend(function () {
                return $injector.get('errorHandler').invokeFunction(func, obj, arguments);
            }, func);
        }

        // decorate function - it is used to decorate all functions of the given service
        var decorator = ['$delegate', '$injector', function ($delegate, $injector) {
                Object.getOwnPropertyNames($delegate).filter(function (prop) {
                    return angular.isFunction($delegate[prop]);
                }).forEach(function (prop) {
                    $delegate[prop] = decorateFunction($injector, $delegate, $delegate[prop]);
                });
                return $delegate;
            }];
        // error handler provider
        return {
            // decorate services
            decorate: function ($provide, services) {
                angular.forEach(services, function (service) {
                    $provide.decorator(service, decorator);
                });
            },
            $get: function ($log, httpErrors) {
                var errorHandler = {
                    errors: [],
                    handleSync: function (err, func) {
                        if (err && !angular.isUndefined(err.status)) {
                            err = httpErrors[err.status];
                        } else if (err && err.message) {
                            err = err.message;
                        }

                        if (!angular.isString(err)) {
                            err = "An unknown error occurred.";
                        }

                        if (func && func.description) {
                            err = 'Unable to ' + func.description + '. ' + err;
                        }

                        $log.info('Caught error: ' + err);
                        errorHandler.errors.push(err);
                    },
                    handleAsync: function (func, promise) {
                        promise['catch'](function (err) {
                            errorHandler.handleSync(func, err);
                        });
                        return promise;
                    },
                    invokeFunction: function (func, obj, args) {
                        $log.debug('Invoked function: ', (func.name || func));
                        var result = {};
                        try {
                            result = func.apply(obj, args);
                        } catch (e) {
                            errorHandler.handleSync(e, func);
                            throw e;
                        }

                        // Catch asynchronous errors.
                        var promise = result && result.$promise || result;
                        if (promise && angular.isFunction(promise.then) && angular.isFunction(promise['catch'])) {
                            // promise is a genuine promise, so we call [handler.async].
                            errorHandler.handleAsync(func, promise);
                        }

                        return result;
                    }
                };
                return errorHandler;
            }

        };
    });
}());