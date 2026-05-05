# Redis Visual Demo

Простой учебный проект для понимания того, как работает Redis.

Проект показывает Redis не через сложную теорию, а через понятный frontend-интерфейс. Пользователь открывает страницу в браузере, нажимает кнопки и сразу видит, как данные сохраняются, читаются, удаляются и автоматически исчезают через TTL.

---

## Цель проекта

Главная цель проекта — объяснить Redis человеку, который раньше с ним не работал.

В проекте можно на практике увидеть:

- как Redis хранит данные по принципу key-value;
- как получить данные обратно из Redis;
- как удалить данные из Redis;
- как работает TTL;
- как Redis используется для счётчиков;
- как Redis может хранить быстрый статус курьера в delivery-приложении.

---

## Как работает проект

Архитектура очень простая:

```text
Frontend page
     |
     v
Spring Boot Backend
     |
     v
Redis
```

Пользователь работает только с браузером.

Например:

```text
Нажал "Сохранить" → backend отправил данные в Redis
Нажал "Получить" → backend прочитал данные из Redis
Нажал "Сохранить код" → Redis сохранил код на 10 секунд
Нажал "+1" → Redis увеличил счётчик
```

---

## Используемые технологии

- Java 17
- Spring Boot 3.3.0
- Spring Web
- Spring Data Redis
- Redis 7 Alpine
- Docker Compose
- HTML
- CSS
- JavaScript

---

## Возможности проекта

### 1. Простое хранение данных

Redis сохраняет обычное значение по ключу.

Пример:

```text
demo:username -> Shaxriyor
```

Можно:

- сохранить имя;
- получить имя;
- удалить имя.

---

### 2. TTL

TTL означает, что данные живут только определённое время.

Пример:

```text
demo:verification-code -> 123456
```

Если указать 10 секунд, Redis сам удалит этот ключ через 10 секунд.

Это удобно для:

- email verification code;
- SMS code;
- password reset token;
- temporary session.

---

### 3. Counter

Redis удобно использовать как быстрый счётчик.

Пример:

```text
demo:click-counter -> 1
demo:click-counter -> 2
demo:click-counter -> 3
```

Это можно использовать для:

- количества кликов;
- попыток входа;
- rate limiting;
- просмотров;
- статистики.

---

### 4. Courier Status

Проект также показывает пример из delivery-приложения.

Redis хранит быстрый статус курьера:

```text
demo:courier:1
    name   -> Ali Courier
    status -> ONLINE
    lat    -> 41.3111
    lng    -> 69.2797
```

Статусы:

```text
ONLINE
BUSY
ON_DELIVERY
OFFLINE
```

Такой подход полезен, потому что статус курьера часто меняется. Нет смысла каждый раз нагружать PostgreSQL, если можно быстро обновлять Redis.

---

## Структура проекта

```text
redis-visual-demo
├── docker-compose.yml
├── pom.xml
└── src
    └── main
        ├── java
        │   └── org
        │       └── gatewayservice
        │           ├── RedisVisualDemoApplication.java
        │           ├── RedisDemoController.java
        │           └── RedisDemoService.java
        └── resources
            ├── application.properties
            └── static
                ├── index.html
                ├── style.css
                └── app.js
```

---

## Запуск проекта

### 1. Клонировать проект

```bash
git clone https://github.com/YOUR_USERNAME/redis-visual-demo.git
cd redis-visual-demo
```

---

### 2. Запустить Redis через Docker

```bash
docker compose up -d
```

Проверить контейнер:

```bash
docker ps
```

Проверить Redis:

```bash
docker exec -it redis-simple-test redis-cli ping
```

Если Redis работает, ответ будет:

```text
PONG
```

---

### 3. Запустить Spring Boot приложение

```bash
mvn clean spring-boot:run
```

---

### 4. Открыть проект в браузере

```text
http://localhost:8080
```

---

## Проверка Redis вручную

Можно зайти в Redis CLI:

```bash
docker exec -it redis-simple-test redis-cli
```

Посмотреть все ключи:

```bash
KEYS *
```

Получить имя:

```bash
GET demo:username
```

Получить счётчик:

```bash
GET demo:click-counter
```

Получить данные курьера:

```bash
HGETALL demo:courier:1
```

Проверить TTL кода:

```bash
TTL demo:verification-code
```

Выйти из Redis CLI:

```bash
exit
```

---

## Примеры Redis keys

В проекте используются такие ключи:

```text
demo:username
demo:verification-code
demo:click-counter
demo:courier:1
```

---

## Почему Redis полезен

Redis не заменяет обычную базу данных.

Например, PostgreSQL лучше подходит для постоянных данных:

```text
users
orders
payments
delivery history
couriers
restaurants
```

Redis лучше подходит для быстрых и временных данных:

```text
verification codes
online status
last location
temporary sessions
rate limits
counters
cache
```

---

## Redis в Delivery App

В реальном delivery-приложении Redis можно использовать для:

```text
courier online/offline status
courier live location
active delivery tracking
verification code
password reset token
login attempts
rate limiting
temporary cache
```

Пример:

```text
courier:123:status -> ONLINE
courier:123:location -> 41.3111,69.2797
order:456:tracking -> ACTIVE
login_attempts:user:789 -> 3
```

---

## Главная идея проекта

Этот проект помогает понять Redis через практику.

Не нужно сразу изучать сложную документацию. Достаточно открыть сайт, нажимать кнопки и смотреть, как данные появляются в Redis.

---

## Author

Created by **Shaxriyor**

GitHub: [@RahimovShaxriyor](https://github.com/RahimovShaxriyor)