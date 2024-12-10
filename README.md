# About project.
A simple project with PostgreSQL database. The database stores information about books and their authors (many-to-many relationship); the project exposes HTTP endpoints allowing for interactions with database.


These endpoints are exposed by two subproject: first one based on Spring Boot and second one based on .NET. The second one offers similar functionality with exception of CSRF protection (I deployed it only in Spring Boot project).