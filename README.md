# Tiekėjų valdymo sistema

## Local development

Compile the project:

```bash
mvn clean compile
```

Start the application with Payara Micro:

```bash
mvn payara-micro:dev
```

Check the health endpoint:

```bash
curl http://localhost:8080/api/health
```

Expected response:

```json
{"status":"ok"}
```

Open the application pages:

- http://localhost:8080/
- http://localhost:8080/suppliers/list.xhtml

## Database

Start PostgreSQL:

```bash
docker compose up -d
```

Stop PostgreSQL:

```bash
docker compose down
```

Connect with psql:

```bash
docker compose exec postgres psql -U tiekejai -d tiekejai_db
```
