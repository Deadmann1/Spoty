using Newtonsoft.Json;

namespace Spoty.Data.Models
{
    public class County
    {
        [JsonProperty("IdCounty")]
        public int Id { get; }
        [JsonProperty("CountyName")]
        public string Name { get; }
        [JsonProperty("IdCountry")]
        public int IdCountry { get; }

        public County(int id, string name, int idCountry)
        {
            this.Id = id;
            this.Name = name;
            this.IdCountry = idCountry;
        }
    }
}