namespace Spoty.Data.Models
{
    public class User
    {
        public int IdUserAccount { get; }
        public string Username { get; }
        public string Password { get; }

        public User(int idUserAccount, string username, string password)
        {
            this.IdUserAccount = idUserAccount;
            this.Username = username;
            this.Password = password;
        }

        protected bool Equals(User other)
        {
            return string.Equals(Username, other.Username) && string.Equals(Password, other.Password);
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != this.GetType()) return false;
            return Equals((User) obj);
        }

        public override int GetHashCode()
        {
            unchecked
            {
                return ((Username != null ? Username.GetHashCode() : 0)*397) ^ (Password != null ? Password.GetHashCode() : 0);
            }
        }
    }
}