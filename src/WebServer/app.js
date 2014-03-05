/**
 * Module dependencies.
 */
var express = require('express');
var routes = require('./routes');
var user = require('./routes/user');
var http = require('http');
var path = require('path');
var app = express();

var mongodb = require('mongodb');
// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));
// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}
app.get('/', routes.index);
app.get('/users', user.list);
app.get('/login', function(request, response) {
	var cUser = request.query.username; 
		cPass = request.query.password;
	var uri = process.env.MONGOLAB_URI || 
    	      process.env.MONGOHQ_URL ||
    	      'mongodb://localhost/RiotData';

mongodb.MongoClient.connect(uri, { server: { auto_reconnect: true } }, function (err, db) {
	if(!err){
		console.log('we are connected!');
		var collection = db.collection('login');
		collection.findOne({'username':cUser}, function(err, item){
			console.log(item.password);
			if(cPass == item.password)
			{
				console.log('passwords were equivalent!');
				response.send('1');
			}
			else
			{
				response.send('0');
			}
		})
	}
});
});
app.get('/register', function(request, response) {
	var cUser = request.query.username; 
		cPass = request.query.password;
	var uri = process.env.MONGOLAB_URI || 
    	      process.env.MONGOHQ_URL ||
    	      'mongodb://localhost/RiotData';

mongodb.MongoClient.connect(uri, { server: { auto_reconnect: true } }, function (err, db) {
	if(!err){
		console.log('we are connected!');
		var collection = db.collection('login');
		collection.findOne({'username':cUser}, function(err, item){
			if(item === null )
			{
				console.log('password nonexistent so that means this is a new value');
				response.send('1');
				var doc1 = {
					'username':cUser,
					'password':cPass
				}
				collection.insert(doc1, {w:1}, function(err, result) {
					console.log('new user registered!')})
			}
			else
			{
				console.log('already existing user')
				response.send('0');
			}
		})
	}
	else
	{
		response.send('oh no :(...')
	}
});
});
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});