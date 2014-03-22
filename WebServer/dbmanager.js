var uri =   process.env.MONGOLAB_URI || 
            process.env.MONGOHQ_URL ||
            'mongodb://localhost/RiotData';

var db=require('monk')(uri),
    users = db.get('login');

    users.find({username: 'hello'}, function(err, docs) {
        //look for hello
        var password = docs.password;
        console.log('found the docs ' + password)
    })