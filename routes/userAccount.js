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

router.get('/newUserId', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
    var result = [];
    request = new Request("SELECT MAX(IdUserAccount) + 1  FROM Spoty.UserAccount;", function (err) {
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
        connection.release();
        res.type('application/json');  
        res.send(new UserAccount(result[0],null,null,null,null,null,null));
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
        request = new Request("INSERT INTO Spoty.UserAccount (IdUserAccount, Username, Password, Firstname, Lastname, Birthdate, IdAccountType) VALUES (@IdUserAccount, @Username, @Password, @Firstname, @Lastname, @Birthdate, @IdAccountType);", function (err) {
            if (err) {
                next(err)
            }
        });

        var date = new  Date(user.Birthdate);
         var  month = date.getMonth();
        if(month == 0) {
            month = 1;
        }
        var date2 = date.getFullYear() + "-" + month + "-" + date.getDate();
        request.addParameter('IdUserAccount', types.Int,  user.IdUserAccount);
        request.addParameter('Username', types.NVarChar,  user.Username);
        request.addParameter('Password', types.NVarChar,  user.Password);
        request.addParameter('Firstname', types.NVarChar,  user.Firstname);
        request.addParameter('Lastname', types.NVarChar,  user.Lastname);
        request.addParameter('Birthdate', types.Date, date2);
        request.addParameter('IdAccountType', types.Int,  user.IdAccountType);
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

router.put('/users/:_id', function (req, res, next) {
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
        var date = new  Date(user.Birthdate);
         var  month = date.getMonth();
        if(month == 0) {
            month = 1;
        }
        var date2 = date.getFullYear() + "-" + month + "-" + date.getDate();
        request.addParameter('IdUserAccount', types.Int,  user.IdUserAccount);
        request.addParameter('Username', types.NVarChar,  user.Username);
        request.addParameter('Password', types.NVarChar,  user.Password);
        request.addParameter('Firstname', types.NVarChar,  user.Firstname);
        request.addParameter('Lastname', types.NVarChar,  user.Lastname);
        request.addParameter('Birthdate', types.Date,  date2);
        request.addParameter('IdAccountType', types.Int,  user.IdAccountType);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'User successfully updated'});
        });
        connection.execSql(request);
    });
});

module.exports = router;
