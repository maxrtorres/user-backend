# User Backend

This is a backend application to store and retrieve data on a set of social media users.
Basic user data is maintained such as username and social media posts. 

Here is how it works:
- A [Spark Java](https://github.com/perwendel/spark) API listens for HTTP requests
  - The API runs locally as a proof of concept
- An API endpoint receives a request and passes it to a data access object (DAO)
- The DAO queries a relational database, which it connects to through
[JDBC](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/)
  - Queries use [MySQL](https://www.mysql.com/) syntax, but the database url and credentials are left as environment variables
- The DAO returns data to the endpoint, which then responds to the request
- Though there is no frontend, backend functionality is tested using
[JUnit](https://junit.org/junit5/)
