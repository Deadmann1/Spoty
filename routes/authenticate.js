var express = require('express');
var router = express.Router();
var app = require('../app');
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var UserAccount = require("../models/userAccount.js");
var pool = require("../database/database.js");
var jwt = require("jsonwebtoken");
var config = require('../config');

router.post('/authenticate', function (req, res) {

    var username = req.body.username;
    var password = req.body.password;

    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var user;
        request = new Request("SELECT * FROM Spoty.UserAccount WHERE Username = @Username;", function (err) {
            if (err) {
                next(err)
            }
        });
        if (!username || !password) {
            res.status(400).send('No username or password');
            return;
        }
        request.addParameter('Username', types.NVarChar, username);
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
            user = (new UserAccount(result[0], result[1], result[2], result[3], result[4], result[5], result[6]));
            connection.release();
            if (err) throw err;
            if (!user) {
                res.status(400).send('Authentication failed. User not found.');
                return;
            } else if (user) {

                // check if password matches
                if (user.Password != req.body.password) {
                    res.status(400).send('Authentication failed. Wrong password.');
                    return;
                } else {

                    // if user is found and password is right
                    // create a token
                    var token = jwt.sign(user, config.secret);

                    // return the information including token as JSON
                    res.json({                      
                        token: token
                    });
                }
            }
        });
        connection.execSql(request);
    });
});


module.exports = router;
