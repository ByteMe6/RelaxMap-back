# RelaxMap — Бэкенд

REST API для приложения RelaxMap. Управляет локациями, отзывами, пользователями и изображениями.

[English](./README.md)

## Стек

| | |
|---|---|
| Язык | Java 21 |
| Фреймворк | Spring Boot 4 |
| Безопасность | Spring Security, JWT |
| БД | PostgreSQL, Spring Data JPA |
| Документация | SpringDoc OpenAPI (Swagger) |
| Утилиты | Lombok |
| Деплой | Docker, Docker Compose |

## Модули

- **auth** — регистрация, вход, refresh-токены
- **users** — профили пользователей
- **places** — CRUD локаций
- **reviews** — отзывы на локации
- **images** — загрузка и хранение изображений
- **security / jwt** — фильтры, токены доступа (15 мин) и обновления (7 дней)

## Запуск через Docker

```bash
git clone https://github.com/ByteMe6/RelaxMap-back
cd RelaxMap-back
cp .env.example .env   # заполни переменные
docker compose up -d
```

API будет доступно на `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui/index.html`

### Переменные окружения `.env`

```env
DB_USER=postgres
DB_PASSWORD=yourpassword
DB_PORT=5432
SPRING_PORT=8080
```

## Запуск вручную

```bash
./mvnw spring-boot:run
```

Требуется PostgreSQL с базой `relaxmap_db` и настроенные переменные `SPRING_DATASOURCE_*`.

## Репозитории

- [Фронтенд](https://github.com/ByteMe6/RelaxMap-front)
- [Бэкенд](https://github.com/ByteMe6/RelaxMap-back)

## Разработчики

<a href="https://github.com/ByteMe6/RelaxMap-back/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=ByteMe6/RelaxMap-back" />
</a>
