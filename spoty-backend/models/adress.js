var method = Adress.prototype;

function Adress(IdAdress, StreetName, HouseNumber  ) {
    this.IdAdress = IdAdress;
    this.StreetName = StreetName;
    this.HouseNumber = HouseNumber;
}

module.exports = Adress;