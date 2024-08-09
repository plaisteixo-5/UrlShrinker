# Url Shrinker
This project implements a REST API with Spring Boot to solve this [challenge](https://github.com/backend-br/desafios/blob/master/url-shortener/PROBLEM.md).

The main objective is learn Java and implement additional technologies to shorten urls.

## Technologies used
- Java 21
- Redis 
- Docker 27.0.3
- Spring boot 3.3.1
- Flyway 10.10.0
- H2 2.2.224
- Jedis 5.0.2

## Endpoints
- **POST /shorten-url**
	- Create a shortened URL.
	- Request Payload:
		``` json
		{
		  "longUrl": "https://example.com"
		}
		```
	- Response:
		``` json
		{
			"longUrl": "https://ziolfadshuosd.com/teste",
			"shortUrl": "3BAK0",
			"expirationDate": "2024-08-04T00:19:14.318808458"
		}
		```

- **GET /{urlShortened}**
	- Redirect to te url that was shortened, if is valid.
	- Response:
		``` json
		{
			// Content of url page.
		}
		```

## Running the project

To run the project, you will need to have [docker compose](https://docs.docker.com/compose/install/) or maven installed on your machine.

1. Clone the repository:

	```
	git clone https://github.com/plaisteixo-5/UrlShrinker.git
 	```

3. Navigate to the project directory:

	```
	cd UrlShrinker
 	```

5. Run docker compose to up the application
   
	```
	docker compose up
 	```

7. Test the application:
   ```
   curl --location 'localhost:8080/shorten-url' \--header 'Content-Type: application/json' \--data '{ "longUrl": "https://github.com/"}'
   ```

## To-do
- [x] Implement timeout to cache on redis
- [x] Implement redirect
- [ ] Add logs
- [ ] Error handling
- [ ] Unit tests
- [ ] Documentation
- [x] Create image
- [x] Create docker-compose
- [x] Create validations in url
- [ ] Upload service design
- [ ] Utilize PostgreSQL
