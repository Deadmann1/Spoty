

(function ()
{
    'use strict';
    var orte = [
        {
            "Land": "Österreich",
            "Bundesland": "Kärnten",
            "Stadt": "Klagenfurt",
            "Locationname": "CineCity",
            "Locationtyp": "Einkaufszentrum",
            "Bewertung": "Keine Bewertung"
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Kärnten",
            "Stadt": "Villach",
            "Locationname": "V-Club",
            "Locationtyp": "Disco",
            "Bewertung": "Keine Bewertung"
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Kärnten",
            "Stadt": "Hermagor",
            "Locationname": "MCDonalds Hermagor",
            "Locationtyp": "Fast Food",
            "Bewertung": "Keine Bewertung"
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Tirol",
            "Stadt": "Sillian",
            "Locationname": "OMV Tankstelle",
            "Locationtyp": "Tankstelle",
            "Bewertung": "Keine Bewertung"
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Tirol",
            "Stadt": "Innsbruck",
            "Locationname": "Museum",
            "Locationtyp": "Museum",
            "Bewertung": "Keine Bewertung"
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Niederösterreich",
            "Stadt": "St. Pölten",
            "Locationname": "MCDonalds St.Pölten",
            "Locationtyp": "Fast Food",
            "Bewertung": "Keine Bewertung"
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Steiermark",
            "Stadt": "Graz",
            "Locationname": "Atrio",
            "Locationtyp": "Einkaufszentrum",
            "Bewertung": "Keine Bewertung"
        }
    ];

    var username;
    var password;
    var bewertung;


    var app = angular.module("SpotyWebApplication", ['ngRoute']);


    app.config(['$routeProvider', function ($routeProvider)
    {
        $routeProvider.
            when('/', { templateUrl: 'login.html', controller: 'LoginController' }).
            when('/home', { templateUrl: 'home.html', controller: 'HomeController' }).
            when('/bewerten', { templateUrl: 'bewerten.html', controller: 'BewertenController' });
    }]);

    app.controller("LoginController", function ($scope, $location)
    {
        var User =
        [
            {
                "Username": "admin",
                "Password": "admin"
            }
            ,
            {
                "Username": "user1",
                "Password": "asdf"
            }
            ,
            {
                "Username": "user2",
                "Password": "asdf"
            }
        ];
        $scope.goToHome = function (path) {
            username = $scope.username;
            password = $scope.password;

            for (var i = 0; i < User.length; i++) {
                if ((username === User[i].Username) && (password === User[i].Password)) {
                    $location.path(path);
                }
            }
        }
    });

    app.controller("HomeController", function ($scope, $location) {
        $scope.orte = orte;

        $scope.goToBewerten = function (path)
        {
            $location.path(path);
        }

        setTimeout(function ()
        {
            $scope.$apply();
        }, 2000);
    });

    app.controller("BewertenController", function ($scope, $location)
    {
        $scope.orte = orte;

        
        $scope.saveBewertung = function (bew)
        {
            bewertung = bew;
            alert(bew);
        }

        $scope.goToHomeBack = function (path)
        {
            if (bewertung != null) 
            {
                if ($scope.orte.Locationname != null)
                {
                    for (var i = 0; i < orte.length; i++)
                    {
                        if (orte[i].Locationname == $scope.orte.Locationname)
                        {
                            orte[i].Bewertung = bewertung;
                        }
                    }
                    $location.path(path);
                }
            }
        }
    });
})();