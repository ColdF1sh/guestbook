Версії інструментів

JDK: 21

Maven: 3.9.x

Команда запуску
mvn jetty:run

База даних

URL:

jdbc:h2:file:./data/guest;AUTO_SERVER=TRUE


Файл БД:

/data/guest.mv.db

Доступні ендпоїнти

/ — головна сторінка з формою та списком відгуків

GET /comments — отримання списку відгуків у форматі JSON

POST /comments — додавання нового відгуку
