var express = require('express')
  , routes = require('./routes')
  , User = require('./routes/User')
  , Auth = require('./routes/Auth')
  , Location = require('./routes/Location')
  , AccountType = require('./routes/AccountType')
  , Rating = require('./routes/Rating')
  , LocationType = require('./routes/LocationType')
  , Adress = require('./routes/Adress')
  , City = require('./routes/City')
  , County = require('./routes/County')
  , Country = require('./routes/Country')
  , http = require('http')
  , path = require('path');
var app = express();

  app.configure(function () {
    app.set('port', process.env.PORT || 3000);
    app.set('views', __dirname + '/views');
    app.set('view engine', 'jade');
    app.use(express.favicon());
    app.use(express.logger('dev'));
    app.use(express.bodyParser());
    app.use(express.methodOverride());
    app.set(app.router);
    app.use(express.static(path.join(__dirname, 'public')));
  });

  app.configure('development', function () {
    app.use(express.errorHandler());
  });

  app.get('/', routes.index);
  app.get('/users', User.list);
  app.get('/api', routes.Location);

  http.createServer(app).listen(app.get('port'), function () {
    console.log("Express server listening on port " + app.get('port'));
  });
