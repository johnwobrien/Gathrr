DOCUMENTATION -

CONSTRUCTOR -

FightrDBClient()
	DESCRIPTION:
		- Returns an object representing the DB Client
	PARAMETERS:
		- None
	RETURN:
		- FightrDBClient object

METHODS -

(CREATE)
public addUser
	DESCRIPTION:
		- Adds a user to the database requiring some standard personal information
		- An empty fight-history record and fighters-seen record are automatically created.
	PARAMETERS:
		- String id (individual user ID from Facebook)
		- String name (User's full name)
		- double weight (User's weight)
		- String sex (male/female)
		- String linkToPicture (String link to user's profile picture)
	RETURN:
		- void

(READ)
public getUser(String id)
	DESCRIPTION:
		- Returns a DBObject representing a user.
		- This DBObject is accessed by using the addUser parameters as keys
	PARAMETERS:
		- String id (individual user ID from Facebook)
	RETURN:
		- DBObject : represents a user.

(UPDATE)
public updateUser
	DESCRIPTION:
		- Allows for the update of a user record.
		- This function is primarily used to update a user record, but also to change user information.
	PARAMETERS:
		- String id (individual user ID from Facebook)
		- String name (User's full name)
		- double weight (User's weight)
		- String sex (male/female)
		- ArrayList<Object> history - List representation of a user's fight history
		- HashMap<String,Long> fightersSeen) - HashMap representation of users that have already been swiped on
	RETURN:
		- void
	
public addSeen
	DESCRIPTION:
		- Wrapper for the updateUser function to update only the fighters-seen parameter.
	PARAMETERS:
		- String id (individual user ID from Facebook)
		- String idSeen (individual user ID from Facebook that the first user swiped on)
	RETURN:
		- void

public getAllNotSeen
	DESCRIPTION:
		Given a user's ID, it will return a list of DBObjects representing users that they've already swiped on.
	PARAMETERS:
		- String id (individual user ID from Facebook)
	RETURN:
		- ArrayList<DBObject> : Array list DBObjects representing users

(DESTROY)
public void deleteUser(String id)
	DESCRIPTION:
		- Delete the record of a user with the given ID from the database.
	PARAMETERS:
		- String id (individual user ID from Facebook)
	RETURN:
		- void