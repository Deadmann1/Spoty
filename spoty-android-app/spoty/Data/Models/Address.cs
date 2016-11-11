using Newtonsoft.Json;

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


        public Address(int id, int idCity, string streetname, int housenumber)
        {
            this.Id = id;
            this.Housenumber = housenumber;
            this.Streetname = streetname;
            this.IdCity = idCity;
        }
    }
}