(function ()
{
    'use strict';

    var clickedStarRating = 0;
    var currentUser = null;
    var googleMapsRatings = [];
    var mapLocations = [];
    var map;

    var app = angular.module("SpotyWebApplication", ['ngRoute']);


    app.config(['$routeProvider', function ($routeProvider)
    {
        $routeProvider.
            when('/', { templateUrl: 'login.html', controller: 'LoginController' }).
            when('/home', { templateUrl: 'home.html', controller: 'HomeController' });
    }]);

    app.filter('unique', locationFilter);

    app.controller("LoginController", LoginController);

    app.controller("HomeController", HomeController);

    app.directive('starRating', starRating);
   
    app.directive('myMap', googleMaps);

function LoginController ($scope, $http, $location)
{
    var Users = [];

    getUsersFromWebService($scope, $http, Users);

    checkLogin($scope, $location, Users);
}

function HomeController($scope, $http, $location, $filter, $timeout)
{
    var directionsDisplay = new google.maps.DirectionsRenderer();
    var directionsService = new google.maps.DirectionsService();

    $scope.map =
    {
        control: {},
        center: {
            latitude: 46,
            longitude: 14
        },
        zoom: 8
    }

    $scope.directions = {
        origin: "",
        destination: "",
        showList: false
    }

    $scope.getDirections = function () {
        var request = {
            origin: $scope.directions.origin,
            destination: $scope.directions.destination,
            travelMode: google.maps.DirectionsTravelMode.DRIVING
        };
        directionsService.route(request, function (response, status) {
            if (status === google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(response);
                directionsDisplay.setMap(map);
                directionsDisplay.setPanel(document.getElementById('directionsList'));
                $scope.directions.showList = true;
            } else {
                alert('Google route unsuccesfull!');
            }
        });
    }

    $scope.checkLoginFromUser = function()
    {
        console.log("vor CurrentUser === null");
        if (currentUser === null)
        {
            console.log(currentUser);
            console.log("currentUser == null")
            $location.path("/");
            $scope.$apply();
        }
    }
    $scope.checkLoginFromUser();


    var Locations = [];
    var maxRatings = [1, 2, 3, 4, 5];
    var Ratings = [];
    var isolatedLocation;
    var currentRating;

    $scope.hoverRating = 0;

    getLocationsFromWebService($http, Locations, mapLocations);
    getRatingsFromWebServerForGoogleMap($http);

    $scope.Locations = Locations;

    $scope.addStarRating = 0;

    $scope.removeSelection = function () 
    {
        $scope.search = undefined;
        $scope.search.$ = undefined;
        $scope.search.LocationCountry = undefined;
        $scope.search.LocationCounty = undefined;
        $scope.search.LocationCity = undefined;
        $scope.search.LocationAddress = undefined;
        $scope.search.LocationName = undefined;
        $scope.search.LocationType = undefined;
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
        if ($scope.search != undefined)
        {
            console.log($scope.search.LocationCountry);
            if ($scope.search.LocationCountry != undefined)
            {
                isolatedLocation = $scope.Locations.filter((Location) => Location.LocationCountry === $scope.search.LocationCountry)[selectedIndex];
            }
            if ($scope.search.LocationCounty != undefined)
            {
                isolatedLocation = $scope.Locations.filter((Location) => Location.LocationCounty === $scope.search.LocationCounty)[selectedIndex];
            }
            if ($scope.search.LocationCity != undefined)
            {
                isolatedLocation = $scope.Locations.filter((Location) => Location.LocationCity === $scope.search.LocationCity)[selectedIndex];
            }
            if ($scope.search.LocationAddress != undefined)
            {
                isolatedLocation = $scope.Locations.filter((Location) => Location.LocationAddress === $scope.search.LocationAddress)[selectedIndex];
            }
            if ($scope.search.LocationName != undefined)
            {
                isolatedLocation = $scope.Locations.filter((Location) => Location.LocationName === $scope.search.LocationName)[selectedIndex];
            }
            if ($scope.search.LocationType != undefined)
            {
                isolatedLocation = $scope.Locations.filter((Location) => Location.LocationType === $scope.search.LocationType)[selectedIndex];
            }
        }

        console.log(isolatedLocation);
        getRatingsFromWebServer(Ratings, $scope, $http, isolatedLocation);
        $scope.addStarRatingLabel = "";

        angular.element(document.getElementById("btnAddRating").className = "btn btn-primary active");

    }

    $timeout(function () {
        $scope.$apply();
    }, 3000);
}

function googleMaps()
{
    function link(scope, element, attrs)
    {
        var infoWindow;
        var markers = [];
        var marker = {};
        // map config
        var mapOptions = {
            center: new google.maps.LatLng(46, 14),
            zoom: 14,
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

        //show the map and place some markers
        if (navigator.geolocation)
        {
            navigator.geolocation.getCurrentPosition(showPosition);
        }
        function showPosition(position)
        {
            var image = 'Marker_icon.png';
            var marker = new google.maps.Marker({
                position: { lat: position.coords.latitude, lng: position.coords.longitude },
                map: map,
                title: "Your Position",
                icon: image
            });

            setMarker(map, marker.position, marker.title, "Your position");
            map.setCenter(marker.position);
            try
            {
                var geocoder = new google.maps.Geocoder();
                geocoder.geocode({ 'latLng': marker.position}, function (results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        if (results[1])
                        {
                            scope.directions.origin = results[1].formatted_address;
                        }
                        else {
                            console.log("Fehler!");
                        }
                    } else {
                        console.log('Geocoder failed due to: ' + status);
                    }
                });
            }
            catch (e)
            {
                console.log(e);
            }
            try {
                for (var i = 0; i < mapLocations.length; i++) {
                    (function (i) {
                        var geocoder = new google.maps.Geocoder();
                        if (geocoder) {
                            var country = "Country: " + mapLocations[i].LocationCountry;
                            var county = "County: " + mapLocations[i].LocationCounty
                            var city = "City: " + mapLocations[i].LocationCity
                            var address = "Address: " + mapLocations[i].LocationAddress;
                            var locationname = "Location: " + mapLocations[i].LocationName;
                            var locationtype = "Locationtype: " + mapLocations[i].LocationType;
                            var ratingsDurchschnitt = 0.00;
                            try {
                                var anzahlVonRatings = 0;
                                for (var j = 0; j < googleMapsRatings.length; j++) {
                                    if (mapLocations[i].IdLocation == googleMapsRatings[j].IdLocation) {
                                        if (angular.isNumber(googleMapsRatings[j].Grade)) {
                                            ratingsDurchschnitt = ratingsDurchschnitt + googleMapsRatings[j].Grade;
                                            anzahlVonRatings = anzahlVonRatings + 1;
                                        }
                                    }

                                }
                                if (ratingsDurchschnitt != 0 && anzahlVonRatings != 0)
                                    ratingsDurchschnitt = ratingsDurchschnitt / anzahlVonRatings;

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

                                        if (ratingsDurchschnitt != 0) {
                                            var contentString = '<p>' +
                                            country +
                                            '</p><p>' +
                                            county +
                                            '</p><p>' +
                                            city +
                                            '</p><p>' +
                                            address +
                                            '</p><p>' +
                                            locationname +
                                            '</p><p>' +
                                            locationtype +
                                            '</p>' +
                                            '<p>Durchschnitt - Rating: ' +
                                            ratingsDurchschnitt +
                                            '</p>';
                                        }
                                        else {
                                            var contentString = '<p>' +
                                            country +
                                            '</p><p>' +
                                            county +
                                            '</p><p>' +
                                            city +
                                            '</p><p>' +
                                            address +
                                            '</p><p>' +
                                            locationname +
                                            '</p><p>' +
                                            locationtype +
                                            '</p>' +
                                            '<p>Keine Ratings vorhanden</p>';
                                        }

                                        var infowindow = new google.maps.InfoWindow({
                                            content: contentString
                                        });

                                        google.maps.event.addListener(newMarker, 'click', function ()
                                        {
                                            infowindow.open(map, newMarker);
                                            scope.directions.destination = mapLocations[i].LocationAddress + ", " + mapLocations[i].LocationCity + " " + mapLocations[i].LocationCountry;
                                            scope.$apply();
                                        });
                                        setMarker(newMarker, "aösdfkj");
                                    }
                                });
                            }
                            catch (e) {
                                console.log(e);
                            }
                        }
                    })(i);
                }
            }
            catch (e) {
                console.log(e);
            }
        }
        scope.$apply();
    };
    return {
        restrict: 'A',
        template: '<div id="gmaps" control="map.control" center="map.center" zoom="map.zoom" draggable="true"></div>',
        replace: true,
        link: link,
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
                    var dateStringSplitted = response.data[i].Date.split('-');
                    var dateYear = dateStringSplitted[0];
                    var dateMonth = dateStringSplitted[1];
                    var dateDay = dateStringSplitted[2];
                    
                    var dateString;

                    if (dateDay < 10)
                    {
                        if (dateMonth < 10)
                        {
                            dateString = "0" + dateDay + "." + "0" + dateMonth + "." + dateYear;
                        }
                        else
                        {
                            dateString = "0" + dateDay + "." + dateMonth + "." + dateYear;
                        }
                    }
                    else
                    {
                        if (dateMonth < 10)
                        {
                            dateString = dateDay + "." + "0" + dateMonth + "." + dateYear;
                        }
                        else
                        {
                            dateString = dateDay + "." + dateMonth + "." + dateYear;
                        }
                    }
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

function getRatingsFromWebServerForGoogleMap($http)
{
    var httpGetRequestRatings = $http.get('http://spotyweb-backend.azurewebsites.net/api/ratings/');

    //Get all Ratings from User
    httpGetRequestRatings.then(
        function (response) {
            if (response.status == 200) {
                for (var i = 0; i < response.data.length; i++) {
                    //[{"Grade":1,"Feedback":"Test","IdUserAccount":1,"IdLocation":1,"Date":"2015-09-22T00:00:00.000Z"}]
                    var dateStringSplitted = response.data[i].Date.split('-');
                    var dateYear = dateStringSplitted[0];
                    var dateMonth = dateStringSplitted[1];
                    var dateDay = dateStringSplitted[2];

                    var dateString;

                    if (dateDay < 10) {
                        if (dateMonth < 10) {
                            dateString = "0" + dateDay + "." + "0" + dateMonth + "." + dateYear;
                        }
                        else {
                            dateString = "0" + dateDay + "." + dateMonth + "." + dateYear;
                        }
                    }
                    else {
                        if (dateMonth < 10) {
                            dateString = dateDay + "." + "0" + dateMonth + "." + dateYear;
                        }
                        else {
                            dateString = dateDay + "." + dateMonth + "." + dateYear;
                        }
                    }
                    var rating =
                        {
                            "Grade": response.data[i].Grade,
                            "Feedback": response.data[i].Feedback,
                            "IdUserAccount": response.data[i].IdUserAccount,
                            "IdLocation": response.data[i].IdLocation,
                            "Date": dateString
                        }
                    googleMapsRatings.push(rating);
                }
            }
        },
        function (error) {
            $scope.locationNotFound = "Es ist ein Fehler aufgetreten, bitte versuchen Sie es nochmal!";
            alert(error.statusText);
            alert(error.status);
        });
}

function findWithAttr(array, attr, value)
{
    var retValue = -1;
    for (var i = 0; i < array.length; i += 1)
    {
        if (array[i][attr] === value)
        {
            retValue = i;
        }
    }
    return retValue;
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
                console.log("loginOK");
                currentUser = User[i];
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
                clickedStarRating = param;
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