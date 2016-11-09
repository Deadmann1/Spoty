var express = require('express');
var router = express.Router();
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var AccountType = require("../models/accountType.js");
var pool = require("../database/database.js");


router.get('/accountTypes', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var accountTypes = [];
        request = new Request("SELECT * FROM Spoty.AccountType;", function (err) {
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
            accountTypes.push(new AccountType(result[0], result[1]));
            result = [];
        });
        request.on('doneInProc', function (rowCount, more, rows) {
            console.log(rowCount + ' rows returned');
            connection.release();
            res.type('application/json');
            res.send(accountTypes);
        });
        connection.execSql(request);
    });
});

router.get('/accountTypes/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
    var result = [];
    var accountTpye;
    request = new Request("SELECT * FROM Spoty.AccountType WHERE IdAccountType =" + req.params._id + ";", function (err) {
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
        accountTpye = new AccountType(result[0], result[1]);
        connection.release();
        res.type('application/json');
        res.send(accountTpye);
    });
    connection.execSql(request);
    });
});

module.exports = router;
