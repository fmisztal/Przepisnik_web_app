# Przepisnik

## Introduction

Przepisnik is a web application designed for searching, organizing, and sharing culinary recipes. It provides users with a user-friendly interface to easily add and manage their own recipes. Users can create an account, store private and favorite recipes, share recipes with others, create folders to organize recipes, and search for recipes using keywords and tags. The application supports both light and dark themes and allows users to add photos to their recipes.

## System Architecture

The application follows a three-tier client-server architecture. The client, implemented using HTML, CSS, and JavaScript, communicates with the server built in Java using the Spring framework. The server interacts with the MongoDB database and responds to client requests. The server-side implements the business logic, while the client-side handles data presentation and user interaction.

### Presentation Layer

The presentation layer is a web page that connects to the running application. It provides an attractive graphical user interface for easy utilization of all system features. The client-side is developed using HTML, CSS, and JavaScript. JavaScript handles user events such as button clicks, sends requests to the server, receives responses, and manipulates the HTML document to present the received data.

The server is implemented as a RESTful system, allowing direct access to public resources, such as recipe search, by sending HTTP requests to the server using the GET method. The server responds with JSON documents.

## Features

The application supports the following functionalities:

- User account creation
- Intuitive recipe creation using a user-friendly form
- Each account has a private recipe collection and a list of favorite recipes from other users
- Ability to create folders and add recipes to folders
- Recipe search within the user's collection using keywords and tags (e.g., vegetarian, spicy)
- Sharing recipes with other users
- Searching for recipes shared by other users using keywords and tags (e.g., vegetarian, spicy, breakfast)
- Unauthenticated users can browse recipes shared by users
- Light and dark themes
- Ability to add photos to recipes

## Security

The application ensures security through the following measures:

- Access to specific functionalities is restricted to authenticated users.
- User passwords are encrypted and stored securely in the database.
- The application provides an easy-to-use and secure login and registration process.
- Authorization is implemented using JSON Web Tokens (JWT) and cookies.
- The application prevents unauthorized access to protected resources.
