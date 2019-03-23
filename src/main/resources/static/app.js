var myApp = angular.module('scrap', ['ui.router', 'ngStorage']);

myApp.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

        .state('home', {
            url: '/home',
            templateUrl: 'views/partial-home.html'
        })

        .state('airport', {
        	url: '/airport',
        	templateUrl: 'views/airport.html',
        	controller: 'AirportController as ac'
        })
        
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
             
        .state('limit', {
        	url: '/limit',
        	templateUrl: 'views/limit.html',
        	controller: 'LimitController as lc'
        })
        
        .state('translate', {
        	url:'/translate',
        	templateUrl: 'views/translate.html',
        	controller: 'TranslateController as tc',
        })
        
        .state('reports', {
        	url: '/reports',
        	templateUrl: 'views/reports.html',
        	controller: 'ReportsController as rc'
        });

});

