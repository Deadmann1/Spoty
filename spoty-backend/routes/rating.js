var express = require('express');
var router = express.Router();
var app = require('../app');
var Request = require('tedious').Request;
var types = require('tedious').TYPES;
var Rating = require("../models/rating.js");
var pool = require("../database/database.js");


router.get('/ratings', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var ratings = [];
        request = new Request("SELECT * FROM Spoty.Rating;", function (err) {
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
            ratings.push(new Rating(result[0], result[1], result[2], result[3]));
            result = [];
        });
        request.on('doneInProc', function (rowCount, more, rows) {
            console.log(rowCount + ' rows returned');
            connection.release();
            res.type('application/json');
            res.send(ratings);
        });
        connection.execSql(request);
    });
});

router.get('/ratings/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var result = [];
        var rating;
        request = new Request("SELECT * FROM Spoty.Rating WHERE IdRating =" + req.params._id + ";", function (err) {
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
            rating = new Rating(result[0], result[1], result[2], result[3]);
            connection.release();
            res.type('application/json');
            res.send(rating);
        });
        connection.execSql(request);
    });
});

router.post('/ratings', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        var rating = req.body;
        request = new Request("INSERT Spoty.Rating (Grade, Feedback, IdUserAccount, IdLocation) VALUES (@Grade, @Feedback, @IdUserAccount, @IdLocation);", function (err) {
            if (err) {
                next(err)
            }
        });
        request.addParameter('Grade', types.Int,  rating.Grade);
        request.addParameter('Feedback', types.NVarChar,  rating.Feedback);
        request.addParameter('IdUserAccount', types.Int,  rating.IdUserAccount);
        request.addParameter('IdLocation', types.Int,  rating.IdLocation);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Rating successfully added'});
        });
        connection.execSql(request);
    });
});
/*
router.delete('/ratings/:_id', function (req, res, next) {
    pool.acquire(function (err, connection) {
        if (err) {
            console.error(err);
            return;
        }
        request = new Request("DELETE FROM Spoty.Rating WHERE IdCity =" + req.params._id + ";", function (err) {
            if (err) {
                next(err)
            }
        });
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Rating successfully deleted'});
        });
        connection.execSql(request);
    });
    
});
*/

module.exports = router;