var method = Location.prototype;

function Location(IdLocation, LocationName, IdLocationType, IdAddress ) {
    this.IdLocation = IdLocation;
    this.LocationName = LocationName;
    this.IdLocationType = IdLocationType;
    this.IdAddress = IdAddress;
}

module.exports = Location;