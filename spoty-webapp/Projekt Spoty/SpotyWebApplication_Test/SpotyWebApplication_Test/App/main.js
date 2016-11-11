

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
            "Bewertung": 0
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Kärnten",
            "Stadt": "Villach",
            "Locationname": "V-Club",
            "Locationtyp": "Disco",
            "Bewertung": 0
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Kärnten",
            "Stadt": "Hermagor",
            "Locationname": "MCDonalds Hermagor",
            "Locationtyp": "Fast Food",
            "Bewertung": 0
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Tirol",
            "Stadt": "Sillian",
            "Locationname": "OMV Tankstelle",
            "Locationtyp": "Tankstelle",
            "Bewertung": 0
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Tirol",
            "Stadt": "Innsbruck",
            "Locationname": "Museum",
            "Locationtyp": "Museum",
            "Bewertung": 0
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Niederösterreich",
            "Stadt": "St. Pölten",
            "Locationname": "MCDonalds St.Pölten",
            "Locationtyp": "Fast Food",
            "Bewertung": 0
        }
        ,
        {
            "Land": "Österreich",
            "Bundesland": "Steiermark",
            "Stadt": "Graz",
            "Locationname": "Atrio",
            "Locationtyp": "Einkaufszentrum",
            "Bewertung": 0
        }
        ,
        {
            "Land": "Deutschland",
            "Bundesland": "Bayern",
            "Stadt": "München",
            "Locationname": "MCDonalds",
            "Locationtyp": "Fast Food",
            "Bewertung": 0
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
            var loginOK = false;

            for (var i = 0; i < User.length && loginOK == false; i++) {
                if ((username === User[i].Username) && (password === User[i].Password)) {
                    $location.path(path);
                    loginOK = true;
                }
            }
            if (!loginOK)
            {
                alert("Login fehlgeschlagen");
            }
        }
    });

    app.controller("HomeController", function ($scope, $location)
    {
        $scope.selectedRow = null;
        $scope.ratings = [];
        $scope.orte = orte;

        $scope.ratings.push({ current: 3, max: 5 });


        $scope.getSelectedRow = function (index)
        {
            $scope.selectedRow = index;
        }

        $scope.removeSelection = function ()
        {
            $scope.search.Land = "";
            $scope.search.Bundesland = "";
            $scope.search.Stadt = "";
            $scope.search.Locationtyp = "";

            //for (var i = 0; i < orte.length; i++)
            //{
            //    $scope.ratings[i] = orte[i].Bewertung;
            //}
        }

        $scope.getSelectedRating = function (index)
        {
            $scope.ratings[$scope.selectedRow].current = index;
            $scope.orte[$scope.selectedRow].Bewertung = index;
        }

        setTimeout(function ()
        {
            $scope.$apply();
        }, 200);
    });


    app.directive('starRating', function () {
        return {
            restrict: 'A',
            template: '<ul class="rating">' +
                '<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">' +
                '\u2605' +
                '</li>' +
                '</ul>',
            scope: {
                ratingValue: '=ngModel',
                max: '=',
                onRatingSelected: '&'
            },
            link: function (scope, elem, attrs) {

                var updateStars = function () {
                    scope.stars = [];
                    for (var i = 0; i < scope.max; i++) {
                        scope.stars.push({
                            filled: i < scope.ratingValue
                        });
                    }
                };

                scope.toggle = function (index) {
                    scope.ratingValue = index + 1;
                    scope.onRatingSelected({
                        rating: index + 1
                    });
                };

                scope.$watch('ratingValue', function (oldVal, newVal) {
                    if (newVal) {
                        updateStars();
                    }
                });
            }
        }
    });
})();