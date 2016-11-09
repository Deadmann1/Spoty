var express = require('express');
var router = express.Router();
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var LocationType= require("../models/locationType.js");
var connection = require("../database/database.js");


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
        locationTypes.push(new LocationTyp(result[0],result[1]));
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

module.exports = router;
