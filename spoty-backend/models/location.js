var method = Location.prototype;

function Location(IdLocation, Locationname, IdLocationType, IdAddress ) {
    this.IdLocation = IdLocation;
    this.Locationname = Locationname;
    this.IdLocationType = IdLocationType;
    this.IdAddress = IdAddress;
}

module.exports = Location;