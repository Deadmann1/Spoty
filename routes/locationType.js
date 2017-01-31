var express = require('express');
var router = express.Router();
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var LocationType= require("../models/locationType.js");
var pool = require("../database/database.js");


router.get('/locationTypes', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
    var result = [];
    var locationTypes = [];
    request = new Request("SELECT * FROM Spoty.LocationType;", function (err) {
        if (err) {
            throw(err);
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
        locationTypes.push(new LocationType(result[0],result[1]));
        result = [];
    });
    request.on('doneInProc', function (rowCount, more, rows) {
        console.log(rowCount + ' rows returned');
        connection.release();
        res.type('application/json');  
        res.send(locationTypes);
    });
    connection.execSql(request);
    });
});

router.get('/locationTypes/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
    var result = [];
    var locationType;
    request = new Request("SELECT * FROM Spoty.LocationType WHERE IdLocationType =" + req.params._id + ";", function (err) {
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
        if(!result) {
            next(err)
        }
        locationType = new LocationType(result[0],result[1]);
        connection.release();
        res.type('application/json');  
        res.send(locationType);
    });
    connection.execSql(request);
    });
});

router.get('/locationTypes/new/id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        request = new Request("Select MAX(IdLocationType) from Spoty.LocationType;", function (err) {
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
        });
        request.on('doneInProc', function (rowCount, more, rows) {
            console.log(rowCount + ' rows returned');
            connection.release();
            res.type('application/json');
            var ret = "";
            ret = {"Id": result[0]+1};
            if(ret.length == 0 ){
                res.sendStatus(204);
            }
            else {
                res.send(ret);
            }
        });
        connection.execSql(request);
    });
});

router.post('/locationTypes', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var locationType = req.body;
        request = new Request("INSERT Spoty.LocationType (IdLocationType, LocationTypeName) VALUES (@IdLocationType, @LocationTypeName);", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdLocationType', types.Int,  locationType.IdLocationType);
        request.addParameter('LocationTypeName', types.NVarChar,  locationType.LocationTypeName);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'LocationType successfully added'});
        });
        connection.execSql(request);
    });
});

router.delete('/locationTypes/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        request = new Request("DELETE FROM Spoty.LocationType WHERE IdLocationType =" + req.params._id + ";", function (err) {
            if (err) {
                next(err)
            }
        });
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'LocationType successfully deleted'});
        });
        connection.execSql(request);
    });
});

router.put('/locationTypes/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var locationType = req.body;
        request = new Request("UPDATE Spoty.Location SET LocationTypeName = @LocationTypeName WHERE IdLocationType = @IdLocationType;", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdLocationType', types.Int,  locationType.IdLocationType);
        request.addParameter('LocationTypeName', types.NVarChar,  locationType.LocationTypeName);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'LocationType successfully updated'});
        });
        connection.execSql(request);
    });
});
module.exports = router;
