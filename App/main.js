

(function () {
    'use strict';
    var username;
    var password;

    var app = angular.module("SpotyWebApplication", ['ngRoute']);

    app.config(['$routeProvider', function ($routeProvider)
    {
        $routeProvider.
            when('/', { templateUrl: 'login.html', controller: 'LoginController' }).
            when('/home', { templateUrl: 'home.html', controller: 'HomeController' });
    }]);

    app.controller("LoginController", function ($scope, $location)
    {
        $scope.goToHome = function (path)
        {
            username = $scope.username;
            password = $scope.password;

            $location.path(path);
        }
    });

    app.controller("HomeController", function ($scope)
    {
        $scope.orte = [
            {
                "Land": "Österreich",
                "Bundesland": "Kärnten",
                "Stadt": "Klagenfurt",
                "Location" : "CineCity"
            }
            ,
            {
                "Land": "Österreich",
                "Bundesland": "Kärnten",
                "Stadt": "Villach",
                "Location" : "V-Club"
            }
            ,
            {
                "Land": "Österreich",
                "Bundesland": "Kärnten",
                "Stadt": "Hermagor",
                "Location" : "MCDonalds"

            }
            ,
            {
                "Land": "Österreich",
                "Bundesland": "Tirol",
                "Stadt": "Sillian",
                "Location": "OMV Tankstelle"
            }
            ,
            {
                "Land": "Österreich",
                "Bundesland": "Tirol",
                "Stadt": "Innsbruck",
                "Location": "Museum"
            }
            ,
            {
                "Land": "Österreich",
                "Bundesland": "Niederösterreich",
                "Stadt": "St. Pölten",
                "Location": "MCDonalds"
            }
            ,
            {
                "Land": "Österreich",
                "Bundesland": "Steiermark",
                "Stadt": "Graz",
                "Location": "asdf"
            }
        ];
    });
})();