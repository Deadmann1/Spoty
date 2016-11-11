using Android.Locations;

namespace Spoty.Data
{
    public class Location
    {
        public int Id { get; }
        public string Name { get; }
        public string Type { get; }
        public Address Address { get; }

        public Location(int id, string name, string type, Address address)
        {
            this.Id = id;
            this.Name = name;
            this.Type = type;
            this.Address = address;
        }
    }
}