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
    }
}