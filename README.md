# URL Shortener API

A REST API built with Spring Boot that shortens long URLs.

## Features
- Create short URLs with auto-generated or custom codes
- Redirect to original URL
- Track click counts
- Link expiry (TTL)
- Input URL validation

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok

## API Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/shorten | Create short URL |
| GET | /{shortCode} | Redirect to original URL |
| GET | /api/urls/{shortCode}/stats | Get click stats |
| DELETE | /api/urls/{shortCode} | Delete short URL |

## Setup
1. Clone the repository
2. Create MySQL database: `CREATE DATABASE url_shortener;`
3. Copy `application.properties.example` to `application.properties`
4. Fill in your MySQL credentials
5. Run the application