var express = require('express');
var router = express.Router();
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var Adress = require("../models/adress.js");
var pool = require("../database/database.js");


router.get('/adresses', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var adresses = [];
        request = new Request("SELECT * FROM Spoty.Adress;", function (err) {
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
            adresses.push(new Adress(result[0], result[1], result[2], result[3]));
            result = [];
        });
        request.on('doneInProc', function (rowCount, more, rows) {
            console.log(rowCount + ' rows returned');
            connection.release();
            res.type('application/json');
            res.send(adresses);
        });
        connection.execSql(request);
    });
});

router.get('/adresses/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var adress;
        request = new Request("SELECT * FROM Spoty.Adress WHERE IdAdress =" + req.params._id + ";", function (err) {
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
            adress = new Adress(result[0], result[1], result[2], result[3]);
            connection.release();
            res.type('application/json');
            res.send(adress);
        });
        connection.execSql(request);
    });
});

module.exports = router;
