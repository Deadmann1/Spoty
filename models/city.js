var method = City.prototype;

function City(IdCity, CityName, PostalCode, IdCounty ) {
    this.IdCity = IdCity;
    this.CityName = CityName;
    this.PostalCode = PostalCode;
    this.IdCounty = IdCounty;
}

module.exports = City;