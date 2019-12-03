(function() {
	'use strict';
	
	myApp.controller('ActiveController', ['$scope', '$http', function($scope, $http) {
			
		var vm = this;
		
		vm.deleteFromActive = deleteFromActive; 
		
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
		
		function deleteFromActive(code) {
			$http ({
				method: 'DELETE',
				url: '/api/active/' + code
			})
			.then (function (response) {
				console.log(response.status);
				getActiveAirports();
			})
			.catch (function (response) {
				console.log(response);
			});
		}
	}]);
})();