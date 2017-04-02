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

outer.get('/users/newId', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
    var result = [];
    var newId;
    request = new Request("SELECT max(IdUserAccount) FROM Spoty.UserAccount;", function (err) {
        if (err) {
            next(err)
        }
    });
    request.on('row', function (columns) {
        columns.forEach(function (column) {
            if (column.value === null) {
                result.push('NULL');
            } else {
                result.push(column.value + 1);
            }
        });
        console.log(result);
        if(!result) {
            next(err)
        }
        newId = result[0];
        connection.release();
        res.type('text/plain');  
        res.send(newId);
    });
    connection.execSql(request);
    });
});
router.post('/users', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var user = req.body;
        request = new Request("INSERT INTO Spoty.UserAccount (IdUserAccount, Username, Password, Firstname, Lastname, Birthdate, IdAccountType) VALUES (?, ?, ?, ?, ?, ?, ?);", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdUserAccount', types.Int,  user.IdUserAccount);
        request.addParameter('Username', types.NVarChar,  location.Username);
        request.addParameter('Password', types.Int,  location.Password);
        request.addParameter('Firstname', types.Int,  location.Firstname);
        request.addParameter('Lastname', types.Int,  location.Lastname);
        request.addParameter('Birthdate', types.Int,  location.Birthdate);
        request.addParameter('IdAccountType', types.Int,  location.IdAccountType);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'User successfully added'});
        });
        connection.execSql(request);
    });
});

router.delete('/users/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        request = new Request("DELETE FROM Spoty.UserAccount  WHERE IdUserAccount =" + req.params._id + ";", function (err) {
            if (err) {
                next(err)
            }
        });
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'User successfully deleted'});
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
        var user = req.body;
        request = new Request("UPDATE Spoty.UserAccount SET Username = @Username, Password = @Password, Firstname = @Firstname, Lastname = @Lastname, Birthdate = @Birthdate, IdAccountType = @IdAccountType WHERE IdUserAccount = @IdUserAccount;", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdUserAccount', types.Int,  user.IdUserAccount);
        request.addParameter('Username', types.NVarChar,  location.Username);
        request.addParameter('Password', types.Int,  location.Password);
        request.addParameter('Firstname', types.Int,  location.Firstname);
        request.addParameter('Lastname', types.Int,  location.Lastname);
        request.addParameter('Birthdate', types.Int,  location.Birthdate);
        request.addParameter('IdAccountType', types.Int,  location.IdAccountType);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'User successfully updated'});
        });
        connection.execSql(request);
    });
});

module.exports = router;
