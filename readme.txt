Методы:
1. searchParts(String name, String vin) - Поиск деталей по названию и ВИН-номеру
2. searchByName(String name) - Поиск деталей по названию
3. searchByVin(String vin) - Поиск деталей по ВИН-номеру
4. modifyOrder(String action, long partId) - Изменить последний сохранённый заказ
5. addPart(long partId) - Добавить деталь в последний заказ
6. removePart(long partId) - Удалить деталь из последнего заказа
7. calculateTotalPrice(long orderId) - Подсчитать итоговую цену заказа


Параметры:
Для environment.properties: -Denv
Для логгера: -Dlog4j2.configurationFile

Типы Датасорсов:
CSV
XML
JDBC

Примеры команд запуска:
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./GoslingDrive.jar XML searchParts bumper A1BX4
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./GoslingDrive.jar XML searchByName bumper
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./GoslingDrive.jar CSV searchByVin A1BX4
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./GoslingDrive.jar CSV modifyOrder add 21
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./GoslingDrive.jar CSV addPart 21
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./GoslingDrive.jar JDBC removePart 21
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./GoslingDrive.jar JDBC calculateTotalPrice 52