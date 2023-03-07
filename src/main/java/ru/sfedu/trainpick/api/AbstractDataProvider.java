package ru.sfedu.trainpick.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.trainpick.model.HistoryContent;
import ru.sfedu.trainpick.model.bean.*;
import ru.sfedu.trainpick.utils.ConfigurationUtil;
import ru.sfedu.trainpick.utils.Constants;
import ru.sfedu.trainpick.utils.MongoUtil;
import ru.sfedu.trainpick.utils.ReflectUtil;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractDataProvider {
    protected final Logger log = LogManager.getLogger(this.getClass());

    private boolean MONGO_ENABLE = false;
    private String MONGO_ACTOR = "";

    public AbstractDataProvider() {
        try {
            MONGO_ENABLE = Boolean.parseBoolean(ConfigurationUtil.getConfigurationEntry(Constants.MONGO_ENABLE));
            MONGO_ACTOR = ConfigurationUtil.getConfigurationEntry(Constants.MONGO_ACTOR);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    // ABSTRACT GENERICS

    protected abstract <T> List<T> getAll(Class<T> type);

    protected abstract <T> T getById(Class<T> type, long id);

    protected abstract <T> long insert(Class<T> type, T bean);

    protected abstract <T> boolean delete(Class<T> type, long id);

    protected abstract <T> boolean update(Class<T> type, T bean);


    // SERVICE

    protected void sendLogs(String methodName, Object bean, boolean result) {
        HistoryContent historyContent = new HistoryContent(UUID.randomUUID(),
                this.getClass().getSimpleName(),
                LocalDateTime.now().toString(),
                MONGO_ACTOR,
                methodName,
                MongoUtil.objectToString(bean),
                result);
        if (MONGO_ENABLE) MongoUtil.saveToLog(historyContent);
    }

    protected <T> boolean hasSavedId(Class<T> type, long id) {
        T oldBean = getById(type, id);
        return ReflectUtil.getId(oldBean) != 0;
    }

    protected <T> String getNotFoundMessage(Class<T> type, long id) {
        return String.format(Constants.NOT_FOUND, type.getSimpleName(), id);
    }


    // USE CASES

    private Optional<Passenger> findPassenger(long passengerId) {
        Passenger p = getPassenger(passengerId);
        Passenger cp = getChildPassenger(passengerId);
        Passenger dp = getDiscountPassenger(passengerId);

        if (p.getId() == 0 && cp.getId() == 0 && dp.getId() == 0) {
            log.info(getNotFoundMessage(Passenger.class, passengerId));
            return Optional.empty();
        }

        List<Passenger> passengers = List.of(p, cp, dp);
        return passengers.stream().filter(e -> e.getId() != 0).findFirst();
    }

    public Optional<Ticket> addPassenger(String from, String to, long passengerId, boolean isPaid) {
        Optional<Ticket> result;
        Optional<Train> optionalTrain = findTrain(from, to);
        Optional<Passenger> optionalPassenger = findPassenger(passengerId);

        if (optionalTrain.isPresent() && optionalPassenger.isPresent()) {
            Train train = optionalTrain.get();
            String duration = calculateDuration(train.getDeparture(), train.getArrival());

            Passenger passenger = optionalPassenger.get();
            double cost = train.getPrice();
            if (passenger instanceof DiscountPassenger dp) {
                calculatePrice(train.getPrice(), dp.getDiscount());
            }

            Ticket ticket = new Ticket();
            ticket.setPassenger(passenger);
            ticket.setTrain(train);
            ticket.setDuration(duration);
            ticket.setCost(cost);
            ticket.setPaid(isPaid);
            insertTicket(ticket);
            result = Optional.of(ticket);
            log.info(String.format(Constants.TICKET, ticket));
        } else {
            result = Optional.empty();
        }
        return result;
    }

    public Optional<Train> findTrain(String from, String to) {
        List<Train> trains = getTrains();
        trains = trains.stream()
                .filter((e) -> e.getFrom().equalsIgnoreCase(from) && e.getTo().equalsIgnoreCase(to))
                .toList();
        Optional<Train> result;
        if (trains.isEmpty()) {
            result = Optional.empty();
            log.info(String.format(Constants.NO_TRAINS, from, to));
        } else {
            result = Optional.of(trains.get(0));
            log.info(String.format(Constants.TRAIN, trains.get(0)));
        }
        return result;
    }

    private LocalDateTime getDate(String dateTime) {
        String[] dateAndTime = dateTime.split(" ");
        String date = dateAndTime[0];
        String time = dateAndTime[1];
        List<Integer> dateNumbers = Arrays.stream(date.split("\\.")).map(Integer::parseInt).toList();
        List<Integer> timeNumbers = Arrays.stream(time.split(":")).map(Integer::parseInt).toList();
        return LocalDateTime.of(dateNumbers.get(2), dateNumbers.get(1), dateNumbers.get(0), timeNumbers.get(0), timeNumbers.get(1));
    }

    public String calculateDuration(String departure, String arrival) {
        LocalDateTime dep = getDate(departure);
        LocalDateTime arr = getDate(arrival);
        Duration duration = Duration.between(dep, arr);
        long days = duration.toDays();
        long hours = duration.toHours();
        hours = hours - days * 24;
        String dur = String.format(Constants.DURATION, days, hours);
        log.info(dur);
        return dur;
    }

    public double calculatePrice(double price, double discount) {
        double cost = price * discount;
        log.info(String.format(Constants.COST, cost));
        return cost;
    }

    public List<Passenger> viewPassengers(long trainId, long ticketId) {
        if (ticketId != 0) {
            payTicket(ticketId);
        }
        Train train = getTrain(trainId);
        List<Ticket> tickets = getTickets();
        List<Passenger> passengers = tickets.stream()
                .filter((e) -> e.getTrain().equals(train))
                .map(Ticket::getPassenger)
                .toList();
        log.info(String.format(Constants.PASSENGERS, train.getId()) + passengers.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n")));
        return passengers;
    }

    public Optional<Ticket> payTicket(long ticketId) {
        Optional<Ticket> result;
        Ticket ticket = getTicket(ticketId);
        if (ticket.getId() != 0) {
            ticket.setPaid(true);
            updateTicket(ticket);
            result = Optional.of(ticket);
            log.info(String.format(Constants.TICKET_PAID, ticket.getId()));
        } else {
            result = Optional.empty();
        }
        return result;
    }

    // CRUD

    public List<ChildPassenger> getChildPassengers() {
        return getAll(ChildPassenger.class);
    }

    public ChildPassenger getChildPassenger(long id) {
        return getById(ChildPassenger.class, id);
    }

    public long insertChildPassenger(ChildPassenger childPassenger) {
        return insert(ChildPassenger.class, childPassenger);
    }

    public boolean deleteChildPassenger(long id) {
        return delete(ChildPassenger.class, id);
    }

    public boolean updateChildPassenger(ChildPassenger childPassenger) {
        return update(ChildPassenger.class, childPassenger);
    }


    public List<DiscountPassenger> getDiscountPassengers() {
        return getAll(DiscountPassenger.class);
    }

    public DiscountPassenger getDiscountPassenger(long id) {
        return getById(DiscountPassenger.class, id);
    }

    public long insertDiscountPassenger(DiscountPassenger discountPassenger) {
        return insert(DiscountPassenger.class, discountPassenger);
    }

    public boolean deleteDiscountPassenger(long id) {
        return delete(DiscountPassenger.class, id);
    }

    public boolean updateDiscountPassenger(DiscountPassenger discountPassenger) {
        return update(DiscountPassenger.class, discountPassenger);
    }


    public List<Passenger> getPassengers() {
        return getAll(Passenger.class);
    }

    public Passenger getPassenger(long id) {
        return getById(Passenger.class, id);
    }

    public long insertPassenger(Passenger passenger) {
        return insert(Passenger.class, passenger);
    }

    public boolean deletePassenger(long id) {
        return delete(Passenger.class, id);
    }

    public boolean updatePassenger(Passenger passenger) {
        return update(Passenger.class, passenger);
    }


    public List<Ticket> getTickets() {
        return getAll(Ticket.class);
    }

    public Ticket getTicket(long id) {
        return getById(Ticket.class, id);
    }

    public long insertTicket(Ticket ticket) {
        return insert(Ticket.class, ticket);
    }

    public boolean deleteTicket(long id) {
        return delete(Ticket.class, id);
    }

    public boolean updateTicket(Ticket ticket) {
        return update(Ticket.class, ticket);
    }


    public List<Train> getTrains() {
        return getAll(Train.class);
    }

    public Train getTrain(long id) {
        return getById(Train.class, id);
    }

    public long insertTrain(Train train) {
        return insert(Train.class, train);
    }

    public boolean deleteTrain(long id) {
        return delete(Train.class, id);
    }

    public boolean updateTrain(Train train) {
        return update(Train.class, train);
    }
}
