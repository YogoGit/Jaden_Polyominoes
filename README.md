# Polyominoes
A repository to for a web application game I made for CS-389: Advanced Software Engineering.

This project is a webapp that lets users play a game  that is similar to Tetris, but has polyominoes from
monomino to hexominoes. Users can play without an account, but if they create an account the application will track their 
high scores. The high scores can be viewed on the leaderboard page to see who is the top player.

You are welcome to download/clone the project build and set up you own database.

1. For easy deployment you can use docker and docker compose:

   * Instructions to install docker can be found here: https://docs.docker.com/get-docker/
   * Instructions to install docker compose plugin can be found here: https://docs.docker.com/compose/install/

2. Once you have docker and docker compose install you can download the DockerDeployment.zip from the latest release.
   * https://github.com/jbathon/Polyominoes/latest/download/DockerDeployment.zip

3. Unzip the file and navigate through the terminal to the Polyominoes directory.
4. Run the command `docker-compose up -d` to launch the application
5. The application can be accessed at https://localhost:8080/

---

To build the project I recommend using docker to launch the postgresql database.

1. Install Docker
   * Instructions to install docker can be found here: https://docs.docker.com/get-docker/
2. Once docker has been downloaded, open up the command line interface of your choice, type the following: "docker pull postgres"

3. Execute the following command to set up your database container: </br>
`docker run --name <container-name> -e POSTGRES_USER=polyominoes -e POSTGRES_PASSWORD=appPassword -e POSTGRES_DB=polyominoes -p 5432:5432 -d postgres`

4. Now that your database is running you can use git to clone the repository or download the src from the latest release
5. Navigate to the application directory in your terminal and run the command `./gradlew build`
   * Note Java 17 is need to run gradle and build the application 
6. Once build is complete run the command `./gradlew bootRun`

