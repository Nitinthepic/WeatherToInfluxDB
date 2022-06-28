# WeatherToInfluxDB
A simple Java program that read local weather data from weather.gov and puts into an influxDB server

## Configuration
To configure the program, enter the Main.java file and then modify the String values for the influxDB token, bucket, organization, and url. Furthermore, you'll need to find the correct Weather.gov url.
To that end, here's a [link](https://www.weather.gov/documentation/services-web-api) to the API's webiste. Figure out your local weather office and its gridX and gridY. You'll want the weatherURL to be in the format: "https://api.weather.gov/gridpoints/{office}/{grid X},{grid Y}/forecast/hourly".
## Building the Project
Simply load the project into your IDE of choice and build the project with assembly:single. This produces a single jar that you can execute. On linux, I've found that using & at the end of the java -jar command is a pretty nice solution which allows you to run the program in the background.
