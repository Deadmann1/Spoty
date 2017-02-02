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
            ratings.push(new Rating(result[0], result[1], new Date(result[2]), result[3], result[4]));
            result = [];
        });
        request.on('doneInProc', function (rowCount, more, rows) {
            console.log(rowCount + ' rows returned');
            connection.release();
            res.type('application/json');
            var ret = [];
            for(var i=0;i<ratings.length;i++) {
                var d = new Date(ratings[i].Date);
                var month = d.getMonth();
                if(month == 0) {
                    month = 1;
                }
                ret.push({"Grade": ratings[i].Grade, "Feedback":ratings[i].Feedback, "Date":  d.getFullYear() + "-" + month + "-" + d.getDate() , "IdUserAccount":ratings[i].IdUserAccount, "IdLocation":ratings[i].IdLocation});
            }
            if(ret.length == 0 ){
                res.sendStatus(204);
            }
            else {
                res.send(ret);
            }
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
        var ratings = [];
        var ret = [];
        request = new Request("SELECT * FROM Spoty.Rating WHERE IdLocation =" + req.params._id + ";", function (err) {
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
                ratings.push(new Rating(result[0], result[1], new Date(result[2]), result[3], result[4]));
                console.log(result);
                result = [];
            });
           request.on('doneInProc', function (rowCount, more, rows) {
            console.log(rowCount + ' rows returned');
            connection.release();
            res.type('application/json');
            var ret = [];
            for(var i=0;i<ratings.length;i++) {
                var d = new Date(ratings[i].Date);
                var month = d.getMonth();
                if(month == 0) {
                    month = 1;
                }
                ret.push({"Grade": ratings[i].Grade, "Feedback":ratings[i].Feedback, "Date":  d.getFullYear() + "-" + month + "-" + d.getDate() , "IdUserAccount":ratings[i].IdUserAccount, "IdLocation":ratings[i].IdLocation});
            }
            if(ret.length == 0 ){
                res.sendStatus(204);
            }
            else {
                res.send(ret);
            }
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
        
        var date = new Date();
        var rating = req.body;
        request = new Request("INSERT Spoty.Rating (Grade, Feedback, Date, IdUserAccount, IdLocation) VALUES (@Grade, @Feedback,@Date, @IdUserAccount, @IdLocation);", function (err) {
            if (err) {
                next(err)
            }
        });

        request.addParameter('Grade', types.Int,  rating.Grade);
        request.addParameter('Feedback', types.NVarChar,  rating.Feedback);
        request.addParameter('IdUserAccount', types.Int,  rating.IdUserAccount);
        request.addParameter('IdLocation', types.Int,  rating.IdLocation);

        var  month = date.getMonth();
        if(month == 0) {
            month = 1;
        }
        var date = date.getFullYear() + "-" + month + "-" + date.getDate();

        request.addParameter('Date', types.Date, date);
        request.on('doneInProc', function (columns) {
            connection.release();
            res.send({message: 'Rating successfully added'});
        });
        connection.execSql(request);
});
});

module.exports = router;