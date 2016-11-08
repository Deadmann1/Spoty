var express = require('express');
var router = express.Router();
var app = require('../app');
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var Location = require("../models/location.js");


router.get('/locations', function (req, res, next) {

    var conn = req.app.get('connection');
    var result = [];
    var locations = [];
    request = new Request("SELECT * FROM Spoty.Location;", function (err) {
        if (err) {
            console.log(err);
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
    conn.execSql(request);
    
});

router.get('/locations/:_id', function (req, res, next) {
    res.send("WOW : " + req.params._id);
});

module.exports = router;
