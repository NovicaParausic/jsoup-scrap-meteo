(function() {
	'use strict';
	
	myApp.controller('TranslateController', [
						'$scope', '$http', '$stateParams', 'dataStoreService', 'weatherService',
						function($scope, $http, $stateParams, dataStoreService, weatherService) {
		console.log('hola translate controller');
		
		var vm = this;
		
		var code = dataStoreService.loadCode();
		console.log(code);
		
		$scope.metar = '';
		$scope.humidity = 0;
		$scope.winds = [];
		$scope.weathers = [];
		
		function getTranslate(code){
			$http({
				method: 'POST',
				url: '/api/active/metarFromCode/' + code
			})
			.then( function(response) {
				console.log(response.status);
				console.log(response.data);
				$scope.metar = response.data;
				
				//calcHumidity();
				$scope.humidity = weatherService.calculateHumidity($scope.metar.temperature, $scope.metar.dewPoint);
				
				$scope.winds = weatherService.parseWind($scope.metar.wind);
				
				$scope.weathers = weatherService.parseWeather($scope.metar.weatherConditions);
			})
			.catch( function(response) {
				console.log(response.status);
			})
		}
		
		getTranslate(code);
		
		function calcHumidity(){
			$scope.humidity = weatherService.calculateHumidity($scope.metar.temperature, $scope.metar.dewPoint);
		}
				
		function refreshMetar(){
			var d = new Date();
			var h = d.getMinutes() % 5;
			
			setTimeout(repeatDate, (5 - h) * 60 * 1000);
		} 
			
		refreshMetar();
		
		function repeatDate() {
			console.log(new Date());
			getTranslate(code);
			setTimeout(repeatDate,  5 * 60 * 1000);
		}
	}]);
})();