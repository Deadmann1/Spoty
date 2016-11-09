var express = require('express');
var router = express.Router();
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var Address = require("../models/address.js");
var pool = require("../database/database.js");


router.get('/addresses', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var addresses = [];
        request = new Request("SELECT * FROM Spoty.Address;", function (err) {
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
            addresses.push(new Address(result[0], result[1], result[2], result[3]));
            result = [];
        });
        request.on('doneInProc', function (rowCount, more, rows) {
            console.log(rowCount + ' rows returned');
            connection.release();
            res.type('application/json');
            res.send(addresses);
        });
        connection.execSql(request);
    });
});

router.post('/addresses', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var address = req.body;
        request = new Request("INSERT Spoty.Address (IdAddress, StreetName, HouseNumber, IdCity) VALUES (@IdAddress, @StreetName, @HouseNumber, @IdCity);", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdAddress', types.Int,  address.IdAddress);
        request.addParameter('StreetName', types.NVarChar,  address.StreetName);
        request.addParameter('HouseNumber', types.NVarChar,  address.HouseNumber);
        request.addParameter('IdCity', types.Int,  address.IdCity);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Address successfully added'});
        });
        connection.execSql(request);
    });
});

router.get('/addresses/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var address;
        request = new Request("SELECT * FROM Spoty.Address WHERE IdAddress =" + req.params._id + ";", function (err) {
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
            address = new Address(result[0], result[1], result[2], result[3]);
            connection.release();
            res.type('application/json');
            res.send(address);
        });
        connection.execSql(request);
    });
});

router.delete('/addresses/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        request = new Request("DELETE FROM Spoty.Address WHERE IdAddress =" + req.params._id + ";", function (err) {
            if (err) {
                next(err)
            }
        });
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Address successfully deleted'});
        });
        connection.execSql(request);
    });
});

router.put('/addresses/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var address = req.body;
        request = new Request("UPDATE Spoty.Address SET StreetName = @StreetName, HouseNumber = @HouseNumber, IdCity = @IdCity WHERE IdAddress= @IdAddress;", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdAddress', types.Int,  address.IdAddress);
        request.addParameter('StreetName', types.NVarChar,address.StreetName);
        request.addParameter('HouseNumber', types.Int,  address.HouseNumber);
        request.addParameter('IdCity', types.Int,  address.IdCity);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Address successfully updated'});
        });
        connection.execSql(request);
    });
});
module.exports = router;
