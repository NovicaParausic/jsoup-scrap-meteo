(function() {
	'use strict';
	
	myApp.controller('TranslateController', [
						'$scope', '$http', '$stateParams', '$timeout', 'dataStoreService', 'weatherService',
						function($scope, $http, $stateParams, $timeout, dataStoreService, weatherService) {
		console.log('hola translate controller');
		
		var vm = this;
		
		var code = dataStoreService.loadCode();
		console.log(code);
		
		$scope.metar = '';
		$scope.humidity = 0;
		$scope.winds = [];
		$scope.weathers = [];
		
		var timeout = '';
		
		function getTranslate(code){
			$http({
				method: 'POST',
				url: '/api/active/metarFromCode/' + code
			})
			.then( function(response) {
				console.log(response.data);
				$scope.metar = response.data;
				
				$scope.humidity = weatherService.calculateHumidity($scope.metar.temperature, $scope.metar.dewPoint);
				$scope.winds = weatherService.parseWind($scope.metar.wind);
				$scope.weathers = weatherService.parseWeather($scope.metar.weatherConditions);
				
				repeatGet();
			})
			.catch( function(response) {
				console.log(response.status);
			})
		}
		
		getTranslate(code);
					
		function repeatGet() {
			console.log(new Date());
			timeout = $timeout(getTranslate, 60000);
		}
				
		$scope.$on('$destroy', function() {
		    $timeout.cancel(timeout);
		});
	}]);
})();