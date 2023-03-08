package ru.sfedu.trainpick.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.trainpick.TrainPickClient;
import ru.sfedu.trainpick.model.bean.Passenger;
import ru.sfedu.trainpick.model.bean.Ticket;
import ru.sfedu.trainpick.model.bean.Train;
import ru.sfedu.trainpick.utils.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ApiTest extends TestData {
    protected AbstractDataProvider dp;

    void testMain() {
        TrainPickClient.main("XML addPassenger Moscow Rostov-on-Don 11 false".split(" "));
        TrainPickClient.main("XML findTrain Moscow Rostov-on-Don".split(" "));
        TrainPickClient.main("XML calculateDuration 21.03.2022//00:00 21.03.2022//18:00".split(" "));
        TrainPickClient.main("XML calculatePrice 5000 0.5".split(" "));
        TrainPickClient.main("XML viewPassengers 41".split(" "));
        TrainPickClient.main("XML payTicket 51".split(" "));
    }

    @Test
    void addPassengerPos() {
        Optional<Ticket> actualTicket = dp.addPassenger(tr1.getFrom(), tr1.getTo(), cp1.getId(), false);
        String duration = dp.calculateDuration(tr1.getDeparture(), tr1.getArrival());
        Optional<Ticket> expectedTicket = Optional.of(new Ticket(actualTicket.get().getId(), cp1, tr1, duration, ti1.getCost(), false));
        Assertions.assertEquals(expectedTicket, actualTicket);
        dp.deleteTicket(actualTicket.get().getId());
    }

    @Test
    void addPassengerNeg() {
        Optional<Ticket> ticket = dp.addPassenger("", "", 0, false);
        Ticket actualTicket = ticket.orElse(null);
        Assertions.assertNull(actualTicket);
    }

    @Test
    void findTrainPos() {
        Optional<Train> actualTrain = dp.findTrain(tr1.getFrom(), tr1.getTo());
        Optional<Train> expectedTrain = Optional.of(tr1);
        Assertions.assertEquals(expectedTrain, actualTrain);
    }

    @Test
    void findTrainNeg() {
        Optional<Train> train = dp.findTrain("", "");
        Train actualTrain = train.orElse(null);
        Assertions.assertNull(actualTrain);
    }

    @Test
    void calculateDurationPos() {
        String expectedDuration = "1 day(s) 19 hour(s)";
        String actualDuration = dp.calculateDuration(tr2.getDeparture(), tr2.getArrival());
        Assertions.assertEquals(expectedDuration, actualDuration);
    }

    @Test
    void calculateDurationNeg() {
        Assertions.assertThrows(NumberFormatException.class, () -> dp.calculateDuration("", ""));
    }

    @Test
    void calculatePricePos() {
        double expectedPrice = tr1.getPrice();
        double actualPrice = dp.calculatePrice(tr1.getPrice(), 0);
        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void calculatePriceNeg() {
        double unexpectedPrice = tr1.getPrice();
        double actualPrice = dp.calculatePrice(tr1.getPrice(), 0.5);
        Assertions.assertNotEquals(unexpectedPrice, actualPrice);
    }

    @Test
    void viewPassengersPos() {
        List<Passenger> expectedPassengers = List.of(ps1, dp2);
        List<Passenger> actualPassengers = dp.viewPassengers(tr1.getId(), 0);
        Assertions.assertEquals(expectedPassengers, actualPassengers);
    }

    @Test
    void viewPassengersNeg() {
        List<Passenger> expectedPassengers = new ArrayList<>();
        List<Passenger> actualPassengers = dp.viewPassengers(tr2.getId(), 0);
        Assertions.assertEquals(expectedPassengers, actualPassengers);
    }

    @Test
    void payTicketPos() {
        Ticket expectedTicket = ti1;
        expectedTicket.setPaid(true);
        Optional<Ticket> ticket = dp.payTicket(ti1.getId());
        Ticket actualTicket = ticket.orElse(null);
        Assertions.assertEquals(expectedTicket, actualTicket);
    }

    @Test
    void payTicketNeg() {
        Optional<Ticket> ticket = dp.payTicket(0);
        Ticket actualTicket = ticket.orElse(null);
        Assertions.assertNull(actualTicket);
    }

    @BeforeEach
    void setUp() {
        dp.insertChildPassenger(cp1);
        dp.insertChildPassenger(cp2);

        dp.insertDiscountPassenger(dp1);
        dp.insertDiscountPassenger(dp2);

        dp.insertPassenger(ps1);
        dp.insertPassenger(ps2);

        dp.insertTrain(tr1);
        dp.insertTrain(tr2);

        dp.insertTicket(ti1);
        dp.insertTicket(ti2);
    }

    @AfterEach
    void tearDown() {
        dp.deleteChildPassenger(cp1.getId());
        dp.deleteChildPassenger(cp2.getId());

        dp.deleteDiscountPassenger(dp1.getId());
        dp.deleteDiscountPassenger(dp2.getId());

        dp.deletePassenger(ps1.getId());
        dp.deletePassenger(ps2.getId());

        dp.deleteTrain(tr1.getId());
        dp.deleteTrain(tr2.getId());

        dp.deleteTicket(ti1.getId());
        dp.deleteTicket(ti2.getId());
    }
}
