

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
            when('/home', { templateUrl: 'home.html', controller: 'HomeController' });
    }]);

    app.filter('unique', function () {
        // we will return a function which will take in a collection
        // and a keyname
        return function (collection, keyname) {
            // we define our output and keys array;
            var output = [],
                keys = [];

            // we utilize angular's foreach function
            // this takes in our original collection and an iterator function
            angular.forEach(collection, function (item) {
                // we check to see whether our object exists
                var key = item[keyname];
                // if it's not already part of our keys array
                if (keys.indexOf(key) === -1) {
                    // add it to our keys array
                    keys.push(key);
                    // push this item to our final output array
                    output.push(item);
                }
            });
            // return our array which should be devoid of
            // any duplicates
            return output;
        };
    });

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

    app.controller("HomeController", function ($scope, $location)
    {
        $scope.selectedRow = null;
        $scope.orte = orte;

        $scope.removeSelection = function ()
        {
            $scope.search.Land = "";
            $scope.search.Bundesland = "";
            $scope.search.Stadt = "";
            $scope.search.Locationtyp = "";
        }
        $scope.setSelectedRow = function (index)
        {
            $scope.selectedRow = index;
            alert(index);
        }

        $scope.saveBewertung = function(bew)
        {
            bewertung = bew;
            if (bewertung != null)
            {
                alert($scope.orte[$scope.selectedRow].Locationname);
                if ($scope.orte[$scope.selectedRow] != null)
                {
                    orte[$scope.selectedRow].Bewertung = bewertung;
                    $scope.orte = orte;
                }
            }
        }
        setTimeout(function ()
        {
            $scope.$apply();
        }, 2000);
    });
})();