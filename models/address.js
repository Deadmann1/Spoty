var method = Address.prototype;

function Address(IdAddress,IdCity, StreetName, HouseNumber) {
    this.IdAddress = IdAddress;
    this.IdCity = IdCity;
    this.StreetName = StreetName;
    this.HouseNumber = HouseNumber;
}

module.exports = Address;