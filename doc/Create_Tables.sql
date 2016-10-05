Drop table Permission Cascade Constraints;
Drop table UserAccount Cascade Constraints;
Drop table Setting Cascade Constraints;
Drop table Location Cascade Constraints;
Drop table Rating Cascade Constraints;
Drop table Address Cascade Constraints;
Drop table LocationType Cascade Constraints;

create table Permission
(
   accounttype Varchar2(100) NOT NULL,
   
   CONSTRAINT pkPermission PRIMARY KEY (accounttype)
);

create table UserAccount
(
  username  Varchar2(50) NOT NULL,
  password  Varchar2(50) NOT NULL,
  firstname Varchar2(30),
  lastname  Varchar2(30),
  birthdate Date,
  accounttype Varchar2(100) NOT NULL,
  
  CONSTRAINT pkUserAccount PRIMARY KEY(username),
  CONSTRAINT fkPermission FOREIGN KEY(accounttype) REFERENCES Permission (accounttype)
);

create table Setting
(
  type Varchar2(100),
  value Varchar2(100),
  username Varchar2(50),

  CONSTRAINT fkUserAccountSetting FOREIGN KEY(username) REFERENCES UserAccount(username),
  CONSTRAINT pkSetting PRIMARY KEY(username)
);

create table Location
(
  name Varchar2(50),
  
  CONSTRAINT pkLocation PRIMARY KEY(name)
);

create table Rating
(
  grade Integer NOT NULL,
  feedback Varchar2(100),
  username Varchar2(50) NOT NULL,
  locationname Varchar2(50),
  
  CONSTRAINT fkUserAccountRating FOREIGN KEY(username) REFERENCES UserAccount(username),
  CONSTRAINT fkLocation FOREIGN KEY(locationname) REFERENCES Location(name),
  CONSTRAINT pkRating PRIMARY KEY(username, locationname)
);


create table Address
(
  country Varchar2(30),
  county Varchar2(30),
  city Varchar2(30),
  street Varchar2(30),
  housenumber Integer,
  locationname Varchar2(50),
  
  CONSTRAINT pkAddress PRIMARY KEY(country,county,city,street,housenumber,locationname)
);

create table LocationType
(
  typename Varchar2(50),
  
  CONSTRAINT pkLocationType PRIMARY KEY(typename)
);


