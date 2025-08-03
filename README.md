# 📘 Elasticsearch Course Search

A Spring Boot application that indexes and searches course data using Elasticsearch.  
Developed as part of an academic assignment.

---

## ✅ Features

- Full-text search across course fields (`title`, `description`, `category`)
- Filtering by:
  - Age range (`minAge`, `maxAge`)
  - Price range (`minPrice`, `maxPrice`)
  - Start date (`startDate`)
- Sorting by numeric fields (e.g., `price`)
- Pagination support

---

## 🚀 Getting Started

### 🔧 Prerequisites

- Java 21
- Maven
- Elasticsearch 7.17 running locally at `http://localhost:9200`

---

### 📦 Setup & Run

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/elasticsearch-course-search.git
   cd elasticsearch-course-search
   ```

2. **Configure Elasticsearch URI**

   Update `src/main/resources/application.properties`:
   ```properties
   spring.elasticsearch.uris=http://localhost:9200
   ```

3. **Run the application**

   ```bash
   ./mvnw spring-boot:run
   ```

   Or using Maven directly:

   ```bash
   mvn spring-boot:run
   ```

---

## 📡 API Reference

### `GET /api/search`

#### 🔍 Query Parameters:

| Parameter    | Type     | Description                                  |
|--------------|----------|----------------------------------------------|
| `q`          | `String` | Full-text keyword (title, desc, category)     |
| `minAge`     | `int`    | Filter: minimum age                          |
| `maxAge`     | `int`    | Filter: maximum age                          |
| `minPrice`   | `double` | Filter: minimum price                        |
| `maxPrice`   | `double` | Filter: maximum price                        |
| `startDate`  | `long`   | Filter: start date (epoch milliseconds)      |
| `sortField`  | `String` | Sort field (e.g., `price`, `minAge`)         |
| `sortOrder`  | `String` | `asc` or `desc`                              |
| `page`       | `int`    | Page number (0-indexed)                      |
| `size`       | `int`    | Results per page                             |

#### 🧪 Example:

```bash
curl "http://localhost:8080/api/search?q=math&minPrice=100&maxPrice=500&sortField=price&sortOrder=asc&page=0&size=10"
```

---

## 📁 Sample Data Upload

If you have a `bulk_courses.json` file:

```bash
curl -XPOST "http://localhost:9200/courses/_bulk?pretty"   -H 'Content-Type: application/json'   --data-binary @bulk_courses.json
```

✅ Make sure your data uses ISO 8601 dates or epoch millis, depending on your mapping.

---

## 💡 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com.assignment.elasticsearch_course_search/
│   │       ├── controller/       # REST controller
│   │       ├── service/          # Business logic and search queries
│   │       ├── document/         # Elasticsearch document mapping
│   │       └── ElasticsearchCourseSearchApplication.java
│   └── resources/
│       ├── application.properties
│       └── (optional) application.yml
└── test/
```

---

## 👨‍💻 Author

**Prashant Shukla**  
Final Year Student  
Rajkiya Engineering College, Sonbhadra

---

## 📜 License

This project is created for academic purposes only.