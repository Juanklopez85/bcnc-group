# README #

Un buen readme explicando tecnologías, arquitecturas y patrones utilizados, además de otras cosas que debe tener un readme:

### What is this repository for? ###

* Quick summary
* Version
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Summary of set up
* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Commits ###

- Initial commit (Version: 0.0.1-SNAPSHOT)
  - Generate project from https://start.spring.io/
  - Create initial README.md
  - Define .gitignore
- API First (Version: 1.0.0)
  - Create controller with generic response
  - Define DTO models
  - Define error response model
  - Add model validation and Exception handler
  - Add OpenApi configuration and annotations
  - Add MVC tests
- Build H2 database (Version: 1.1.0)
  - Add dependencies
  - Add configuration in application.properties
  - Create entity and repository
  - Insert data on startup
  - Add JPA test (Not necessary, only to check is loaded)