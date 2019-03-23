(function() {
	'use strict';
	
	myApp.controller('AirportsController', ['$scope', '$http', function($scope, $http) {
		console.log('airports controller');
		
		var vm = this;
		
		vm.addToActiveList = addToActiveList;
		vm.ifNextPageAvailable = ifNextPageAvailable;
		vm.ifPrevPageAvailable = ifPrevPageAvailable;
		
		
		$scope.airports = '';
		
		function getAirports() {
			$http({
				method: 'GET',
				url: '/api/scrap'
			})
			.then(function (response) {
				console.log(response.data);
				$scope.airports = response.data.content;
			})
			.catch(function (response) {
				console.log(response.status);
			})
		}
		
		getAirports();
		 
		function addToActiveList(code) {
			console.log('Add to active list');
			$http({
				method: 'POST',
				url: '/api/active',
				data: code
			})
			.then(function (response) {
				console.log(response.status);
			})
			.catch(function (response) {
				console.log('NE dodato');
			});
		}
		
		function ifNextPageAvailable(){
			
		}
		
		function ifPrevPageAvailable(){
			
		}
	}]);
})();