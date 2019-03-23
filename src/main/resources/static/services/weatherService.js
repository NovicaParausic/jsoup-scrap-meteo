(function () {
	'use strict';
	
	myApp.constant('ConstValues', {
		'a': 17.625,
		'b': 243.04
	});
	
	myApp.factory('weatherService', ['ConstValues', function(ConstValues) {
		console.log('holaaa');
		
		var weatherService = {
				calculateHumidity: calculateHumidity,
				parseWeather: parseWeather,
				parseWind: parseWind
		};
		
		function calculateHumidity(temperature, dewPoint){
			var a = ConstValues.a;
			var b = ConstValues.b;
			
			var up = (a * dewPoint) / (b + dewPoint); 
			var dwn = (a * temperature) / (b + temperature);
			
			var ret = Math.exp(up) / Math.exp(dwn);
			ret = Math.round(ret * 100);
			
			return ret;
		}
		
		function parseWind(wind){
			var ret = [];
			var degreeSpeed = wind.directionDegrees + ' at ' + wind.speed + ' KT'; 
			console.log(degreeSpeed);
			ret.push(degreeSpeed);
			
			if (wind.extreme1 && wind.extreme2) {
				var variable = 'Variable between ' + wind.extreme1 + ' and ' + wind.extreme2;
				console.log(variable);
				ret.push(variable);
			}
			
			console.log(wind.gust);
			if (wind.gust > 0) {
				ret.push(wind.gust);
			}
			
			return ret;
		}
		
		function parseWeather(weathers){
			var ret = [];
			if(weathers){
				for(var i = 0; i < weathers.length; i ++){
					ret.push(weathers[i].phenomenons[0]);
				}
				
				return ret;
			}
			return '/';
		}
		
		return weatherService;
	}]);
})();