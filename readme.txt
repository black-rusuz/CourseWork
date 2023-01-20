Методы:
1. countBuildPrice(long orderId) - Подсчёт стоимости сборки компьютера
2. buildPc(long orderId, String action, long partId) - Сборка компьютера
3. addPart(long orderId, long partId) - Добавить деталь в сборку
4. removePart(long orderId, long partId) - Удалить деталь из сборки
5. validateBuild(long orderId) - Проверить сборку
6. findBuild(long orderId) - Найти сборку
7. showMissingParts(long orderId) - Показать недостающие детали

Параметры:
Для environment.properties: -Denv
Для логгера: -Dlog4j2.configurationFile

Типы Датасорсов:
CSV
XML
JDBC

Примеры команд запуска:
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./PCAdvisor.jar XML countBuildPrice 41
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./PCAdvisor.jar XML buildPc 41 add 21
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./PCAdvisor.jar CSV addPart 41 31
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./PCAdvisor.jar CSV removePart 41 31
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./PCAdvisor.jar CSV validateBuild 41
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./PCAdvisor.jar JDBC findBuild 41
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./PCAdvisor.jar JDBC showMissingParts 41