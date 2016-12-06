

(function ()
{
    'use strict';

    var CurrentUser = {};

    var app = angular.module("SpotyWebApplication", ['ngRoute']);


    app.config(['$routeProvider', function ($routeProvider)
    {
        $routeProvider.
            when('/', { templateUrl: 'login.html', controller: 'LoginController' }).
            when('/home', { templateUrl: 'home.html', controller: 'HomeController' });
    }]);

    app.filter('unique', locationFilter);

    app.controller("LoginController", function ($scope, $http, $location)
    {
        var Users = [];

        getUsersFromWebService($scope, $http, Users);

        checkLogin($scope, $location, Users, CurrentUser);
    });

    app.controller("HomeController", function ($scope, $http, $location)
    {
        var Locations = [];
        
        $scope.starRating1 = 0;
        $scope.hoverRating = 0;

        //alert(CurrentUser.IdUserAccount);
        //getRatingsFromWebServer($scope, CurrentUser);

        getLocationsFromWebService($scope, $http, Locations);

        $scope.Locations = Locations;


        $scope.removeSelection = function ()
        {
            $scope.search.LocationCountry = "";
            $scope.search.LocationCounty = "";
            $scope.search.LocationCity = "";
            $scope.search.LocationAddress = "";
            $scope.search.LocationName = "";
            $scope.search.LocationType = "";
            $scope.search.starRating1 = "";
        }

        $scope.starClick = function (param) {
            console.log('Click');
        };

        $scope.mouseStarHover = function (param)
        {
            $scope.hoverRating = param;
        };

        $scope.mouseStarLeave = function (param)
        {
            $scope.hoverRating = param + '*';
        };
    });

    app.directive('starRating', starRating);

})();

function getUsersFromWebService($scope, $http, Users)
{
    var httpGetRequestUsers = $http.get('http://spotyweb-backend.azurewebsites.net/api/users');

    httpGetRequestUsers.then(
        function (response)
        {
            for (var i = 0; i < response.data.length; i++) {
                var user = {
                    "IdUserAccount": response.data[i].IdUserAccount,
                    "Username": response.data[i].Username,
                    "Password": response.data[i].Password,
                    "Firstname": response.data[i].Firstname,
                    "Lastname": response.data[i].Lastname,
                    "Birthdate": response.data[i].Birthdate,
                    "IdAccountType": response.data[i].IdAccountType
                };
                Users.push(user);
            }
        },
        function (error) {
            $scope.LoginOutput = error.statusText;
            alert(error.statusText);
            alert(error.status);
        });
}

function getLocationsFromWebService($scope, $http, Locations)
{

    var locationtypes = [];
    var addresses = [];
    var cities = [];
    var counties = [];
    var countries = [];




    var httpGetRequestLocationtypes = $http.get('http://spotyweb-backend.azurewebsites.net/api/locationtypes');

    //Get All LocationTypes
    httpGetRequestLocationtypes.then
        (
        function (response)
        {
            for (var i = 0; i < response.data.length; i++) {
                //[{"IdLocationType":1,"LocationTypeName":"Restaurant"},
                var locationtype =
                {
                    "IdLocationType": response.data[i].IdLocationType,
                    "LocationTypeName": response.data[i].LocationTypeName,
                };
                locationtypes.push(locationtype);
            }


            var httpGetRequestAddresses = $http.get('http://spotyweb-backend.azurewebsites.net/api/addresses');

            //Get all Adresses
            httpGetRequestAddresses.then(
                function (response) {
                    for (var i = 0; i < response.data.length; i++) {
                        //[{"IdAddress":1,"IdCity":1,"StreetName":"Völkermarkterstraße","HouseNumber":102}
                        var address = {
                            "IdAddress": response.data[i].IdAddress,
                            "IdCity": response.data[i].IdCity,
                            "StreetName": response.data[i].StreetName,
                            "HouseNumber": response.data[i].HouseNumber,
                        };
                        addresses.push(address);
                    }





                    var httpGetRequestCities = $http.get('http://spotyweb-backend.azurewebsites.net/api/cities');

                    //Get All Cities
                    httpGetRequestCities.then(
                       function (response) {
                           for (var i = 0; i < response.data.length; i++) {
                               //[{"IdCity":1,"PostalCode":9020,"CityName":"Klagenfurt","IdCounty":1}
                               var city = {
                                   "IdCity": response.data[i].IdCity,
                                   "PostalCode": response.data[i].PostalCode,
                                   "CityName": response.data[i].CityName,
                                   "IdCounty": response.data[i].IdCounty
                               };
                               cities.push(city);
                           }



                           var httpGetRequestCounties = $http.get('http://spotyweb-backend.azurewebsites.net/api/counties');

                           //Get All Counties
                           httpGetRequestCounties.then(
                              function (response) {
                                  for (var i = 0; i < response.data.length; i++) {
                                      //[{"IdCounty":1,"CountyName":"Kärnten","IdCountry":1}
                                      var location = {
                                          "IdCounty": response.data[i].IdCounty,
                                          "CountyName": response.data[i].CountyName,
                                          "IdCountry": response.data[i].IdCountry,
                                      };
                                      counties.push(location);
                                  }


                                  var httpGetRequestCountries = $http.get('http://spotyweb-backend.azurewebsites.net/api/countries');

                                  //Get All Countries
                                  httpGetRequestCountries.then(
                                    function (response) {
                                        for (var i = 0; i < response.data.length; i++) {
                                            //[{"IdCountry":1,"CountryName":"Österreich"}
                                            var country = {
                                                "IdCountry": response.data[i].IdCountry,
                                                "CountryName": response.data[i].CountryName,
                                            };
                                            countries.push(country);
                                        }

                                        var httpGetRequestLocations = $http.get('http://spotyweb-backend.azurewebsites.net/api/locations');



                                        //Get all Locations
                                        httpGetRequestLocations.then(
                                            function (response)
                                            {
                                                for (var i = 0; i < response.data.length; i++)
                                                {
                                                    //[{"IdLocation":1,"LocationName":"McDonalds","IdLocationType":3,"IdAddress":1},{"IdLocation":2,"LocationName":"Burger King","IdLocationType":3,"IdAddress":2},{"IdLocation":4,"LocationName":"Cineplexx","IdLocationType":1,"IdAddress":4},
                                                                                                                   
                                                    var LocationTypeResult = findWithAttr(locationtypes, "IdLocationType", response.data[i].IdLocationType);
                                                    var LocationAddressResult = findWithAttr(addresses, "IdAddress", response.data[i].IdAddress);
                                                    var LocationCityResult = findWithAttr(cities, "IdCity", addresses[LocationAddressResult].IdCity);
                                                    var LocationCountyResult = findWithAttr(counties, "IdCounty", cities[LocationCityResult].IdCounty);
                                                    var LocationCountryResult = findWithAttr(countries, "IdCountry", counties[LocationCountyResult].IdCountry);

                                                    var location =
                                                    {
                                                        "LocationName": response.data[i].LocationName,
                                                        "LocationType": locationtypes[LocationTypeResult].LocationTypeName,
                                                        "LocationAddress": addresses[LocationAddressResult].StreetName + " - " + addresses[LocationAddressResult].HouseNumber,
                                                        "LocationCity": cities[LocationCityResult].CityName,
                                                        "LocationCounty" : counties[LocationCountyResult].CountyName,
                                                        "LocationCountry": countries[LocationCountryResult].CountryName
                                                    };
                                                    Locations.push(location);
                                                }
                                            },
                                            function (error) {
                                                alert(error.statusText);
                                                alert(error.status);
                                            });
                                    },
                                    function (error) {
                                        alert(error.statusText);
                                        alert(error.status);
                                    });
                              },
                              function (error) {
                                  alert(error.statusText);
                                  alert(error.status);
                              });

                       },
                       function (error) {
                           alert(error.statusText);
                           alert(error.status);
                       });
                },
                function (error) {
                    alert(error.statusText);
                    alert(error.status);
                });
        },
        function (error) {
            alert(error.statusText);
            alert(error.status);
        });
}

function getRatingsFromWebServer(scope, CurrentUser)
{
    var httpGetRequestRatings = $http.get('http://spotyweb-backend.azurewebsites.net/api/ratings');

    //Get all Ratings from User
    httpGetRequestRatings.then(
        function (response)
        {
            alert("asdf");
            for (var i = 0; i < response.data.length; i++)
            {
                //[{"Grade":1,"Feedback":"Super Laden","IdUserAccount":1,"IdLocation":1}
                if (CurrentUser.IdUserAccount == response.data[i].IdUserAccount)
                {
                    scope.starRating1 = response.data[i].Grade;
                }
            }
            alert("klasjdf");
        },
        function (error) {
            alert(error.statusText);
            alert(error.status);
        });
}

function findWithAttr(array, attr, value)
{
    for (var i = 0; i < array.length; i += 1)
    {
        if (array[i][attr] === value)
        {
            return i;
        }
    }
    return -1;
}

function checkLogin($scope, $location, User, CurrentUser)
{
    $scope.goToHome = function (path)
    {
        var username = $scope.username;
        var password = $scope.password;
        var loginOK = false;

        for (var i = 0; i < User.length && loginOK == false; i++)
        {
            if ((username === User[i].Username) && (password === User[i].Password))
            {
                CurrentUser = User[i];
                $location.path(path);
                loginOK = true;
            }
        }
        if (!loginOK)
        {
            alert("Login fehlgeschlagen");
        }
    }
}

function starRating() 
{
    return {
        scope: {
            rating: '=',
            maxRating: '@',
            readOnly: '@',
            click: "&",
            mouseHover: "&",
            mouseLeave: "&"
        },
        restrict: 'EA',
        template:
            "<div style='display: inline-block; margin: 0px; padding: 0px; cursor:pointer;' ng-repeat='idx in maxRatings track by $index'> \
                    <img ng-src='{{((hoverValue + _rating) <= $index) && \"http://www.codeproject.com/script/ratings/images/star-empty-lg.png\" || \"http://www.codeproject.com/script/ratings/images/star-fill-lg.png\"}}' \
                    ng-Click='isolatedClick($index + 1)' \
                    ng-mouseenter='isolatedMouseHover($index + 1)' \
                    ng-mouseleave='isolatedMouseLeave($index + 1)'></img> \
            </div>",
        compile: function (element, attrs) {
            if (!attrs.maxRating || (Number(attrs.maxRating) <= 0)) {
                attrs.maxRating = '5';
            };
        },
        controller: function ($scope, $element, $attrs) {
            $scope.maxRatings = [];

            for (var i = 1; i <= $scope.maxRating; i++) {
                $scope.maxRatings.push({});
            };

            $scope._rating = $scope.rating;

            $scope.isolatedClick = function (param)
            {
                if ($scope.readOnly == 'true') return;

                $scope.rating = $scope._rating = param;
                $scope.hoverValue = 0;
                $scope.click({
                    param: param
                });
            };

            $scope.isolatedMouseHover = function (param) {
                if ($scope.readOnly == 'true') return;

                $scope._rating = 0;
                $scope.hoverValue = param;
                $scope.mouseHover({
                    param: param
                });
            };

            $scope.isolatedMouseLeave = function (param) {
                if ($scope.readOnly == 'true') return;

                $scope._rating = $scope.rating;
                $scope.hoverValue = 0;
                $scope.mouseLeave({
                    param: param
                });
            };
        }
    };
}

function locationFilter()
{

    return function (collection, keyname) {

        var output = [],
            keys = [];

        angular.forEach(collection, function (item) {

            var key = item[keyname];

            if (keys.indexOf(key) === -1) {            
                keys.push(key);
                output.push(item);
            }
        });
      
        return output;
    };
}