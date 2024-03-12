# Elevators System

The system is built with Java and Spring Boot for the server and Vite and Chakra UI for the client. Client sends POST requests with current state of the elevators and the server responds with the next state of the elevators.

## Aims of the Project

The project aimed to develop a complete full-stack application, containerize it, and deploy it to the cloud. Additionally, it served as an opportunity to gain knowledge and experience with Spring Boot, Vite, Docker and GCP.

## About the Algorithm

The most interesting part of the project is the algorithm that decides the next state of the elevators. The algorithm is designed to minimize the time it takes for passengers to get to their destination.

### Assumptions

The system I designed assumes that picking up and dropping off passengers is done instantly. That means that when two elevators would move from floor A to B and the first one would never open the door to let passengers they would both arrive at the same time.

Also I assumed that the elevators have infinite capacity and that the system is not concerned with the number of passengers in the elevator.

Lastly, I assumed that the system somehow knows how many passengers are waiting on each floor. (it has some cool computer vision system 😊)

### Algorithm

The algorithm goes at follows:

1. If there are passengers inside the elevator, it will go to the farthest requested floor in the direction it is currently going. I will allow entering the elevator if the requested floor is in the same direction as the elevator is currently going.
2. If there are no passengers inside the elevator, it will look for critical floors (floors where passengers are waiting) and go to the closest one. (Critical floors are floors where more than 5 passengers are waiting) TODO: make this configurable
3. If there are no critical floors, it will go to the farthest floor where passengers are waiting, in the direction that no other elevator is going to.

## Technologies Used

- Spring Boot (Java 17)
- Vite (TypeScript)
- Chakra UI
- Docker
- GCP (Cloud Run)

## Project Structure

The project is split into two parts:

- Server: The server is a REST API that handles requests to the elevator system. It's built with Spring Boot.
- Client: The client is a simple web interface that allows users to interact with the elevator system. It's built with [Vite](https://vitejs.dev/) and [Chakra UI](https://chakra-ui.com/). You can find the client code [here](/ui-vite-app).

## Building and Running the Project Locally

To build the project, navigate to the project root directory and run the following command:

```sh
./mvnw clean install
```

This will create a JAR file in the target directory.

To run the project, use the following command:

```sh
java -jar target/Elevators-0.0.1-SNAPSHOT.jar
```

## Running with Docker

To build the Docker image, use the following command:

```sh
docker build -t elevators .
```

Then, to run the project with Docker, use the following command:

```sh
docker run -p 8080:8080 elevators
```

App will be available at [http://localhost:8080](http://localhost:8080).

The latest image is also available on [Github Packages](https://github.com/ReptilianEye/ElevatorsSystem/pkgs/container/elevators-ghcr).

## Deployment

The project is deployed to GCP using Cloud Run and Cloud Build. The deployment is automated using a Github Actions [(details)](.github/workflows/google-cloudrun-source.yml).

The application is available at [Elevators System](https://elevatorssystem-uk6zzsnjeq-ey.a.run.app).

## Future Improvements

- Add more configuration options for the algorithm
- Add more tests

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
