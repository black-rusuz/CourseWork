package ru.sfedu.goslingdrive.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.goslingdrive.model.bean.AutoPart;
import ru.sfedu.goslingdrive.utils.Constants;
import ru.sfedu.goslingdrive.utils.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ApiTest extends TestData {
    protected AbstractDataProvider dp;

    @Test
    void searchPartsPos() {
        Assertions.assertEquals(List.of(b1), dp.searchParts("bumper", null));
    }

    @Test
    void searchPartsNeg() {
        Assertions.assertEquals(List.of(), dp.searchParts("qweqwe", null));
    }


    @Test
    void searchByNamePos() {
        Assertions.assertEquals(List.of(e1), dp.searchByName("fan"));
    }

    @Test
    void searchByNameNeg() {
        Assertions.assertEquals(List.of(), dp.searchByName("asdasd"));
    }


    @Test
    void searchByVinPos() {
        Assertions.assertEquals(List.of(r1), dp.searchByVin("12asJLKJ2sd3"));
    }

    @Test
    void searchByVinNeg() {
        Assertions.assertEquals(List.of(), dp.searchByVin("zxczxc"));
    }


    @Test
    void modifyOrderPos() {
        List<AutoPart> parts = new ArrayList<>(p2);
        parts.add(e2);
        o2.setParts(parts);
        o2.setPrice(parts.stream().mapToDouble(AutoPart::getPrice).sum());
        Assertions.assertEquals(Optional.of(o2), dp.modifyOrder(Constants.ADD, e2.getId()));
        o2.setParts(p2);
        o2.setPrice(p2.stream().mapToDouble(AutoPart::getPrice).sum());
    }

    @Test
    void modifyOrderNeg() {
        Assertions.assertEquals(Optional.of(o2), dp.modifyOrder("Constants.ADD", 123));
    }


    @Test
    void addPartPos() {
        List<AutoPart> parts = new ArrayList<>(p2);
        parts.add(r2);
        o2.setParts(parts);
        Assertions.assertEquals(Optional.of(o2), dp.addPart(r2.getId()));
        o2.setParts(p2);
    }

    @Test
    void addPartNeg() {
        Assertions.assertEquals(Optional.of(o2), dp.addPart(123));
    }


    @Test
    void removePartPos() {
        List<AutoPart> parts = new ArrayList<>(p2);
        parts.remove(r2);
        o2.setParts(parts);
        Assertions.assertEquals(Optional.of(o2), dp.removePart(r2.getId()));
        o2.setParts(p2);
    }

    @Test
    void removePartNeg() {
        Assertions.assertEquals(Optional.of(o2), dp.removePart(123));
    }


    @Test
    void calculateTotalPricePos() {
        Assertions.assertEquals(o1.getPrice(), dp.calculateTotalPrice(o1.getId()));
    }

    @Test
    void calculateTotalPriceNeg() {
        Assertions.assertEquals(0, dp.calculateTotalPrice(0));
    }


    @BeforeEach
    void setUp() {
        dp.insertBodyPart(b1);
        dp.insertBodyPart(b2);

        dp.insertElectricPart(e1);
        dp.insertElectricPart(e2);

        dp.insertRunningPart(r1);
        dp.insertRunningPart(r2);

        dp.insertUser(u1);
        dp.insertUser(u2);

        dp.insertOrder(o1);
        dp.insertOrder(o2);
    }

    @AfterEach
    void tearDown() {
        dp.deleteBodyPart(b1.getId());
        dp.deleteBodyPart(b2.getId());

        dp.deleteElectricPart(e1.getId());
        dp.deleteElectricPart(e2.getId());

        dp.deleteRunningPart(r1.getId());
        dp.deleteRunningPart(r2.getId());

        dp.deleteUser(u1.getId());
        dp.deleteUser(u2.getId());

        dp.deleteOrder(o1.getId());
        dp.deleteOrder(o2.getId());
    }
}
