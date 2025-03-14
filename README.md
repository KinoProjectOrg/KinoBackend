
![Kino Project](https://static.vecteezy.com/system/resources/previews/044/514/545/non_2x/background-a-movie-theater-where-love-stories-are-unfolding-on-the-big-screen-and-the-smell-of-popcorn-fills-the-air-photo.jpg)
# Kino Project 

The underlying issue, in this student project, was to build a better reservation system for the cinema. This project reflects our bid on the solution to this issue. We have build a resservation system, with security, API fetching and frontend design, that we feel solve all the demands of the customer.

## Built With
- **Spring Boot** - Backend framework.
- **JavaScript** - Frontend
- **H2 Database** For testing
- **Mysql Database** For deployment.
- **Maven** - Dependency management.

## Getting Started

To run the application locally, follow these steps:

## Prerequisites
- Java 21 or later
- Maven
- Node.js
- An IDE (e.g., IntelliJ IDEA or Eclipse)

## Installation and Running
1. Clone the repository:
   ```bash
   git clone https://github.com/KinoProjectOrg/KinoBackend
   cd KinoBackend ```

2. Run the program through your IDE.

3. Access the application in your browser at http://localhost:8080/login

```java 
Before Usage, run http://localhost:8080/tool/fetch/movies, to get movies in database.
then run the data.sql script in your mysql, then all data should be set up.

Dummy Accounts you can log in with
username: admin
password: 123

username: Operator
password: 123

For a customer account create one on the login page. 
```

## Usage
    1. Open the application in your browser.
    2. Depending on your user, you will get certain pages shown
    3. As customer, you can select movie and reserv seats 
    4. As Admin, you can manange employees and reservations
    5. As Movie Operator, you can schedule showings of movies

## Configuration
- By default, the application uses an local mysql database.
- To connect to another database, update the `application.properties` file:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/kino
  spring.datasource.username=your-username
  spring.datasource.password=your-password

## Contributing
Contributions are welcome! We would love feedback \
Please follow these steps:

    1. Fork the repository.
    2. Create a feature branch: `git checkout -b feature/new-feature`.
    3. Commit your changes: `git commit -m "Add new feature"`.
    4. Push to the branch: `git push origin feature/new-feature`.
    5. Submit a pull request.

## Authors 

 * [BjerregaardGG](https://github.com/BjerregaardGG)
 * [NikolajPirum](https://github.com/NikolajPirum)
 * [Jhockinn](https://github.com/Jhockinn)
 * [Telity](https://github.com/Telity)
 * [Kvetny](https://github.com/kvetny)
 * [Danskode](https://github.com/danskode)
