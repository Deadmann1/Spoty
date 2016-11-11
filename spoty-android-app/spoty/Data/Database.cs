using System.Collections.Generic;
using Android.Locations;
using Spoty.Data.Models;

namespace Spoty.Data
{
    class Database
    {
        /*
         * Als Zwischenspeicher für Daten gedacht
         */
        private static Database instance;
        public User CurrentUser { get; set; }
        public List<Address> Addresses { get; set; } 

        private Database() { }

        public static Database Instance => instance ?? (instance = new Database());
    }
}