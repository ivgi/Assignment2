# Run the Application
${Home} = current directory of the project:<br/>
-> cd ${Home}

Start the services using docker compose:<br/>
-> sudo docker-compose up

Build the application:<br/>
-> mvn package

Start the spring boot application:<br/>
-> java -jar ./target/DevAssignment-1.0.jar

Open in firefox or chrome (I have tested only with these two browsers) localhost:8080<br/>
On this page you will see messages which are getting queued to activemq. The page uses websockets protocol.<br/>
If you happen to restart the application don't forget to refresh the page in order to reestablish the websocket connection<br/>

Post an input to our rest endpoint located at localhost:8080/camel/push:<br/>
-> curl --header "Content-Type: application/json" --request POST --data '{"numIterations":2,"liveCellsPercent":60}' localhost:8080/camel/push

You should see on the page:<br/>
10:47:02 AM: {"numIterations":2,"liveCellsPercent":60}

Open http://localhost:8161 this is our ActiveMq admin page. User: admin, Password: admin<br/>
Go to "Manage ActiveMq Broker" then "Queues" then gol.input. Here you can check how many messages are enqueued and dequeued.<br/>

Enter mysql db and check that we have the output in the database:<br/>
-> sudo mysql --host=127.0.0.1 --port=3307 --user=root --protocol=tcp --password=root<br/>
-> use demo<br/>
-> select * from gol_output;<br/>

If you don't have local mysql client, you can do the same from the docker mysql container. Only difference is the port will be 3306 now.
To login to a docker container do:
->sudo docker exec -it ${container_id} bash

# General Solution Description
We are listening for post requests on /camel/push endpoint.<br/>
The initial json I call it Input. The Input has format described in /resources/schema.json<br/>
When an Input arrives it is first validated against /resources/schema.json then it is sent to the websockets endpoint.<br/>
For the websockets we have configured the following endpoint: /stream/push<br/>
Then the Input is sent asynchronously to an ActiveMq queue called gol.input. There it is stored until consumed by our consumer. <br/>
The consumer listens on gol.input and after a message arrives it is transformed from json to pojo then transformed to another pojo called Output.<br/>
(We use demo.gol.Game class to play the game of life with the input and the end result is the output. Please see authors notes if you are interested later. For now just skip this part).<br/>
The output is persisted in mysql database using jpa and hibernate as implementation.

# Technical Solution Description
Technology stack is:<br/>
Spring-boot<br/>
ActiveMq as a temp storage and queue<br/>
Mysql as a database<br/>
Atmoshpere library for websockets communication<br/>
Apache Camel as a service integration framework<br/>
Jpa with Hibernate as an ORM<br/>

Important classes:
demo.route.FalconAssignmentRout - describes the route our Input travels in order to become an output persisted in MySql. All communication and protocols are abstracted as a camel components.
demo.validation.JsonValidationProcessor - json validation handler, because the default one is not good.
Credentials and configuration properties are stored in application.properties file.

#Resolved Issues
Issue 1:
Currently there is a bug in the atmosphere-runtime library which produces an exception with WARN level on startup.<br/>
Then the atmosphere continues to work as excepted.<br/>
The WARN is: "Failed to create AsyncSupport class: class org.atmosphere.container.JSR356AsyncSupport"<br/>
Real error: The path [/stream/{path0}/] contains one or more empty segments which are is not permitted<br/>
Then atmosphere falls back to default implementation which works ok.

Issue 2:
When websocket connection is first established atmosphere complains:<br/>
WebSocketProcessor$WebSocketException: Status code higher or equal than 400<br/>
However again it falls back and establishes the connection.

These problems were resolved by excluding the atmosphere-runtime 2.4.21 and including 2.4.22  


# Useful Commands
-> sudo docker-compose up<br/>
-> mvn package<br/>
-> java -jar ./target/DevAssignment-1.0.jar<br/>
-> curl --header "Content-Type: application/json" --request POST --data '{"numIterations":2,"liveCellsPercent":60}' localhost:8080/camel/push<br/>
-> sudo mysql --host=127.0.0.1 --port=3307 --user=root --protocol=tcp --password=root<br/>
-> use demo<br/>
-> select * from gol_output;
-> sudo docker exec -it ${container_id} bash

# Authors Notes
To imitate some processing and to make things interesting I TOOK a randomized implementation of [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) from [here](https://github.com/inoryy/game-of-life-java/tree/master/src/main/java/gof/core).<br/>
I added a method called countLiveCells(), which is needed so that we know how many lives cells are present at the end of the game. We write this result in the db.<br/>
The implementation is in package demo.gol<br/>
The input to tha game is:<br/>
numIterations - the number of board changes / generation ticks. (MAX = 100)<br/>
liveCellsPercent - when we initialize the board this defines what is the percent of a cell to be alive. (MAX = 100)<br/>
demo.gol.Game#play - starts the game

The output of the game is:<br/>
board_height: the vertical axis of the board<br/>
board_width: the horizontal axis of the board<br/>
num_iterations: tow many times we interate the board<br/>
num_of_living_cells: tow many cells are still alive after this iterations<br/>
start_percentage_of_living_cells: to initialize the board with dead or alive cells randomly according to percent

