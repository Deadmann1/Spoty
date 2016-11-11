namespace Spoty.Data.Models
{
    public class User
    {
        public int Id { get; }
        public string Username { get; }
        public string Password { get; }

        public User(int id, string username, string password)
        {
            this.Id = id;
            this.Username = username;
            this.Password = password;
        }
    }
}