package ru.sfedu.trainpick.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.trainpick.utils.TestData;

public abstract class ApiTest extends TestData {
    protected AbstractDataProvider dp;

    @Test
    void test() {
        dp.addPassenger(tr1.getFrom(), tr1.getTo(), cp1.getId(), false);
        dp.findTrain(tr1.getFrom(), tr1.getTo());
        dp.calculateDuration(tr2.getDeparture(), tr2.getArrival());
        dp.calculatePrice(tr2.getPrice(), 0.5);
        dp.viewPassengers(tr1.getId(), 0);
        dp.payTicket(ti1.getId());
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
