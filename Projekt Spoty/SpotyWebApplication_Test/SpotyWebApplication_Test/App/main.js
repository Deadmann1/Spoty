(function ()
{
    var mapLocations = [];
    'use strict';

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

        checkLogin($scope, $location, Users);
    });

    app.controller("HomeController", function ($scope, $http, $location)
    {

        var Locations = [];
        var Ratings = [];
        var maxRatings = [1, 2, 3, 4, 5];
        var isolatedLocation

        angular.element(document.getElementById('btnAddRating'))[0].disabled = true;

        $scope.hoverRating = 0;
        getLocationsFromWebService($http, Locations, mapLocations);
             
        $scope.Locations = Locations;

        $scope.data = {
            model: null,
            availableOptions: [
              { id: '1', name: 'Sehr Gut' },
              { id: '2', name: 'Gut' },
              { id: '3', name: 'Befriedigend' },
              { id: '4', name: 'Genügend' },
              { id: '5', name: 'Schlecht' }
            ]
        };

        $scope.addStarRating = 0;

        $scope.removeSelection = function () {
            $scope.search.$ = "";
            $scope.search.LocationCountry = "";
            $scope.search.LocationCounty = "";
            $scope.search.LocationCity = "";
            $scope.search.LocationAddress = "";
            $scope.search.LocationName = "";
            $scope.search.LocationType = "";
            $scope.search.StarRating = "";
            $scope.data.model = "";
            $scope.locationNotFound = "";
        }
        $scope.mouseStarHover = function (param) {
            $scope.hoverRating = param;
        };

        $scope.mouseStarLeave = function (param) {
            $scope.hoverRating = param + '*';
        };

        $scope.locationSelected = function (selectedIndex)
        {
            isolatedLocation = $scope.Locations[selectedIndex];
            getRatingsFromWebServer(Ratings, $scope, $http, isolatedLocation);

            angular.element(document.getElementById('btnAddRating'))[0].disabled = false;
            alert(angular.element(document.getElementById('stars'))[0].value);
        }

        $scope.btnAddRating = function ()
        {

            var feedback = angular.element(document.getElementById('txtFeedback'))[0].value;
            var date = angular.element(document.getElementById('txtDate'))[0].value;
            if (feedback != "" && date != "")
            {
                
            }
        }
    });



    app.directive('starRating', starRating);
   
    app.directive('myMap', googleMaps);


function googleMaps()
{
        var link = function (scope, element, attrs)
        {
            var map, infoWindow;
            var markers = [];
            var marker = {};
            // map config
            var mapOptions = {
                center: new google.maps.LatLng(46, 14),
                zoom: 8,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                scrollwheel: true
            };

            // init the map
            function initMap() {
                if (map === void 0) {
                    map = new google.maps.Map(element[0], mapOptions);
                }
            }

            // place a marker
            function setMarker(map, position, title, content) {
                var marker;
                var markerOptions = {
                    position: position,
                    map: map,
                    title: title,
                    icon: 'https://maps.google.com/mapfiles/ms/icons/green-dot.png'
                };

                marker = new google.maps.Marker(markerOptions);
                markers.push(marker); // add marker to array

                google.maps.event.addListener(marker, 'click', function () {
                    // close window if not undefined
                    if (infoWindow !== void 0) {
                        infoWindow.close();
                    }
                    // create new window
                    var infoWindowOptions = {
                        content: content
                    };
                    infoWindow = new google.maps.InfoWindow(infoWindowOptions);
                    infoWindow.open(map, marker);
                });
            }


            initMap();


            // show the map and place some markers

            if (navigator.geolocation)
            {
                navigator.geolocation.getCurrentPosition(showPosition);
            }
            function showPosition(position)
            {
                mapOptions.center = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                mapOptions.zoom = 8;
                map = map = new google.maps.Map(element[0], mapOptions);

                var image = 'Marker_icon.png';
                var marker = new google.maps.Marker({
                    position: { lat: position.coords.latitude, lng: position.coords.longitude },
                    map: map,
                    title: "Your Position",
                    icon:image
                });

                setMarker(marker, 'Your current Position');
                try
                {
                    for (var i = 0; i < mapLocations.length; i++)
                    {
                        (function (i)
                        {
                            var geocoder = new google.maps.Geocoder();
                            if (geocoder) {
                                var country = "Country: " + mapLocations[i].LocationCountry;
                                var county = "County: " + mapLocations[i].LocationCounty
                                var city = "City: " + mapLocations[i].LocationCity
                                var address = "Address: " + mapLocations[i].LocationAddress;
                                var locationname = "Location: " + mapLocations[i].LocationName;
                                var locationtype = "Locationtype: " + mapLocations[i].LocationType;
                                geocoder.geocode({ 'address': "\"" + mapLocations[i].LocationCounty + " " + mapLocations[i].LocationCity + " " + mapLocations[i].LocationAddress + "\"" }, function (results, status) {
                                    if (status === google.maps.GeocoderStatus.OK) {
                                        var latitude = results[0].geometry.location.lat();
                                        var longitude = results[0].geometry.location.lng();

                                        var R = 6371; // Radius of the earth in km
                                        var dLat = (position.coords.latitude - latitude) * 3.141592653589793 / 180;  // deg2rad below
                                        var dLon = (position.coords.longitude - longitude) * 3.141592653589793 / 180;
                                        var a = 0.5 - Math.cos(dLat) / 2 + Math.cos(latitude * Math.PI / 180) * Math.cos(position.coords.latitude * Math.PI / 180) * (1 - Math.cos(dLon)) / 2;

                                        var d = Math.round((R * 2 * Math.asin(Math.sqrt(a))) * 100) / 100;

                                        var newMarker = new google.maps.Marker({
                                            position: { lat: latitude, lng: longitude },
                                            map: map,
                                            title: "Name of " + locationname + "\nDistance between you and the Location: " + d + " (km)"
                                        });

                                        var infowindow = new google.maps.InfoWindow({
                                            content: '<p>' + country + '</p><p>' + county + '</p><p>' + city + '</p><p>' + address + '</p><p>' + locationname + '</p><p>' + locationtype + '</p>'
                                        });

                                        google.maps.event.addListener(newMarker, 'click', function () {
                                            infowindow.open(map, newMarker);
                                        });
                                        setMarker(newMarker, 'Position of the clicked Location');
                                    }
                                });
                            }
                        })(i);
                    }
                }
                catch (e)
                {

                }
            }
        };

    return {
        restrict: 'A',
        template: '<div id="gmaps"></div>',
        replace: true,
        link: link
    };
    // directive link function
    
}

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

function getLocationsFromWebService($http, Locations, mapLocations)
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
                                                        "IdLocation" : response.data[i].IdLocation,
                                                        "LocationName": response.data[i].LocationName,
                                                        "LocationType": locationtypes[LocationTypeResult].LocationTypeName,
                                                        "LocationAddress": addresses[LocationAddressResult].StreetName + " - " + addresses[LocationAddressResult].HouseNumber,
                                                        "LocationCity": cities[LocationCityResult].CityName,
                                                        "LocationCounty" : counties[LocationCountyResult].CountyName,
                                                        "LocationCountry": countries[LocationCountryResult].CountryName
                                                    };
                                                    Locations.push(location);
                                                    mapLocations.push(location);
                                                }
                                                googleMaps();
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

function getRatingsFromWebServer(Ratings, $scope, $http, isolatedLocation)
{
    var httpGetRequestRatings = $http.get('http://spotyweb-backend.azurewebsites.net/api/ratings/' + isolatedLocation.IdLocation);

    $scope.Ratings = [];
    Ratings = [];

    //Get all Ratings from User
    httpGetRequestRatings.then(
        function (response)
        {
            if (response.status == 200)
            {
                for (var i = 0; i < response.data.length; i++)
                {
                    //[{"Grade":1,"Feedback":"Test","IdUserAccount":1,"IdLocation":1,"Date":"2015-09-22T00:00:00.000Z"}]
                    var date = new Date(response.data[i].Date);
                    dateString = date.getDate() + "." + date.getMonth() + "." + date.getFullYear();
                    var rating =
                        {
                            "Grade": response.data[i].Grade,
                            "Feedback": response.data[i].Feedback,
                            "IdUserAccount": response.data[i].IdUserAccount,
                            "IdLocation": response.data[i].IdLocation,
                            "Date": dateString
                        }
                    Ratings.push(rating);
                }
                $scope.Ratings = Ratings;
                $scope.locationNotFound = "";
            }
            else
            {
                if (response.status == 204)
                {
                    $scope.Ratings = [];
                    $scope.locationNotFound = "Es sind keine Ratings für diese Location vorhanden";
                }
            }
        },
        function (error)
        {
            $scope.locationNotFound = "Es ist ein Fehler aufgetreten, bitte versuchen Sie es nochmal!";
            alert(error.statusText);
            alert(error.status);
        });
}

function addRatingToLocation(isolatedLocation)
{

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

function checkLogin($scope, $location, User)
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
                //CurrentUser = User[i];
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
            mouseLeave: "&",
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
        controller: function ($scope, $element, $attrs)
        {
            $scope.maxRatings = [];

            for (var i = 1; i <= $scope.maxRating; i++)
            {
                $scope.maxRatings.push({});
            };

            $scope._rating = $scope.rating;

            $scope.isolatedClick = function (param)
            {
                if ($scope.readOnly == 'true') return;
                $scope.rating = $scope._rating = param;
                $scope.hoverValue = 0;
                $scope.click(
                {
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
})();