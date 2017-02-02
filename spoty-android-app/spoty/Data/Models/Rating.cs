using Newtonsoft.Json;

namespace Spoty.Data.Models
{
    public class Rating
    {
        [JsonProperty("Grade")]
        public int Grade { get; }
        [JsonProperty("Feedback")]
        public string Feedback { get; }
        [JsonProperty("IdUserAccount")]
        public int IdUserAccount { get; }
        [JsonProperty("IdLocation")]
        public int IdLocation { get; }

        public Rating(int grade, string feedback, int idUserAccount, int idLocation)
        {
            this.Grade = grade;
            this.Feedback = feedback;
            this.IdUserAccount = idUserAccount;
            this.IdLocation = idLocation;
        }

        public override string ToString()
        {
            return "Note: " + Grade + " | " + Feedback;
        }
    }
}