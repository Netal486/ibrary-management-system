# Library Management System API
This is a simple RESTful API for managing books in a library using Spring Boot. The API allows users to perform CRUD operations on books, which include adding, updating, deleting, and retrieving book details. The application also implements basic authentication using Spring Security.a
---------------------------------------------------------------------------------------

1 . Prerequisites
Before getting started, ensure you have the following installed:
- **JDK 17** or above
- **Maven** 
- **Spring Boot 3.3.5** or above
- **JVM-compatible IDE** (e.g., IntelliJ IDEA, Eclipse)
  
You can check your installed versions by running command:
java -version
mvn -v

To get a local copy of the project running on your machine, follow these steps by running command:
Clone the repository
git clone https://github.com/your-username/ibrary-management-system.git
cd ibrary-management-system
Install dependencies
For Maven:
mvn clean install
Running the Application
Running the application locally
After cloning the repository and installing dependencies, run the application with the following command:
For Maven:
mvn spring-boot:run

API Endpoints
The following are the available endpoints to interact with the library management system:
Note : For access all url add authorization with username : netal and password :netal@123
1. GET All Books List
Retrieve a list of all books.
Request url:   http://localhost:9091/api/books
Method: GET
Response:
[
  {
    "id": 1,
    "title": "Book Title",
    "author": "Author Name",
    "publicationYear": 2020
  },
]
2. GET  Book By Id
Retrieve a specific book by its ID.
Request url : http://localhost:9091/api/books/5
Method : Get
Response:
{
  "id": 1,
  "title": "Book Title",
  "author": "Author Name",
  "publicationYear": 2020
}
3. POST  book
Create a new book.
Request: http://localhost:9091/api/books/createbook
Method : POST
Content-Type: application/json
{
  "title": "New Book",
  "author": "New Author",
  "publicationYear": 2024
}
Response:
{
  "message": "created successfully."
}
4. PUT API for update book 
Update an existing book's details.
Request url : http://localhost:9091/api/books/{id}
Method : PUT
Content-Type: application/json
{
  "title": "Updated Book Title",
  "author": "Updated Author",
  "publicationYear": 2024
}
Response:
{
  "message": "updated successfully."
}
5. Delete a book by ID.
Request url: http://localhost:9091/api/books/{id}
Response:
{
  "message": "deleted successfully."
}
________________________________________

For Api documentation by using swagger follow below url:
http://localhost:9091/swagger-ui/index.html
Note : For access all url add authorization with username : netal and password :netal@123

