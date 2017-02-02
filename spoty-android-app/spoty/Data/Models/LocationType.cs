using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace spoty.Data.Models
{
    class LocationType
    {
        public int IdLocationType { get; set; }
        public string LocationTypeName { get; set; }

        public LocationType(int idLocationType, string locationTypeName)
        {
            IdLocationType = idLocationType;
            LocationTypeName = locationTypeName;
        }

        public override string ToString()
        {
            return $"{LocationTypeName}";
        }
    }
}