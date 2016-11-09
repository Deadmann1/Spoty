var method = Location.prototype;

function Location(IdLocation, Locationame, IdLocationType, IdAdress ) {
    this.IdLocation = IdLocation;
    this.Locationame = Locationame;
    this.IdLocationType = IdLocationType;
    this.IdAdress = IdAdress;
}

module.exports = Location;