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
using Newtonsoft.Json;
using RestSharp;
using spoty.Data.Models;
using Spoty.Data;
using Spoty.Data.Models;

namespace spoty.Services
{
    internal static class SpotyService
    {
        private static string ServiceBaseUrl = "http://spotyweb-backend.azurewebsites.net/";
        private static string SpotyServiceUrl = ServiceBaseUrl + "/api";

        public static async Task<bool> Login(User user)
        {
            bool retVal = false;
            if (user.IdUserAccount != 1)
            {
                RestRequest request = new RestRequest("/users", Method.GET);
                request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
                //Execute async for perfomance
                RestClient myRestClient = new RestClient(SpotyServiceUrl);
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception();
                }
                else
                {
                    List<User> users = JsonConvert.DeserializeObject<List<User>>(restResponse.Content);
                    if (users.Contains(user))
                    {
                        retVal = true;
                    }
                }
            }
            else
            {
                retVal = true;
                Database.Instance.CurrentUser = user;
            }
            return retVal;
        }

        public static async Task GetLocations()
        {
            try
            {
                await GetAddresses();
                await GetLocationTypes();
                RestClient myRestClient = new RestClient(SpotyServiceUrl);
                RestRequest request = new RestRequest("/locations", Method.GET);
                //Execute async for perfomance
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception();
                }
                else
                {
                    List<Location> list = JsonConvert.DeserializeObject<List<Location>>(restResponse.Content);
                    foreach (Location l in list)
                    {
                        l.Address = Database.Instance.Addresses.Find(x => x.Id == l.IdAddress);
                        l.Type = Database.Instance.LocationTypes.Find(x => x.IdLocationType == l.IdType).LocationTypeName;
                    }
                    Database.Instance.Locations = list;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private static async Task GetAddresses()
        {
            try
            {
                await GetCities();
                RestClient myRestClient = new RestClient(SpotyServiceUrl);
                RestRequest request = new RestRequest("/addresses", Method.GET);
                //Execute async for perfomance
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception();
                }
                else
                {
                    List<Address> list = JsonConvert.DeserializeObject<List<Address>>(restResponse.Content);
                    foreach (Address ad in list)
                    {
                        City city = Database.Instance.Cities.Find(x => x.Id == ad.IdCity);
                        County county = Database.Instance.Counties.Find(x => x.Id == city.IdCounty);
                        Country country = Database.Instance.Countries.Find(x => x.Id == county.IdCountry);
                        ad.City = city.Name;
                        ad.County = county.Name;
                        ad.Country = country.Name;
                    }
                    Database.Instance.Addresses = list;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private static async Task GetCities()
        {
            
            try
            {
                await GetCounties();
                RestClient myRestClient = new RestClient(SpotyServiceUrl);
                RestRequest request = new RestRequest("/cities", Method.GET);
                //Execute async for perfomance
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception();
                }
                else
                {
                    List<City> list = JsonConvert.DeserializeObject<List<City>>(restResponse.Content);
                    Database.Instance.Cities = list;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private static async Task GetCounties()
        {
            try
            {
                await GetCountries();
                RestClient myRestClient = new RestClient(SpotyServiceUrl);
                RestRequest request = new RestRequest("/counties", Method.GET);
                //Execute async for perfomance
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception();
                }
                else
                {
                    List<County> list = JsonConvert.DeserializeObject<List<County>>(restResponse.Content);
                    Database.Instance.Counties = list;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private static async Task GetCountries()
        {
            try
            {
                RestClient myRestClient = new RestClient(SpotyServiceUrl);
                RestRequest request = new RestRequest("/countries", Method.GET);
                //Execute async for perfomance
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception();
                }
                else
                {
                    List<Country> list = JsonConvert.DeserializeObject<List<Country>>(restResponse.Content);
                    Database.Instance.Countries = list;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        public static async Task GetRatings()
        {
            try
            {
                RestClient myRestClient = new RestClient(SpotyServiceUrl);
                RestRequest request = new RestRequest("/ratings", Method.GET);
                //Execute async for perfomance
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception();
                }
                else
                {
                    List<Rating> list = JsonConvert.DeserializeObject<List<Rating>>(restResponse.Content);
                    Database.Instance.Ratings = list;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private static async Task GetLocationTypes()
        {

            try
            {
                await GetCounties();
                RestClient myRestClient = new RestClient(SpotyServiceUrl);
                RestRequest request = new RestRequest("/locationTypes", Method.GET);
                //Execute async for perfomance
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception();
                }
                else
                {
                    List<LocationType> list = JsonConvert.DeserializeObject<List<LocationType>>(restResponse.Content);
                    Database.Instance.LocationTypes = list;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        public static async void SendFeedback(int rbGradeNumStars, string txtFeedbackText)
        {
            try
            {
                int rating = 0;
                switch (rbGradeNumStars)
                {
                    case 1:
                        rating = 5;
                        break;
                    case 2:
                        rating = 4;
                        break;
                    case 3:
                        rating = 3;
                        break;
                    case 4:
                        rating = 2;
                        break;
                    case 5:
                        rating = 1;
                        break;
                }

                RestClient myRestClient = new RestClient(SpotyServiceUrl);
                RestRequest request = new RestRequest("/ratings", Method.POST);
                //Execute async for perfomance
                request.AddHeader("Content-Type", "application/json");
                request.RequestFormat = DataFormat.Json;
                request.AddBody(new Rating(rating, txtFeedbackText, Database.Instance.CurrentUser.IdUserAccount, Database.Instance.CurrentLocation.Id));
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception("Error message: " + restResponse.ErrorMessage + "  Response Content: " + restResponse.Content);
                }
                else
                {
                    Console.WriteLine("Post rating worked");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }

        }
    }
}