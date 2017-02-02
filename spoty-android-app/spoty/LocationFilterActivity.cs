using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Newtonsoft.Json.Converters;
using spoty.Data.Models;
using Spoty.Data;
using Spoty.Data.Models;

namespace spoty
{
    [Activity(Label = "LocationFilterActivity")]
    public class LocationFilterActivity : Activity
    {
        private Spinner _locationTypesSpinner;
        private Spinner _countriesSpinner;
        private Spinner _countiesSpinner;
        private Spinner _citiesSpinner;
        private Button _setFilterButton;
        private Button _resetFilterButton;
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.LocationFilterLayout);
            FindAllViewsById();
            FillSpinners();
            SetEventHandlers();
        }

        private void FillSpinners()
        {
            if (Database.Instance.Locations != null)
            {
                ArrayAdapter<LocationType> locationTypeAdapter = new ArrayAdapter<LocationType>(this, Android.Resource.Layout.SimpleListItem1,
                    Database.Instance.LocationTypes);
                _locationTypesSpinner.Adapter = locationTypeAdapter;
                ArrayAdapter<Country> countryAdapter = new ArrayAdapter<Country>(this, Android.Resource.Layout.SimpleListItem1,
                    Database.Instance.Countries);
                _countriesSpinner.Adapter = countryAdapter;
                ArrayAdapter<County> countyAdapter = new ArrayAdapter<County>(this, Android.Resource.Layout.SimpleListItem1,
                    Database.Instance.Counties);
                _countiesSpinner.Adapter = countyAdapter;
                ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(this, Android.Resource.Layout.SimpleListItem1,
                    Database.Instance.Cities);
                _citiesSpinner.Adapter = cityAdapter;
            }
            else
            {
                ShowAlertDialog("Error!", "No Locations loaded yet!", "OK");
                Finish();
            }
        }

        private void SetEventHandlers()
        {
            _setFilterButton.Click += _setFilterButton_Click;
            _resetFilterButton.Click += _resetFilterButton_Click;
        }

        private void _resetFilterButton_Click(object sender, EventArgs e)
        {
            ResetSpinners();
            ArrayAdapter<Location> adapter = new ArrayAdapter<Location>(this, Android.Resource.Layout.SimpleListItem1, Database.Instance.Locations);
            Database.Instance.lvLocationOverview.Adapter = adapter;
            Finish();
        }

        private void _setFilterButton_Click(object sender, EventArgs e)
        {
            List<Location> filteredLocations = GetFilteredLocations();
            ArrayAdapter<Location> adapter = new ArrayAdapter<Location>(this, Android.Resource.Layout.SimpleListItem1, filteredLocations);
            Database.Instance.lvLocationOverview.Adapter = adapter;
            Finish();
        }

        private List<Location> GetFilteredLocations()
        {
            string type = (string)_locationTypesSpinner.SelectedItem;
            string country = (string) _countriesSpinner.SelectedItem;
            string county = (string) _countiesSpinner.SelectedItem;
            string city = (string) _citiesSpinner.SelectedItem;
            Address address = new Address(city,county,country);
            List<Location> list1 = Database.Instance.Locations.FindAll(x => x.Type == type);
            List<Location> ret = list1.FindAll(x => x.Address.Equals(address));
            ShowAlertDialog("Success", ret.Count + " Locations were found using this filter!", "OK");
            return ret;
        }

        private void ResetSpinners()
        {
            _locationTypesSpinner.SetSelection(0);
            _countriesSpinner.SetSelection(0);
            _countiesSpinner.SetSelection(0);
            _citiesSpinner.SetSelection(0);
        }
        private void FindAllViewsById()
        {

            _locationTypesSpinner = FindViewById<Spinner>(Resource.Id.spinnerType);
            _countriesSpinner = FindViewById<Spinner>(Resource.Id.spinnerCountry);
            _countiesSpinner = FindViewById<Spinner>(Resource.Id.spinnerCounty);
            _citiesSpinner = FindViewById<Spinner>(Resource.Id.spinnerCity);
            _setFilterButton = FindViewById<Button>(Resource.Id.buttonSetFilter);
            _resetFilterButton = FindViewById<Button>(Resource.Id.buttonResetFilter);
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
        public override void OnBackPressed()
        {
            Finish();
        }
    }
}