var express = require('express');
var router = express.Router();
var app = require('../app');
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var UserAccount = require("../models/userAccount.js");
var pool = require("../database/database.js");


router.get('/users', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
    var result = [];
    var users = [];
    request = new Request("SELECT * FROM Spoty.UserAccount;", function (err) {
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
        users.push(new UserAccount(result[0],result[1],result[2],result[3],result[4],result[5],result[6]));
        result = [];
    });
    request.on('doneInProc', function (rowCount, more, rows) {
        console.log(rowCount + ' rows returned');
        connection.release();
        res.type('application/json');  
        res.send(users);
    });
    connection.execSql(request);
    });
});

router.get('/users/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
    var result = [];
    var user;
    request = new Request("SELECT * FROM Spoty.UserAccount WHERE IdUserAccount =" + req.params._id + ";", function (err) {
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
        user = (new UserAccount(result[0],result[1],result[2],result[3],result[4],result[5],result[6]));
        connection.release();
        res.type('application/json');  
        res.send(user);
    });
    connection.execSql(request);
    });
});

module.exports = router;
