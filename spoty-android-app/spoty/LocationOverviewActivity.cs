using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using spoty.Services;
using Spoty.Data;
using Spoty.Data.Models;

namespace spoty
{
    [Activity()]
    public class LocationOverviewActivity : Activity
    {
        private ListView listLocations;
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.LocationOverviewLayout);
            ActionBar.SetTitle(Resource.String.LocationOverviewLayoutTitle);
            FindAllViewsById();
            SetEventHandlers();
            FillList();
        }

        private async Task GetRatings()
        {
            await SpotyService.GetRatings();
        }

        private async Task FillList()
        {
            await SpotyService.GetLocations();
            ArrayAdapter<Location> adapter = new ArrayAdapter<Location>(this,Android.Resource.Layout.SimpleListItem1, Database.Instance.Locations);
            listLocations.Adapter = adapter;
        }

        private void SetEventHandlers()
        {
            listLocations.ItemClick += ListLocationsOnItemClick;
        }

        private async void ListLocationsOnItemClick(object sender, AdapterView.ItemClickEventArgs e)
        {
            await GetRatings();
            Database.Instance.CurrentLocation = Database.Instance.Locations[e.Position];
            var activity = new Intent(this, typeof(LocationDetailActivity));
            StartActivity(activity);
        }

        private void FindAllViewsById()
        {
            listLocations = FindViewById<ListView>(Resource.Id.listViewLocations);
        }

        public override void OnBackPressed()
        {
            Finish();
        }
    }
}