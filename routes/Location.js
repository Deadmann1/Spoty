var express = require('express');
var router = express.Router();

module.exports = router;
var connection = require('./Database');
router.route('/locations').get('/locations',function (err, req, res) {
    if (err) {
      return res.send(err);
    }
      function executeStatement() {
    request = new Request("select 42, 'hello world'", function(err, rowCount) {
      if (err) {
        console.log(err);
      }
      else{
        return res.send(request);
      }
    });

    connection.execSql(request);
    }
  });


  