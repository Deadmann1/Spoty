var express = require('express');
var router = express.Router();
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var Country = require("../models/country.js");
var pool = require("../database/database.js");


router.get('/countries', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
    var result = [];
    var countries = [];
    request = new Request("SELECT * FROM Spoty.Country;", function (err) {
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
        countries.push(new Country(result[0],result[1]));
        result = [];
    });
    request.on('doneInProc', function (rowCount, more, rows) {
        console.log(rowCount + ' rows returned');
        connection.release();
        res.type('application/json');  
        res.send(countries);
    });
    connection.execSql(request);
    });
});

router.get('/countries/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
    var result = [];
    var country;
    request = new Request("SELECT * FROM Spoty.Country WHERE IdCountry =" + req.params._id + ";", function (err) {
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
        country = new Country(result[0],result[1]);
        connection.release();
        res.type('application/json');  
        res.send(country);
    });
    connection.execSql(request);
    });
});
router.post('/countries', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var country = req.body;
        request = new Request("INSERT Spoty.Country (IdCountry, CountryName) VALUES (@IdCountry, @CountryName);", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdCountry', types.Int,  country.IdCountry);
        request.addParameter('CountryName', types.NVarChar,  country.CountryName);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Country successfully added'});
        });
        connection.execSql(request);
    });
});

router.delete('/countries/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        request = new Request("DELETE FROM Spoty.Country WHERE IdCountry =" + req.params._id + ";", function (err) {
            if (err) {
                next(err)
            }
        });
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Country successfully deleted'});
        });
        connection.execSql(request);
    });
});

router.put('/countries/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var country = req.body;
        request = new Request("UPDATE Spoty.Country SET CountryName = @CountryName WHERE IdCountry= @IdCountry;", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdCountry', types.Int, country.IdCountry);
        request.addParameter('CountryName', types.NVarChar, country.CountryName);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Country successfully updated'});
        });
        connection.execSql(request);
    });
});
module.exports = router;
