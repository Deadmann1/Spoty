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
            var toolbar = FindViewById<Toolbar>(Resource.Id.toolbar1);
            SetActionBar(toolbar);
            ActionBar.Title = "Location Overview";
            FindAllViewsById();
            SetEventHandlers();
            FillList();
            Database.Instance.lvLocationOverview = listLocations;
        }
        public override bool OnCreateOptionsMenu(IMenu menu)
        {
            MenuInflater.Inflate(Resource.Menu.top_menus, menu);
            return base.OnCreateOptionsMenu(menu);
        }
        public override bool OnOptionsItemSelected(IMenuItem item)
        {

            if (Database.Instance.Locations != null)
            {
                if (item.TitleFormatted.ToString() == "Set Filter")
                {
                    var activity = new Intent(this, typeof(LocationFilterActivity));
                    StartActivity(activity);
                }
                else
                {
                    var activity = new Intent(this, typeof(LocationMapActivity));
                    StartActivity(activity);
                }
            }
            else
            {
                ShowAlertDialog("Error!", "Please wait for locations to load before selecting an action!", "OK");
            }

            return base.OnOptionsItemSelected(item);
        }

        private async Task GetRatings()
        {
            await SpotyService.GetRatings();
        }

        private async Task FillList()
        {
            await SpotyService.GetLocations();
            ArrayAdapter<Location> adapter = new ArrayAdapter<Location>(this, Android.Resource.Layout.SimpleListItem1, Database.Instance.Locations);
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
        private void ShowAlertDialog(string title, string message, string posBtnMsg)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.SetTitle(title);
            builder.SetMessage(message);
            builder.SetCancelable(false);
            builder.SetPositiveButton(posBtnMsg, delegate { });
            builder.Show();
        }
    }
}