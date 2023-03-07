Методы:
1. addPassenger(String from, String to, long passengerId, boolean isPaid)
        - Добавить пассажира на поезд. Если билет не оплачен, то он считается забронированным. Если поезд не найден, то билет не будет создан.
2. findTrain(String from, String to)
        - Найти подходящий поезд
3. calculateDuration(String departure, String arrival)
        - Расчёт продолжительности поездки
4. calculatePrice(double price, double discount)
        - Расчёт цены поездки с учётом скидки
5. viewPassengers(long trainId, long ticketId)
        - Просмотр пассажиров поезда
6. payTicket(long ticketId)
        - Оплатить забронированный билет

Параметры:
Для environment.properties: -Denv
Для логгера: -Dlog4j2.configurationFile

Типы Датасорсов:
CSV
XML
JDBC

Примеры команд запуска:
java -jar -Denv=./environment.properties -Dlog4j2.configurationFile=./log4j2.xml ./TrainPick.jar XML addPassenger Moscow Rostov-on-Don false