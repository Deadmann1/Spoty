var method = Address.prototype;

function Address(IdAddress, StreetName, HouseNumber,IdCity) {
    this.IdAddress = IdAddress;
    this.StreetName = StreetName;
    this.HouseNumber = HouseNumber;
    this.IdCity = IdCity;
}

module.exports = Address;