(function() {
	'use strict';
	
	myApp.controller('ReportsController', [ 
					'$scope', '$http', '$state', 'dataStoreService',
					function($scope, $http, $state, dataStoreService) {
		
		console.log('reports controller');
		
		var vm = this;
		
		vm.translateMetar = translateMetar;
		
		$scope.metars = '';
		
		function getMetars(){
			console.log(new Date());
			$http({
				method: 'GET',
				url: '/api/active/metarsFromCart'
			})
			.then( function(response) {
				if(response && response.data) {
					$scope.metars = response.data;
				}
			})
			.catch( function(response) {
				console.log(response.status);
			});
		}
		
		getMetars();
		
		var d = new Date();
		var h = d.getMinutes() % 5;
		
		
		function repeatDate() {
			console.log(new Date());
			getMetars();
			setTimeout(repeatDate,  5 * 60 * 1000);
		}
		
		setTimeout(repeatDate, (5 - h) * 60 * 1000);
		
		function translateMetar(code){
			dataStoreService.saveCode(code);
			$state.go('translate');
		}
	}]);
})();
