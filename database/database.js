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

module.exports = connection;