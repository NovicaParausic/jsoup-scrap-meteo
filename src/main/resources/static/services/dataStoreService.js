(function (){
	'use strict';
	
	myApp.factory('dataStoreService',['$localStorage', function($localStorage) {
		  	var dataService = {
		  			saveCode: saveCode,
		  			loadCode: loadCode
		  	};
		  	console.log('holaaa');
		  	
		  	function saveCode(code) {
		  		$localStorage.code = code;
		  	}
		  	
		  	function loadCode() {
		  		return $localStorage.code;
		  	}
		  	
		  	return dataService;
		}]);
})();