'use strict'

angular.module('eLeave.directives',[]);

angular.module('eLeave.directives').directive('appVersion',['version',function(version){
	return {
		restrict: 'AE',
		link: function(scope,elem,attrs){
			elem.html(version);
		}
	}	
}]);