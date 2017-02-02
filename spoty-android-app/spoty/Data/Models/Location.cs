
using Newtonsoft.Json;
using Spoty.Data.Models;

namespace Spoty.Data.Models
{
    public class Location
    {
        [JsonProperty("IdLocation")]
        public int Id { get; }
        [JsonProperty("LocationName")]
        public string Name { get; }
        [JsonProperty("IdLocationType")]
        public int IdType { get; set; }
        public string Type { get; set; }
        [JsonProperty("IdAddress")]
        public int IdAddress { get; set; }
        public Address Address { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }

        public Location(int id, string name, int idAddress, int idType)
        {
            this.Id = id;
            this.Name = name;
            this.IdAddress = idAddress;
            this.IdType = idType;
        }

        public override string ToString()
        {
            return Name + "      " + Address.City;
        }
    }
}