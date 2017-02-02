using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.Gms.Maps;
using Android.Gms.Maps.Model;
using Android.Locations;
using Android.OS;
using Android.Runtime;
using Android.Util;
using Android.Views;
using Android.Widget;
using Java.Security;
using spoty.Services;
using Spoty.Data;

namespace spoty
{
    [Activity(Label = "LocationMapActivity")]
    public class LocationMapActivity : Activity, IOnMapReadyCallback, ILocationListener, GoogleMap.IOnMarkerClickListener
    {
        private GoogleMap map;
        private Location currentLocation;
        LocationManager locMgr;
        private MapFragment _mapFragment;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            locMgr = GetSystemService(Context.LocationService) as LocationManager;
            SetContentView(Resource.Layout.LocationMapLayout);
            FindAllViewsById();
            GetLocationUpdates();
            _mapFragment.GetMapAsync(this);
        }

        private void GetLocationUpdates()
        {
            string Provider = LocationManager.GpsProvider;

            if (locMgr.IsProviderEnabled(Provider))
            {
                locMgr.RequestLocationUpdates(Provider, 2000, 1, this);
            }
            else
            {
                ShowAlertDialog("Info:",
                    "GPS not enabled only approximate coordinates and no user location, reenter map with gps enabled to get better results and more features!", "OK");
            }
        }

        private void FindAllViewsById()
        {
            _mapFragment = (MapFragment)FragmentManager.FindFragmentById(Resource.Id.map);
        }

        public void OnMapReady(GoogleMap googleMap)
        {
            map = googleMap;
            googleMap.MapType = GoogleMap.MapTypeNormal;
            locMgr.RequestSingleUpdate(LocationManager.NetworkProvider, this, null);
            currentLocation = locMgr.GetLastKnownLocation(LocationManager.NetworkProvider);
            googleMap.UiSettings.ZoomControlsEnabled = true;
            googleMap.UiSettings.CompassEnabled = true;
            googleMap.MyLocationEnabled = true;
            map.SetOnMarkerClickListener(this);
            SetStandardView();
            AddLocationsAsMarkers();
        }

        private void SetStandardView()
        {
            //LatLng locationCamera = new LatLng(46.63333333333333, 14.333333333333334);
            LatLng locationCamera = new LatLng(currentLocation.Latitude, currentLocation.Longitude);
            CameraPosition.Builder builder = CameraPosition.InvokeBuilder();
            builder.Target(locationCamera);
            builder.Zoom(7);
            CameraPosition cameraPosition = builder.Build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.NewCameraPosition(cameraPosition);
            map.MoveCamera(cameraUpdate);
            //Geocoder geo = new Geocoder(this);
            //var address = geo.GetFromLocationName("Völkermarkterstraße  140, Klagenfurt, Kärnten, Österreich", 2);
            //ShowAlertDialog("Test", "Lat: " + address[0].Latitude + " Long: " + address[0].Longitude, "OK");
        }

        private void AddLocationsAsMarkers()
        {
            GetLocationCoordinates();
            foreach (var location in Database.Instance.Locations.FindAll(x => x.Latitude != null))
            {
                MarkerOptions marker = new MarkerOptions();
                marker.SetPosition(new LatLng(location.Latitude, location.Longitude));
                marker.SetTitle(location.Name);
                switch (location.Type)
                {
                    case "Restaurant":
                        marker.SetIcon(BitmapDescriptorFactory.FromResource(Resources.GetIdentifier("ic_restaurant_black_24dp", "drawable", this.PackageName)));
                        break;
                    case "Caffee":
                        marker.SetIcon(BitmapDescriptorFactory.FromResource(Resources.GetIdentifier("ic_local_cafe_black_24dp", "drawable", this.PackageName)));
                        break;
                    case "Fast Food":
                        marker.SetIcon(BitmapDescriptorFactory.FromResource(Resources.GetIdentifier("ic_local_pizza_black_24dp", "drawable", this.PackageName)));
                        break;
                    case "Gasthaus":
                        marker.SetIcon(BitmapDescriptorFactory.FromResource(Resources.GetIdentifier("ic_local_dining_black_24dp", "drawable", this.PackageName)));
                        break;
                    case "Auto":
                        marker.SetIcon(BitmapDescriptorFactory.FromResource(Resources.GetIdentifier("ic_local_gas_station_black_24dp", "drawable", this.PackageName)));
                        break;
                }
                map.AddMarker(marker);
            }

        }

        private void GetLocationCoordinates()
        {
            try
            {
                Geocoder geo = new Geocoder(this);
                foreach (var loc in Database.Instance.Locations)
                {
                    var address =
                            geo.GetFromLocationName(
                                loc.Address.Streetname + "  " + loc.Address.Housenumber + ", " + loc.Address.City + ", " +
                                loc.Address.Country + ", " + loc.Address.Country, 1);
                    if (address[0] != null)
                    {
                        loc.Latitude = address[0].Latitude;
                        loc.Longitude = address[0].Longitude;
                    }
                }
            }
            catch (Exception ex)
            {
                ShowAlertDialog("Error", "There was an error while getting Location coordinates! Reason: " + ex.Message, "OK");
            }
        }

        public override void OnBackPressed()
        {
            locMgr.RemoveUpdates(this);
            Finish();
        }

        public void OnLocationChanged(Location location)
        {
            currentLocation = location;
        }

        public void OnProviderDisabled(string provider)
        {
            locMgr.RemoveUpdates(this);
        }

        public void OnProviderEnabled(string provider)
        {
            locMgr.RemoveUpdates(this);
            locMgr.RequestLocationUpdates(provider, 2000, 1, this);
        }

        public void OnStatusChanged(string provider, Availability status, Bundle extras)
        {
            if (status == Availability.Available)
            {
                locMgr.RemoveUpdates(this);
                locMgr.RequestLocationUpdates(provider, 2000, 1, this);
            }
            else
            {
                locMgr.RemoveUpdates(this);
            }
        }
        private void ShowAlertDialog(string title, string message, string posBtnMsg)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.SetTitle(title);
            builder.SetMessage(message);
            builder.SetCancelable(false);
            builder.SetPositiveButton(posBtnMsg, delegate { });
            builder.Show();
        }

        public bool OnMarkerClick(Marker marker)
        {
            new AlertDialog.Builder(this)
            .SetPositiveButton("Yes", async (sender, args) =>
                {
                    await SpotyService.GetRatings();
                    Database.Instance.GetLocationFromName(marker.Title);
                    var activity = new Intent(this, typeof(LocationDetailActivity));
                    StartActivity(activity);
                })
            .SetNegativeButton("No", (sender, args) =>
            {
            })
            .SetMessage("Do you want to rate/show details of location: " + marker.Title + " ?")
            .SetTitle("Show Location Details?")
            .Show();
            return true;
        }
    }
}