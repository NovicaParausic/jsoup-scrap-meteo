(function() {
	'use strict';
	
	myApp.controller('ActiveController', ['$scope', '$http', function($scope, $http) {
		console.log('HOLA aCTIVE cONTROLLER');
		
		var vm = this;
		
		vm.start = start;
		
		$scope.activeAirports = '';
		
		function getActiveAirports() {
			$http({
				method: 'GET',
				url: 'https://www.metaweather.com/api/location/search/?query=city'//'/api/active'
			})
			.then(function (response) {
				console.log(response.status);
				if(response && response.data) {
					$scope.activeAirports = response.data;
				}
			})
			.catch(function (response) {
				console.log(response.status);
			});
		}
		
		getActiveAirports();
		
		function start() {
			console.log('holaaaaaa');
		
			var corss = 'https://cors-anywhere.herokuapp.com/';
			
			$http({
				method: 'GET',
				url: corss + 'https://www.metaweather.com/api/location/search/?query=san'
			})
			.then(function (response) {
				console.log(response);
				
			})
			.catch(function (response) {
				console.log(response.status);
			});
		}
		
	}]);
})();