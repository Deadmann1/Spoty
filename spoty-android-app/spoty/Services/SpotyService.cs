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
using Spoty.Data;
using Spoty.Data.Models;

namespace spoty.Services
{
    internal static class SpotyService
    {
        private static string ServiceBaseUrl = "http://spotyweb-backend.azurewebsites.net/";
        private static string SpotyServiceUrl = ServiceBaseUrl + "/api";

        public static bool Login(User user)
        {
            bool retVal = false;
            if (user.Id != 1)
            {
                /*
                 * Proper User Login will be added in Sprint 2, meanwhile only the guest account is used, so we know the fixed id and everything means no need to even call the service
                 * 
                RestRequest request = new RestRequest("/auth", Method.POST);
                request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
                request.AddParameter("username", user.username, ParameterType.GetOrPost);
                request.AddParameter("password", AuthHelper.GenerateHash(user.password, challenge), ParameterType.GetOrPost);
                //Execute async for perfomance
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception();
                }
                else
                {
                    var d = JsonConvert.DeserializeObject<TokenResponse>(restResponse.Content);
                    return d.token;
                }
                retVal = ...
                */
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

        public static async void SendFeedback(int rbGradeNumStars, string txtFeedbackText)
        {
            try
            {
                RestClient myRestClient = new RestClient(SpotyServiceUrl);
                RestRequest request = new RestRequest("/ratings", Method.POST);
                //Execute async for perfomance
                request.AddHeader("Content-Type", "application/json");
                request.RequestFormat = DataFormat.Json;
                var jsonString =
                    JsonConvert.SerializeObject(new Rating(rbGradeNumStars, txtFeedbackText,
                        Database.Instance.CurrentLocation.Id, Database.Instance.CurrentLocation.Id)).ToString();
                request.AddBody(jsonString);
                var restResponse = await myRestClient.ExecuteTaskAsync(request);
                if (restResponse.StatusCode != System.Net.HttpStatusCode.OK)
                {
                    throw new Exception();
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