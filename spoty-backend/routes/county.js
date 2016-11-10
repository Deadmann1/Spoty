var express = require('express');
var router = express.Router();
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var County = require("../models/county.js");
var pool = require("../database/database.js");


router.get('/counties', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var counties = [];
        request = new Request("SELECT * FROM Spoty.County;", function (err) {
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
            counties.push(new County(result[0], result[1], result[2]));
            result = [];
        });
        request.on('doneInProc', function (rowCount, more, rows) {
            console.log(rowCount + ' rows returned');
            connection.release();
            res.type('application/json');
            res.send(counties);
        });
        connection.execSql(request);
    });
});

router.get('/counties/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var county;
        request = new Request("SELECT * FROM Spoty.county WHERE IdCounty =" + req.params._id + ";", function (err) {
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
            county = new County(result[0], result[1], result[2]);
            connection.release();
            res.type('application/json');
            res.send(county);
        });
        connection.execSql(request);
    });
});
router.post('/counties', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var county = req.body;
        request = new Request("INSERT Spoty.County (IdCounty, CountyName, IdCountry ) VALUES (@IdCounty, @CountyName, @CityName, @IdCountry);", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdCounty', types.Int,  county.IdCounty);
        request.addParameter('CountyName', types.NVarChar,  county.CountyName);
        request.addParameter('IdCountry', types.Int,  county.IdCountry);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'County successfully added'});
        });
        connection.execSql(request);
    });
});

router.delete('/counties/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        request = new Request("DELETE FROM Spoty.County WHERE IdCounty =" + req.params._id + ";", function (err) {
            if (err) {
                next(err)
            }
        });
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'County successfully deleted'});
        });
        connection.execSql(request);
    });
});

router.put('/counties/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var county = req.body;
        request = new Request("UPDATE Spoty.City SET CountyName = @CountyName, IdCountry = @IdCountry WHERE IdCounty = @IdCounty;", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('IdCounty', types.Int,  county.IdCounty);
        request.addParameter('CountyName', types.NVarChar,  county.CountyName);
        request.addParameter('IdCountry', types.Int,  county.IdCountry);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'County successfully updated'});
        });
        connection.execSql(request);
    });
});

module.exports = router;
