# rickandmorty
Rick and Morty API v2

rickandmory is a wrapper around the rickandmortyapi.com which searchs for a character of the Rick and Morty universe.

In this implementation, a series of calls are made to the original API for every call to our API.

As the universe of Rick and Morty looks more of a multiverse, multiple characters can be returned from different universes of existance.

## Package Architecture

```
├── main
│   ├── java
│   │   └── dev
│   │       └── bracers
│   │           └── rickandmorty
│   │               ├── config
│   │               │   ├── ConsumerConfiguration.java
│   │               │   └── WebConfig.java
│   │               ├── controller
│   │               │   └── CharacterController.java
│   │               ├── exception
│   │               │   └── EpisodesNotFoundException.java
│   │               ├── model
│   │               │   ├── Character.java
│   │               │   ├── consumer
│   │               │   │   ├── CharactersInfoJSON.java
│   │               │   │   ├── CharactersJSON.java
│   │               │   │   ├── CharactersResultJSON.java
│   │               │   │   └── EpisodesResultJSON.java
│   │               │   └── Episode.java
│   │               ├── RickandmortyApplication.java
│   │               └── service
│   │                   ├── CharacterServiceImpl.java
│   │                   ├── CharacterService.java
│   │                   ├── EpisodeServiceImpl.java
│   │                   └── EpisodeService.java
│   └── resources
│       ├── application.properties
│       ├── static
│       └── templates
└── test
    └── java
        └── dev
            └── bracers
                └── rickandmorty
                    ├── RickandmortyApplicationTests.java
                    └── service
                        ├── CharacterServiceTest.java
                        └── EpisodeServiceTest.java

```

The package structure is a common MVC one. We have packages for configurations (**config**), controllers (**controller**), exceptions (**exception**), models (**model**) and services (**service**), and the main application class alone (**RickandmortyApplication.java**).

We also a test package (**service**) to test episodes and characters services (**CharacterServiceTest.java** and **EpisodeServiceTest.java**).

## How Application works

The application runs at port **9960** by default.

For every call to the API, the character and episode services are used to retrieve the requested information (name of the character, name of the episodes in which the character appears, and first appearance of the character in the series).

Notice that the first_appearance field returned is based on the date of the first Episode for the Character we are querying.

An empty list and 404 response is returned if no Characters are found.

## Endpoint

The application only has one endpoint:

```
/api/v2/search-character-appearance?name={a_name}
```

This endpoint returns a JSON object with the character (or characters, as the universe of Rick and Morty is actually a multiverse, so multiple Ricks exists in different planes of existance, for example) containing its name, a list of the episodes its appears on, and the date of first appearance on the series.

## How Postman tests work

Just import the file **postman/rickandmorty_tests.postman_collection.json** of the repository into Postman and run the collection tests. If everything works fine, you will get something like this:

![Tests OK](http://bracers.dev/wp-content/uploads/2023/10/Screenshot-from-2023-10-10-23-16-06.png)

## Docker

You can find the **Dockerfile** for building an image of the application in this repository. The application image is also in my **Docker Hub** repository, which can be found at [https://hub.docker.com/r/rulu158/rickandmorty](https://hub.docker.com/r/rulu158/rickandmorty).

You can run the image at port 9960 as follows:

```
docker pull rulu158/rickandmorty:2.0.0
docker run -p 9960:9960 rulu158/rickandmorty:2.0.0
```

## API Documentation

You can find the API documentation via Swagger at:

```
http://localhost:9960/swagger-ui/index.html
```
