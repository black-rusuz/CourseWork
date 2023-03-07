Методы:
1. searchParts(String name, String vin) - Поиск деталей по названию и ВИН-номеру


Параметры:
Для environment.properties: -Denv
Для логгера: -Dlog4j2.configurationFile

Типы Датасорсов:
CSV
XML
JDBC

Примеры команд запуска:
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./GoslingDrive.jar XML searchParts bumper A1BX4