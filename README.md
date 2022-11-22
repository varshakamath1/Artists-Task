# Artists-Task

## Git Clone and Run server
1. Clone the repository using the command - git clone https://github.com/varshakamath1/Artists-Task.git
2. Run mvn clean install to create the artists.war file.
3. The Wildfly server can be used to deploy the application. Here is the link to download the server https://www.wildfly.org/downloads/.
4. Unzip the the wildfly.zip file. Place the artists.war file in this location wildfly -> standalone -> deployments.
5. Open terminal/command prompt and come to location bin folder (wildfly -> bin).
6. Run ./standalone.bat to start the server.

## Sample requests and response for three apis

The openapi.yml file is updated with "Bonus Feature" is present in resources folder. The artistsInit.json file contains a list of artists.

1. GET - Get all srtists with limit and sort as query parameters.
   Request URL - http://localhost:8080/artists/artists?limit=2&sort=DESC
   Sample Response: 
   [{
    "artist_name": "string",
    "artist_genre": "string",
    "albums_recorded": 0,
    "username": "string"
    }]
  
2. GET - Get artist based on username 
   Request URL - http://localhost:8080/artists/artists/{username}
   Sample Response: 
   [{
    "artist_name": "string",.
    "artist_genre": "string",
    "albums_recorded": 0,
    "username": "string"
    }]
    
3. POST - Register the artist using API-KEY Security. API-KEY is provided in data.properties in resource folder.
   Request URL - http://localhost:8080/artists/artists/
   Sample Request:
   [{
    "artist_name": "string",.
    "artist_genre": "string",
    "albums_recorded": 0,
    "username": "string"
    }]
    
   
