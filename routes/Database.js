var Connection = require('tedious').Connection

/* Microsoft SQL Server */
var config = {
  userName: 'deadmann',
  password: 'Drfreemann33',
  server: 'spoty.database.windows.net',
  // If you are on Microsoft Azure, you need this:  
  options: { encrypt: true, database: 'SpotyDB' }
};

var connection = new Connection(config);
connection.on('connect', function (err) {
  if (err) {
    console.log(err);
  } else {
    console.log("Connected");
  }
});

module.exports = connection;