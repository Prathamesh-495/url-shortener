# 🔗 URL Shortener

A full-stack URL shortening service built with **Spring Boot** and **MySQL**, deployed live on Railway.

🌐 **Live Demo:** [url-shortener-production-1439.up.railway.app](https://url-shortener-production-1439.up.railway.app)  
📦 **GitHub:** [github.com/Prathamesh-495/url-shortener](https://github.com/Prathamesh-495/url-shortener)
 
---

## ✨ Features

- 🔗 **Shorten URLs** — Generate a unique 6-character short code instantly
- ✏️ **Custom Short Codes** — Choose your own short code (e.g. `/pintola`)
- ⏰ **Link Expiry (TTL)** — Set an expiry date after which the link becomes inactive
- 📊 **Click Tracking** — Track how many times a short URL has been visited
- 🛡️ **Input Validation** — Rejects invalid URLs with clean error messages
- 🗑️ **Delete URLs** — Remove short URLs when no longer needed
- 🌐 **Simple Frontend** — Clean UI to shorten URLs without needing Postman

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot 4 |
| Database | MySQL 8 |
| ORM | Spring Data JPA / Hibernate |
| Validation | Jakarta Validation |
| Boilerplate | Lombok |
| Deployment | Railway |
| Frontend | HTML, CSS, JavaScript |
 
---

## 📁 Project Structure

```
src/main/java/com/example/url_shortner/
├── controller/
│   ├── UrlMappingController.java   # API endpoints
│   ├── RedirectController.java     # Handles short URL redirects
│   └── HomeController.java         # Serves frontend
├── DTO/
│   ├── UrlRequest.java             # Request body
│   └── UrlResponse.java            # Response body
├── entity/
│   └── UrlMapping.java             # Database entity
├── repository/
│   └── UrlMappingRepository.java   # JPA repository
├── service/
│   └── UrlMappingService.java      # Business logic
├── exception/
│   └── GlobalExceptionHandler.java # Centralized error handling
└── config/
    └── WebConfig.java              # Static resource config
```
 
---

## 🚀 API Endpoints

| Method | URL | Description | Request Body |
|---|---|---|---|
| `POST` | `/api/shorten` | Create short URL | `UrlRequest` |
| `GET` | `/{shortCode}` | Redirect to original URL | — |
| `GET` | `/api/urls/{shortCode}/stats` | Get click stats | — |
| `DELETE` | `/api/urls/{shortCode}` | Delete short URL | — |

### Request Body Example

```json
{
    "originalUrl": "https://www.amazon.in/very/long/product/url",
    "customUrl": "pintola",
    "expiresAt": "2026-12-31T23:59:59"
}
```

### Response Example

```json
{
    "shortCode": "pintola",
    "originalUrl": "https://www.amazon.in/very/long/product/url",
    "shortUrl": "https://url-shortener-production-1439.up.railway.app/pintola",
    "createdAt": "2026-03-14T10:10:17",
    "clickCount": 0
}
```
 
---

## ⚙️ Local Setup

### Prerequisites
- Java 21+
- Maven
- MySQL 8+

### Steps

1. **Clone the repository**
```bash
git clone https://github.com/Prathamesh-495/url-shortener.git
cd url-shortener
```

2. **Create MySQL database**
```sql
CREATE DATABASE url_shortener;
```

3. **Configure application properties**
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Edit `application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener
spring.datasource.username=root
spring.datasource.password=yourpassword
```

4. **Run the application**
```bash
./mvnw spring-boot:run
```

5. **Open in browser**
```
http://localhost:8080
```
 
---

## 🏗️ Architecture

```
Client
  │
  ├── GET /              → HomeController    → Serves index.html
  ├── POST /api/shorten  → UrlMappingController → UrlMappingService → MySQL
  ├── GET /{shortCode}   → RedirectController → 302 Redirect
  └── GET /api/urls/{shortCode}/stats → UrlMappingController → MySQL
```
 
---

## 🔒 Security Highlights

- MySQL credentials stored as environment variables (never in source code)
- `SecureRandom` used for short code generation (not predictable `Random`)
- Input URL validated with `@URL` annotation before processing
- Database IDs never exposed in API responses (DTO pattern)
- Duplicate custom code detection before database insert

---

## 📸 Screenshots

### Frontend UI
> Clean one-page interface to shorten URLs with optional custom code and expiry date.

### API Response
> JSON response with short URL, click count, and creation timestamp.
 
---

## 📝 License

This project is open source and available under the [MIT License](LICENSE).
 