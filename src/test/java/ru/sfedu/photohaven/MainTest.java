package ru.sfedu.photohaven;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.photohaven.api.AbstractDataProvider;
import ru.sfedu.photohaven.api.DataProviderXml;
import ru.sfedu.photohaven.utils.TestData;

import java.util.stream.Collectors;

public class MainTest extends TestData {
    final Logger log = LogManager.getLogger(this.getClass());
    final AbstractDataProvider dp = new DataProviderXml();

    @Test
    void test() {
        log.info(dp.getAccessories().stream().map(Object::toString).collect(Collectors.joining("\n")));
        log.info(dp.getBaskets().stream().map(Object::toString).collect(Collectors.joining("\n")));
        log.info(dp.getCameras().stream().map(Object::toString).collect(Collectors.joining("\n")));
        log.info(dp.getMaterials().stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    @BeforeEach
    void init() {
        dp.insertAccessory(a1);
        dp.insertAccessory(a2);
        dp.insertAccessory(a3);

        dp.insertBasket(b1);
        dp.insertBasket(b2);
        dp.insertBasket(b3);

        dp.insertCamera(c1);
        dp.insertCamera(c2);
        dp.insertCamera(c3);

        dp.insertMaterial(m1);
        dp.insertMaterial(m2);
        dp.insertMaterial(m3);
    }

    @AfterEach
    void clear() {
        dp.deleteAccessory(a1.getId());
        dp.deleteAccessory(a2.getId());
        dp.deleteAccessory(a3.getId());

        dp.deleteBasket(b1.getId());
        dp.deleteBasket(b2.getId());
        dp.deleteBasket(b3.getId());

        dp.deleteCamera(c1.getId());
        dp.deleteCamera(c2.getId());
        dp.deleteCamera(c3.getId());

        dp.deleteMaterial(m1.getId());
        dp.deleteMaterial(m2.getId());
        dp.deleteMaterial(m3.getId());
    }
}
