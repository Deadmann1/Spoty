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
using spoty.Services;
using Spoty.Data;
using Spoty.Data.Models;

namespace spoty
{
    [Activity(Label = "LocationDetailActivity")]
    public class LocationDetailActivity : Activity
    {
        private TextView txtName;
        private TextView txtType;
        private TextView txtCity;
        private TextView txtStreet;
        private TextView txtCounty;
        private TextView txtCountry;
        private EditText txtFeedback;
        private RatingBar rbGrade;
        private Button btnRate;
        private ListView listRatings;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.LocationDetailLayout);
            ActionBar.SetTitle(Resource.String.LocationDetailLayoutTitle);
            FindAllViewsById();
            SetEventHandlers();
            FillList();
        }

        private void FillList()
        {
            try
            {
                Location l = Database.Instance.CurrentLocation;
                Address a = l.Address;
                txtName.Text = l.Name;
                txtType.Text = l.Type;
                txtCity.Text = a.City;
                txtStreet.Text = a.Streetname + "  " + a.Housenumber;
                txtCounty.Text = a.County;
                txtCountry.Text = a.Country;
                List<Rating> filteredRatings = Database.Instance.Ratings.Where(x => x.IdLocation == l.Id).ToList();
                ArrayAdapter<Rating> adapter = new ArrayAdapter<Rating>(this, Android.Resource.Layout.SimpleListItem1, filteredRatings);
                listRatings.Adapter = adapter;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        private void SetEventHandlers()
        {
            btnRate.Click += BtnRateOnClick;
        }

        private void BtnRateOnClick(object sender, EventArgs eventArgs)
        {
            if (!String.IsNullOrEmpty(txtFeedback.Text))
            {
                SpotyService.SendFeedback(rbGrade.NumStars, txtFeedback.Text);
                ShowAlertDialog("Success!", "You rated the location with: " + rbGrade.NumStars, "OK");
                Finish();
            }
            else
            {
                ShowAlertDialog("Warning!", "Please enter Feedback before rating!", "OK");
            }

        }

        private void FindAllViewsById()
        {
            txtName = FindViewById<TextView>(Resource.Id.textViewName);
            txtType = FindViewById<TextView>(Resource.Id.textViewType);
            txtCity = FindViewById<TextView>(Resource.Id.textViewCity);
            txtStreet = FindViewById<TextView>(Resource.Id.textViewStreet);
            txtCounty = FindViewById<TextView>(Resource.Id.textViewCounty);
            txtCountry = FindViewById<TextView>(Resource.Id.textViewCountry);
            listRatings = FindViewById<ListView>(Resource.Id.listViewRatings);
            txtFeedback = FindViewById<EditText>(Resource.Id.editTextFeedback);
            rbGrade = FindViewById<RatingBar>(Resource.Id.ratingBarLocation);
            btnRate = FindViewById<Button>(Resource.Id.buttonRate);
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