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

//------------------------BEGIN HTTP HANDLERS ----------------//
app.get('/', routes.index);
app.get('/users', user.list);

//--------------------BEGIN USER LOGIN-----------------------//
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

//------------------BEGIN USER REGISTRATION--------------------//
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
					console.log('new user registered!')});
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

//-----------------------BEGIN ADD ACCOUNT-------------------------//
//	Takes the info for an account and associates it with a user
//	Input: name(String) the name of the account as displayed to the user (UNIQUE)
//	Input: user(String) name of the user to associate the account
//  Input: balance(Double) the amount of money in the account
//	Input: type(String) the type of account
//	Input: interest(Double) the amount of interest associated with this account
//	Output: success(bool) whether the account was added successfully or not
//  Input: date(String) the date in which the account was created occurred
app.get('/addAccount', function(request, response)
{	
  //Check if there is an existing account with the same name
	var
	  	cName 	  = request.query.name,
	  	cUser 	  = request.query.user,
	  	cBalance  = request.query.balance,
	  	cType	  = request.query.type,
	  	cInterest = request.query.interest,
	  	cDate	  = request.query.date,
	cName = cUser + '-' + cName,
	cBalance = '+' + cBalance;

	var uri = process.env.MONGOLAB_URI || 
    	      process.env.MONGOHQ_URL  ||
    	      'mongodb://localhost/RiotData';
    mongodb.MongoClient.connect(uri, { server: { auto_reconnect: true } }, function (err, db) {
		if(!err){
			console.log('we are connected!');
			var loginCollection = db.collection('login');
			var accountsCollection = db.collection('accounts');
				//	if requested account name already exists then return 0 don't do anything;
			accountsCollection.findOne({'name': cName}, function(err,item){
				if(item === null ) //	if name doesn't already exist....
				{
					console.log('password nonexistent so that means this is a new value');
					
					//  Into login find user*: user
					//  Into user* Insert: doc{ account : {list} + {user'-'name} }
					loginCollection.update( {'username':cUser},
											{'$push': {"accounts": cName}}, 
											function(err, result){
												console.log('adding the upadte');
											});
					
					//Create first line of transaction history as JSON
					//	Into accounts Insert: doc:{	name    : user'-'name, 
					//								balance : balance, 
					//								type    : type,
					//								interest: interest,
					//								transactionHistory: doc}

					var firstTrans = { 	'name'    :"*Account Created*",
										'balance' :cBalance,
										'insDate' :cDate,
										'effDate' :cDate
									},
						accountDoc = {	'name'    :cName,
										'amount' :cBalance,
										'type'    :cType,
										'interest':cInterest,
										'transactionHistory': firstTrans
									};
					accountsCollection.insert(accountDoc, {w:1}, function(err, result) {
						console.log('Added a new account!!!')
					});
					
					loginCollection.findOne({'username':cUser}, function(err, item){
						response.send(item);
					});
					
				}
				else
				{
					console.log('already existing account');
					response.send('0');
				}
			});
		}
		else
		{
			response.send('Database connection was not successful...');
		}
    });
});


//-----------------------BEGIN GRAB ACCOUNTS-----------------------//

//-----------------------BEGIN UPDATE ACCOUNT----------------------//
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});