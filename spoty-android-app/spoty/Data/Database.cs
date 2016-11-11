using System.Collections.Generic;
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
        public Location CurrentLocation { get; set; }
        public List<Address> Addresses { get; set; }
        public List<Location> Locations { get; set; }
        public List<City> Cities { get; set; }
        public List<County> Counties { get; set; }
        public List<Country> Countries { get; set; }
        public List<Rating> Ratings { get; set; }


        private Database() { }

        public static Database Instance => instance ?? (instance = new Database());
    }
}