In order to run the project:

Install nodejs: http://nodejs.org/download/

Install npm (Node Package Manager):
if you can use .sh: https://www.npmjs.org/install.sh
This allows you to add packages and precoded features
from the command line with ease.

install the following packages:
'npm install <package-name>'
npm install express
npm install jade
npm install mongodb
npm install mongoose
npm install monk

you can run the server locally by running 'node app.js'
from the command line in the WebServer folder.


/-------------GET REQUESTS TO THE SERVER---------------/

*************REGISTER*************
http://buzzfunds.herokuapp.com/register?username=<USERNAME>&password=<PASSWORD>
// Creates a new user with a given username and password if no user with that name already exists
// Input: username(String) the user's requested username
// Input: password(String) the password to associate with the account
// Output: success(bool) whether or not the account was registered
****************************************************

*************LOGIN*************
http://buzzfunds.herokuapp.com/login?username=<USERNAME>&password=<PASSWORD>
// Checks to see if the user has entered the correct password
// Input: username(String) the user's username
// Input: password(String) the user's password
// Output: success(bool) whether the password is correct or not
****************************************************

*************ADD ACCOUNT*************
http://buzzfunds.herokuapp.com/addaccount?user=<USERNAME>&name=<ACCOUNTNAME>&amount=<AMOUNT>&type=<TYPE>&interest=<INTEREST>&date=<DATE>
//	Takes the info for an account and associates it with a user
//	Input: name(String) the name of the account as displayed to the user (UNIQUE)
//	Input: user(String) name of the user to associate the account
//  Input: amount(Double) the amount of money in the account
//	Input: type(String) the type of account
//	Input: interest(Double) the amount of interest associated with this account
//  Input: date(String) the date in which the account was created
//	Output: success(bool) whether the account was added successfully or not
*****************************************************************

*************GRAB ACCOUNT*************
http://buzzfunds.herokuapp.com/retrieveaccounts?user=<USERNAME>
// Takes the user and returns an array of json accounts associated with that user
//	Input: user(String) the name of the user in question
//	Output: accounts(json[]) the accounts associated with a user 
****************************************************

*************TRANSACTION*************
http://buzzfunds.herokuapp.com/transaction?user=<USERNAME>&name=<TRANSNAME>&amount=<AMOUNT>&type=<TYPE>&account=<ACCOUNT>&insDate=<INITDATE>&effDate=<EXEDATE>&category=<CATEGORY>
// Takes in a transaction
// 
// Input:
//  user(string) name of the user
//  name(string) user defined identifier of the transaction
//	amount(float) the amount to be put/taken in/out of the account 
//  type(string) 'withdrawal' if the transaction is a withdrawal or 'deposit' if deposit
//  account(string) the long name of the account from which to make the change
//	insDate(string) the date in which the transaction was created
//  effDate(string) the date in which the transaction should occur
//  category(string) the category of transaction
//	Output: success(bool) whether the transaction was added successfully
****************************************************

*************RETRIEVE TRANSACTIONS*************
retrieves the transactions for the specified user
Input:
user(string) name of the user
name(string) name of the account
****************************************************