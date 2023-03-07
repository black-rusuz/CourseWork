package ru.sfedu.trainpick.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.trainpick.utils.Constants;
import ru.sfedu.trainpick.utils.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ApiTest extends TestData {
    protected AbstractDataProvider dp;

    @Test
    void test() {
        dp = new DataProviderXml();
    }

    @BeforeEach
    void setUp() {
//        dp.insertBodyPart(b1);
//        dp.insertBodyPart(b2);
//
//        dp.insertElectricPart(e1);
//        dp.insertElectricPart(e2);
//
//        dp.insertRunningPart(r1);
//        dp.insertRunningPart(r2);
//
//        dp.insertUser(u1);
//        dp.insertUser(u2);
//
//        dp.insertOrder(o1);
//        dp.insertOrder(o2);
    }

    @AfterEach
    void tearDown() {
//        dp.deleteBodyPart(b1.getId());
//        dp.deleteBodyPart(b2.getId());
//
//        dp.deleteElectricPart(e1.getId());
//        dp.deleteElectricPart(e2.getId());
//
//        dp.deleteRunningPart(r1.getId());
//        dp.deleteRunningPart(r2.getId());
//
//        dp.deleteUser(u1.getId());
//        dp.deleteUser(u2.getId());
//
//        dp.deleteOrder(o1.getId());
//        dp.deleteOrder(o2.getId());
    }
}
