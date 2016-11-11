using Newtonsoft.Json;

namespace Spoty.Data.Models
{
    public class City
    {
        [JsonProperty("IdCity")]
        public int Id { get; }
        [JsonProperty("PostalCode")]
        public int PostalCode { get; }
        [JsonProperty("CityName")]
        public string Name { get; }
        [JsonProperty("IdCounty")]
        public int IdCounty { get; }

        public City(int id, int postalCode, string cityName, int idCounty)
        {
            this.Id = id;
            this.PostalCode = postalCode;
            this.Name = cityName;
            this.IdCounty = idCounty;
        }
    }
}