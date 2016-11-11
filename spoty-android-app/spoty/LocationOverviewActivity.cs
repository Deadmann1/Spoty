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

        private void FillList()
        {
            //listLocations.
        }

        private void SetEventHandlers()
        {
            listLocations.ItemSelected += ListLocationsOnItemSelected;
        }

        private void ListLocationsOnItemSelected(object sender, AdapterView.ItemSelectedEventArgs itemSelectedEventArgs)
        {
        }

        private void FindAllViewsById()
        {
            listLocations = FindViewById<ListView>(Resource.Id.listViewLocations);
        }
    }
}