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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
