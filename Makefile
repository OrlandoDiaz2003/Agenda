-include .env
export

.PHONY: run compile test

docker-test:
	docker compose -f docker-compose.local.yml up --build

docker-db:
	docker exec -it mariadb-agenda mariadb -u agenda_user_test -p${DB_PASSWORD_LOCAL}

docker-clean:
	docker compose down -v

run:
	./mvnw spring-boot:run

compile:
	./mvnw compile

package:
	./mvnw package

clean:
	./mvnw clean

test:
	./mvnw test

build: clean package
