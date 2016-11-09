var express = require('express');
var router = express.Router();
var app = require('../app');
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var Location = require("../models/location.js");
var connection = require("../database/database.js");


router.get('/locations', function (req, res, next) {
    var result = [];
    var locations = [];
    request = new Request("SELECT * FROM Spoty.Location;", function (err) {
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
        locations.push(new Location(result[0],result[1],result[2],result[3]));
        result = [];
    });
    request.on('doneInProc', function (rowCount, more, rows) {
        console.log(rowCount + ' rows returned');
        res.type('application/json');  
        res.send(locations);
    });
    connection.execSql(request);
    
});

router.get('/locations/:_id', function (req, res, next) {
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
        if(!result) {
            next(err)
        }
        location = new Location(result[0],result[1],result[2],result[3]);
        res.type('application/json');  
        res.send(location);
    });
    connection.execSql(request);
});

module.exports = router;
