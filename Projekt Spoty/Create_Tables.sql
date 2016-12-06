Drop table AccountType Cascade Constraints;
Drop table UserAccount Cascade Constraints;
Drop table Country Cascade Constraints;
Drop table County Cascade Constraints;
Drop table City Cascade Constraints;
Drop table Address Cascade Constraints;
Drop table LocationType Cascade Constraints;
Drop table Location Cascade Constraints;
Drop table Rating Cascade Constraints;


create table AccountType
(
   idAccountType Integer,
   accounttype Varchar2(100),
   
   CONSTRAINT pkPermission PRIMARY KEY (idAccountType)
);

create table UserAccount
(
  idUserAccount Integer NOT NULL,
  username      Varchar2(50) NOT NULL,
  password      Varchar2(50) NOT NULL,
  firstname     Varchar2(30),
  lastname      Varchar2(30),
  birthdate     Date,
  idAccountType   Integer,
  
  CONSTRAINT pkUserAccount PRIMARY KEY(idUserAccount),
  CONSTRAINT fkPermission FOREIGN KEY(idAccountType) REFERENCES AccountType (idAccountType)
);

create table Country
(
  idCountry Integer,
  countryname Varchar2(30),
  
  CONSTRAINT pkCountry PRIMARY KEY(idCountry)
);

create table County
(
  idCounty Integer,
  countyname Varchar2(30),
  idCountry Integer,
  
  CONSTRAINT pkCounty PRIMARY KEY(idCounty),
  CONSTRAINT fkCounty FOREIGN KEY(idCountry) REFERENCES Country(idCountry)
);

create table City
(
  idCity Integer,
  postalcode Integer,
  cityname Varchar2(30),
  idCounty Integer,
  
  CONSTRAINT pkCity PRIMARY KEY(idCity),
  CONSTRAINT fkCityCounty FOREIGN KEY(idCounty) REFERENCES County(idCounty)
);

create table Address
(
  idAddress Integer,
  idCity Integer,
  streetname Varchar2(50),
  housenumber Integer,
  
  CONSTRAINT pkAddress PRIMARY KEY(idAddress),
  CONSTRAINT fkCity FOREIGN KEY(idCity) REFERENCES City(idCity)
);

create table LocationType
(
  idType Integer,
  locationtypename Varchar2(50),
  
  CONSTRAINT pkLocationType PRIMARY KEY(idType)
);

create table Location
(
  idLocation Integer,
  locationname Varchar2(50),
  idType Integer,
  idAdress Integer,
  
  CONSTRAINT pkLocation PRIMARY KEY(idLocation),
  CONSTRAINT fkLocationType FOREIGN KEY(idType) REFERENCES LocationType(idType),
  CONSTRAINT fkIPAddress FOREIGN KEY(idAdress) REFERENCES Address(idAddress)
);

create table Rating
(
  grade Integer NOT NULL,
  feedback Varchar2(100),
  idUserAccount Integer,
  idLocation Integer,
  
  CONSTRAINT fkUserAccountRating FOREIGN KEY(idUserAccount) REFERENCES UserAccount(idUserAccount),
  CONSTRAINT fkLocation FOREIGN KEY(idLocation) REFERENCES Location(idLocation),
  CONSTRAINT pkRating PRIMARY KEY(idUserAccount, idLocation)
);












