(function() {
	'use strict';
	
	myApp.controller('ReportsController', [ 
					'$scope', '$http', '$state', '$timeout', 'dataStoreService',
						function($scope, $http, $state, $timeout, dataStoreService) {
		
		console.log('reports controller');
		
		var vm = this;
		
		vm.translateMetar = translateMetar;
		
		$scope.metars = '';
		var timeout = '';
		
		function getMetars(){
			//console.log(new Date());
			$http({
				method: 'GET',
				url: '/api/active/metarsFromCart'
			})
			.then( function(response) {
				if(response && response.data) {
					$scope.metars = response.data;
					console.log($scope.metars.length);
					var reports = $scope.metars;  
					repeatGet();
				}
			})
			.catch( function(response) {
				console.log(response.status);
			});
		}
		
		getMetars();
				
		function repeatGet() {
			console.log(new Date());
			timeout = $timeout(getMetars, 60000);
		}
		
		function translateMetar(code){
			dataStoreService.saveCode(code);
			$state.go('translate');
		}
		
		$scope.$on('$destroy', function() {
		    $timeout.cancel(timeout);
		});
	}]);
})();