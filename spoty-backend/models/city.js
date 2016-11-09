var method = City.prototype;

function City(IdCity,PostalCode, CityName, IdCounty ) {
    this.IdCity = IdCity;
    this.PostalCode = PostalCode;
    this.CityName = CityName;
    this.IdCounty = IdCounty;
}

module.exports = City;