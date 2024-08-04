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
- PostegreSQL

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
			-- Content of url page.
		}
		```

## Running the project

1. Clone the repository:

`git clone https://github.com/plaisteixo-5/UrlShrinker.git`

2. Navigate to the project directory:

`cd UrlShrinker`

3. Build the project:

`mvn clean package`

4. Run the application:

`java -jar target/UrlShrinker.jar`

## To-do
- [x] Implement timeout to cache on redis
- [x] Implement redirect
- [ ] Error handling
- [ ] Unit tests
- [ ] Documentation
- [ ] Create image
- [ ] Create docker-compose
- [x] Create validations in url
- [ ] Upload service design
