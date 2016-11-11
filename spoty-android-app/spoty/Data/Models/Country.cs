using Newtonsoft.Json;

namespace Spoty.Data.Models
{
    public class Country
    {
        [JsonProperty("IdCountry")]
        public int Id { get; }

        [JsonProperty("CountryName")]
        public string Name { get; }

        public Country(int id, string name)
        {
            this.Id = id;
            this.Name = name;
        }
    }
}