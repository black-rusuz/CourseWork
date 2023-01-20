Методы:
1. viewProducts(String filter, long productId) — Показать доступные товары и заказать выбранный
2. filterView(String filter) — Фильтр товаров
3. orderProduct(long productId) — Заказ выбранного товара
4. viewUserData(long userId, boolean calculate) - Просмотр информации о пользователе
5. viewUserOrders(long userId) - Просмотр товаров, заказанных пользователем с указанным ID
6. calculateAmount(long userId) - Подсчёт итоговой суммы, потраченной на все заказы выбранного пользователя

Параметры:
Для environment.properties: -Denv
Для логгера: -Dlog4j2.configurationFile

Типы Датасорсов:
CSV
XML
JDBC

Примеры команд запуска:
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./ArtSale.jar XML viewProducts any 21
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./ArtSale.jar XML filterView product
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./ArtSale.jar CSV orderProduct 21
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./ArtSale.jar CSV showRelated 21
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./ArtSale.jar CSV viewUserData 12 true
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./ArtSale.jar JDBC viewUserOrders 12
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./ArtSale.jar JDBC calculateAmount 12