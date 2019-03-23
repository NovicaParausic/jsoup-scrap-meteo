(function() {
	'use strict';
	
	myApp.controller('ActiveController', ['$scope', '$http', function($scope, $http) {
		console.log('HOLA aCTIVE cONTROLLER');
		
		var vm = this;
		
		$scope.activeAirports = '';
		
		function getActiveAirports() {
			$http({
				method: 'GET',
				url: '/api/active'
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
	}]);
})();