var myApp = angular.module('scrap', ['ui.router', 'ngStorage']);

myApp.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/airports');

    $stateProvider
    	
        .state('airports', {
        	url: '/airports',
        	templateUrl: 'views/airports.html',
        	controller: 'AirportsController as arc'
        })
        
        .state('active', {
        	url: '/active',
        	templateUrl: 'views/active.html',
        	controller: 'ActiveController as acc'
        })
        
         .state('reports', {
        	url: '/reports',
        	templateUrl: 'views/reports.html',
        	controller: 'ReportsController as rc'
        })
        
        .state('translate', {
        	url:'/translate',
        	templateUrl: 'views/translate.html',
        	controller: 'TranslateController as tc',
        });
});

