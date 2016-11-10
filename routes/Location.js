var express = require('express');
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var Location = require("../models/location.js");
var pool = require("../database/database.js");
var router = express.Router();


router.get('/locations', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var locations = [];
        request = new Request("SELECT * FROM Spoty.Location;", function (err) {
            if (err) {
                next(err)
            }
        });
        request.on('row', function (columns) {
            columns.forEach(function (column) {
                if (column.value === null) {
                    result.push('NULL');
                } else {
                    result.push(column.value);
                }
            });
            console.log(result);
            locations.push(new Location(result[0], result[1], result[2], result[3]));
            result = [];
        });
        request.on('doneInProc', function (rowCount, more, rows) {
            console.log(rowCount + ' rows returned');
            connection.release();
            res.type('application/json');
            res.send(locations);
        });
        connection.execSql(request);
    });
});

router.get('/locations/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var location;
        request = new Request("SELECT * FROM Spoty.Location WHERE IdLocation =" + req.params._id + ";", function (err) {
            if (err) {
                next(err)
            }
        });
        request.on('row', function (columns) {
            columns.forEach(function (column) {
                if (column.value === null) {
                    result.push('NULL');
                } else {
                    result.push(column.value);
                }
            });
            console.log(result);
            if (!result) {
                next(err)
            }
            location = new Location(result[0], result[1], result[2], result[3]);
            connection.release();
            res.type('application/json');
            res.send(location);
        });
        connection.execSql(request);
    });
});

router.post('/locations', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var location = req.body;
        request = new Request("INSERT Spoty.Location (IdLocation, LocationName, IdLocationType, IdAddress) VALUES (@IdLocation, @LocationName, @IdLocationType, @IdAddress);", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdLocation', types.Int,  location.IdLocation);
        request.addParameter('LocationName', types.NVarChar,  location.LocationName);
        request.addParameter('IdLocationType', types.Int,  location.IdLocationType);
        request.addParameter('IdAddress', types.Int,  location.IdAddress);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Location successfully added'});
        });
        connection.execSql(request);
    });
});

router.delete('/locations/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        request = new Request("DELETE FROM Spoty.Location WHERE IdLocation =" + req.params._id + ";", function (err) {
            if (err) {
                next(err)
            }
        });
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Location successfully deleted'});
        });
        connection.execSql(request);
    });
});

router.put('/locations/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var location = req.body;
        request = new Request("UPDATE Spoty.Location SET LocationName = @LocationName, IdLocationType = @IdLocationType, IdAddress = @IdAddress WHERE IdLocation = @IdLocation;", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdLocation', types.Int,  location.IdLocation);
        request.addParameter('LocationName', types.NVarChar,  location.LocationName);
        request.addParameter('IdLocationType', types.Int,  location.IdLocationType);
        request.addParameter('IdAddress', types.Int,  location.IdAddress);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Location successfully updated'});
        });
        connection.execSql(request);
    });
});

module.exports = router;
