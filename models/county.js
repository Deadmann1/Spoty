var method = County.prototype;

function County(IdCounty, CountyName, IdCountry) {
    this.IdCounty = IdCounty;
    this.CountyName = CountyName;
    this.IdCountry = IdCountry;
}

module.exports = County;