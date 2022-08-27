# user-backend

This is a backend server for accessing data on a set of users, such as their username and social media posts. Here is how it works:
- A [Spark Java](https://github.com/perwendel/spark) API receives requests (the API runs locally as a proof of concept)
- These requests are handled by a data access object (DAO)
- The DAO completes the request by operating on a MySQL database, through JDBC
- Since there is no frontend, functionality is demonstrated with unit tests
