# Contributing to Movie Search Application

Thank you for considering contributing to the **Movie Search Application**! We appreciate your interest in improving the project and helping it grow. Below are the guidelines and steps to contribute to this project.

The **Movie Search Application** is a containerized full-stack movie search application built using **React**, **Spring Boot**, and **Elasticsearch**. This project allows users to search for movies with real-time fuzzy search capabilities, browse results with pagination, and manage movie data through RESTful APIs.

## Code of Conduct

Please note that this project adheres to a [Code of Conduct](./CODE_OF_CONDUCT.md). By participating in this project, you agree to abide by its terms. Please read it carefully to ensure a positive and respectful experience for everyone involved.

## How to Contribute

We welcome contributions of all kinds! Here's how you can get involved:

### Reporting Bugs
If you find a bug in the project, please report it by opening a new **Issue**. Provide as much detail as possible to help us understand the problem, including:
- Steps to reproduce the bug
- Expected behavior
- Actual behavior
- Your environment (OS, version of the software, etc.)

### Suggesting Features
If you have an idea for a new feature, please open a **Feature Request** issue. Provide a clear description of the feature and why it would be valuable.

### Contributing Code
To contribute code, follow these steps:
1. **Fork the repository**: Click on the **Fork** button at the top of the repository page to create your copy of the repository.
2. **Clone your fork**: On your machine, clone the repository with:
   ```bash
   git clone https://github.com/yourusername/movie-app-react.git
   ```
3. Create a branch: Always create a new branch to work on your contribution. Avoid using the main or master branch for your work.
```
git checkout -b feature-name
```
4. Make your changes: Implement the feature or bug fix you want to contribute. For example, you can enhance the fuzzy search functionality or improve pagination.
5. Test your changes: Run both the front-end (React) and back-end (Spring Boot) to ensure that everything works as expected. If you added or modified any RESTful APIs, ensure that the endpoints return the expected data.
6. Commit your changes: Write a meaningful commit message explaining what you've done. Follow the commit message conventions if provided by the project.
```
git add .
git commit -m "Add movie filtering functionality"
```
7. Push your changes: Push your changes to your forked repository.
```
git push origin feature/feature-name
```
8. Open a Pull Request: Go to the original repository, and open a Pull Request from your branch. Describe your changes in the PR and reference the issue number (if applicable).
Once your Pull Request is submitted, the maintainers will review your changes and give feedback or approve the PR.

## Testing Your Changes
Before submitting a Pull Request, please ensure that your code passes the project’s test suite. Here are the steps to run tests locally:
1. Front-End (React)
Install the necessary dependencies:
```
npm install
```
Run the development server:
```
npm run dev
```
Visit http://localhost:5173 in your browser to test your changes.
2. Back-End (Spring Boot)
Install the necessary dependencies using Maven or Gradle:
```
mvn clean install
```

Run the application:
```
mvn spring-boot:run
```
The back-end server will run on http://localhost:8083. Make sure your changes work with the provided RESTful APIs.
If you added new features, please include tests to verify that your changes work as expected.

## Coding Guidelines
### Commit Messages
Follow these rules for writing commit messages:
Use present tense (e.g., "Fix bug" instead of "Fixed bug").
Use imperative mood (e.g., "Add test for fuzzy search" instead of "Added test").
Break the message into a short summary (50 characters or less) followed by a detailed description (if necessary).

## Acknowledgments
Special thanks to the open-source community for their contributions and tools like Elasticsearch, Spring Boot, and React that made this project possible.
Thanks to all contributors who improve this project!

### Additional Resources

[React Documentation](https://reactjs.org/docs/getting-started.html)
[Spring Boot Documentation](https://spring.io/projects/spring-boot)
[Elasticsearch Documentation](https://www.elastic.co/guide/en/elasticsearch/reference/index.html)

We look forward to your contributions!
