var method = Address.prototype;

function Address(IdAddress, StreetName, HouseNumber  ) {
    this.IdAddress = IdAddress;
    this.StreetName = StreetName;
    this.HouseNumber = HouseNumber;
}

module.exports = Address;