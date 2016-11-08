var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var errorHandler = require('error-handler');

/* ROUTES */
var location = require('./routes/location');
var index = require('./routes/index');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
// SQL SERVER
var Connection = require('tedious').Connection;
var config = {
  userName: 'deadmann',
  password: 'Drfreemann33',
  server: 'spoty.database.windows.net',
  // If you are on Microsoft Azure, you need this:  
  options: { encrypt: true, database: 'SpotyDB' }
};
config.options.rowCollectionOnRequestCompletion = true;
var connection = new Connection(config);
connection.on('connect', function (err) {
  // If no error, then good to proceed.  
  console.log("Connected");
});
app.set('connection', connection);


/* ROUTES */
app.use('/', index);
app.use('/api', location);




var env = process.argv[2] || 'dev';
switch (env) {
  case 'dev':
    // Setup development config
    //app.use(errorHandler);
    break;
  case 'prod':
    // Setup production config
    break;
}

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
