(function(){
	'use strict';
	
	myApp.controller('AirportController', ['$scope', '$http', function($scope, $http) {
	  console.log('hola airport controller');
	  var vm = this;
	  
	  
	  vm.saveAirport = saveAirport;
	  
	  //vm.airport = '';
	  
	  function saveAirport(){
		  $http({
			  method: 'POST',
			  url: 'api/airports',
			  data: vm.airport
		  })
		  .then(function (response) {
			  console.log(response.status);
		  })
		  .catch(function (response) {
			  console.log(response.status);
		  });
	  } 
	}]);
})();