# link-shortener

A URL shortener built with Spring Boot. Creates short codes, redirects, and tracks clicks.

While this app works perfectly fine, I've also built a similar one in plain Java, just to see what Spring actually does for me under the hood — [http-from-scratch](https://github.com/KonradLitwiniuk/http-from-scratch).

## What it does

- POST /links - send a URL in the body, get a short code and the full shortened address back. Empty URLs are rejected.
- GET /{code} - visiting the short link redirects (302) to the original URL. Every click is tracked.
- GET /links/{code}/stats - returns link statistics: total clicks and a daily breakdown.
- Errors - missing codes return 404, invalid input 400, and internal errors 500.

## Running it

bash
docker compose up -d


Then run LinkShortenerApplication from your IDE (or ./mvnw spring-boot:run) and hit http://localhost:8080.



## How it's put together


Controller  ->  Service  ->  Repository  ->  Postgres


- **Controller** - handles HTTP requests, validates incoming data, and returns the appropriate responses to the client. Errors are caught globally by `GlobalExceptionHandler`.
- **Service** - the heart of the app. It manages all the core business logic, including generating unique short codes, handling collisions, and assembling stats.
- **Repository** - the only component that talks directly to the database, abstracting SQL queries into simple Java methods.
- **Postgres** - the database. Hibernate maps the entities to tables and creates the schema on startup.

## A few decisions worth explaining

**Why catch DB exceptions instead of checking if a code exists?**

Checking existsByCode first is a trap - nother request could snatch that code a split second later (a race condition). I,nstead, I just try to save it and let the database's unique constraint reject duplicates. Postgres handles the check and save all at once, making it completely thread-safe.

**Why AT TIME ZONE in SQL queries?** 

Grouping stats by date can break depending on your server's timezone. I explicitly use Postgres's AT TIME ZONE in the query to lock in the right timezone. This guarantees a midnight click in Warsaw isn't accidentally counted as yesterday in UTC.l

**Why Testcontainers instead of H2?** 

H2 is fast, but it only pretends to be Postgres. It can easily choke on Postgres-specific SQL, like that AT TIME ZONE trick. I use Testcontainers to test against a real database. Testing on the exact same engine you use in production prevents the classic "it passed locally but crashed live" problem.


## Known limitations

- **No pagination on stats.** The daily breakdown returns every day at once. Fine for a few weeks of data, less so after a couple of years.
- **Clicks are saved synchronously.** The redirect waits for the database write to finish, so every visitor pays for it. Moving this off the request would be the obvious next step.
- **No authentication.** Anyone who knows a code can see its stats. There's no concept of link ownership at all.