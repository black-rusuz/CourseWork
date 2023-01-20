package ru.sfedu.pcadvisor.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.pcadvisor.model.bean.Part;
import ru.sfedu.pcadvisor.utils.Constants;
import ru.sfedu.pcadvisor.utils.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public abstract class ApiTest extends TestData {
    protected AbstractDataProvider dp;

    @Test
    void countBuildPricePos() {
        Assertions.assertEquals(17470, dp.countBuildPrice(o1.getId()));
    }

    @Test
    void countBuildPriceNeg() {
        Assertions.assertEquals(0, dp.countBuildPrice(1));
    }


    @Test
    void buildPcPos() {
        List<Part> parts = new ArrayList<>(p2);
        parts.add(r1);
        o2.setParts(parts);
        Assertions.assertEquals(Optional.of(o2), dp.buildPc(o2.getId(), Constants.ADD, r1.getId()));
        o2.setParts(p2);
    }

    @Test
    void buildPcNeg() {
        Assertions.assertNotEquals(Optional.of(o2), dp.buildPc(o2.getId(), Constants.ADD, r1.getId()));
        o2.setParts(p2);
    }


    @Test
    void addPartPos() {
        List<Part> parts = new ArrayList<>(p2);
        parts.add(r1);
        o2.setParts(parts);
        Assertions.assertEquals(Optional.of(o2), dp.addPart(o2.getId(), r1.getId()));
        o2.setParts(p2);
    }

    @Test
    void addPartNeg() {
        Assertions.assertNotEquals(Optional.of(o2), dp.addPart(o2.getId(), r1.getId()));
        o2.setParts(p2);
    }


    @Test
    void removePartPos() {
        List<Part> parts = new ArrayList<>(p2);
        parts.remove(c2);
        o2.setParts(parts);
        Assertions.assertEquals(Optional.of(o2), dp.removePart(o2.getId(), c2.getId()));
        o2.setParts(p2);
    }

    @Test
    void removePartNeg() {
        Assertions.assertEquals(Optional.of(o2), dp.removePart(o2.getId(), r1.getId()));
        o2.setParts(p2);
    }


    @Test
    void validateBuildPos() {
        Assertions.assertTrue(dp.validateBuild(o1.getId()));
    }

    @Test
    void validateBuildNeg() {
        Assertions.assertFalse(dp.validateBuild(o2.getId()));
    }


    @Test
    void findBuildPos() {
        Assertions.assertEquals(Optional.of(o1), dp.findBuild(o1.getId()));
    }

    @Test
    void findBuildNeg() {
        Assertions.assertEquals(Optional.empty(), dp.findBuild(0));
    }


    @Test
    void showMissingPartsPos() {
        Assertions.assertEquals(List.of(r1, r2), dp.showMissingParts(o2.getId()));
    }

    @Test
    void showMissingPartsNeg() {
        Assertions.assertEquals(List.of(), dp.showMissingParts(o1.getId()));
    }


    @BeforeEach
    void setUp() {
        dp.insertCpu(c1);
        dp.insertCpu(c2);

        dp.insertMotherboard(m1);
        dp.insertMotherboard(m2);

        dp.insertRam(r1);
        dp.insertRam(r2);

        dp.insertOrder(o1);
        dp.insertOrder(o2);
    }

    @AfterEach
    void tearDown() {
        dp.deleteCpu(c1.getId());
        dp.deleteCpu(c2.getId());

        dp.deleteMotherboard(m1.getId());
        dp.deleteMotherboard(m2.getId());

        dp.deleteRam(r1.getId());
        dp.deleteRam(r2.getId());

        dp.deleteOrder(o1.getId());
        dp.deleteOrder(o2.getId());
    }
}
