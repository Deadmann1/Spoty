var express = require('express');
var router = express.Router();
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var City = require("../models/city.js");
var pool = require("../database/database.js");


router.get('/cities', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var cities = [];
        request = new Request("SELECT * FROM Spoty.City;", function (err) {
            if (err) {
                throw (err);
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
            cities.push(new City(result[0], result[1], result[2], result[3]));
            result = [];
        });
        request.on('doneInProc', function (rowCount, more, rows) {
            console.log(rowCount + ' rows returned');
            connection.release();
            res.type('application/json');
            res.send(cities);
        });
        connection.execSql(request);
    });

});

router.get('/cities/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var city;
        request = new Request("SELECT * FROM Spoty.City WHERE IdCity =" + req.params._id + ";", function (err) {
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
            city = new City(result[0], result[1], result[2], result[3]);
            connection.release();
            res.type('application/json');
            res.send(city);
        });
        connection.execSql(request);
    });
});

module.exports = router;
