using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using IComparable = Java.Lang.IComparable;
using Object = Java.Lang.Object;

namespace Spoty.Data.Models
{
    public class Address
    {
        [JsonProperty("IdAddress")]
        public int Id { get; }
        [JsonProperty("HouseNumber")]
        public int Housenumber { get; }
        [JsonProperty("StreetName")]
        public string Streetname { get; }
        public string City { get; set; }
        public string County { get; set; }
        public string Country { get; set; }
        [JsonProperty("IdCity")]
        public int IdCity { get; }

        [JsonConstructor]
        public Address(int id, int idCity, string streetname, int housenumber)
        {
            this.Id = id;
            this.Housenumber = housenumber;
            this.Streetname = streetname;
            this.IdCity = idCity;
        }

        public Address(string city, string county, string country)
        {
            City = city;
            County = county;
            Country = country;
        }

        protected bool Equals(Address other)
        {
            return string.Equals(City, other.City) && string.Equals(County, other.County) && string.Equals(Country, other.Country);
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != this.GetType()) return false;
            return Equals((Address) obj);
        }

        public override int GetHashCode()
        {
            unchecked
            {
                var hashCode = (City != null ? City.GetHashCode() : 0);
                hashCode = (hashCode*397) ^ (County != null ? County.GetHashCode() : 0);
                hashCode = (hashCode*397) ^ (Country != null ? Country.GetHashCode() : 0);
                return hashCode;
            }
        }
    }
}