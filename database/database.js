var ConnectionPool = require('tedious-connection-pool');
var Request = require('tedious').Request;

var poolConfig = {
  min: 0,
  max: 100,
  log: true
};

var connectionConfig = {
  userName: 'deadmann',
  password: 'Drfreemann33',
  server: 'spoty.database.windows.net',
  // If you are on Microsoft Azure, you need this:  
  options: { encrypt: true, database: 'SpotyDB' }
};

//create the pool
var pool = new ConnectionPool(poolConfig, connectionConfig);

pool.on('error', function (err) {
  console.error(err);
});

module.exports = pool;