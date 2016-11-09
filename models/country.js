var method = Country.prototype;

function Country(IdCountry, CountryName) {
    this.IdCountry = IdCountry;
    this.CountryName = CountryName;
}

module.exports = Country;