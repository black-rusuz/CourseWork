package ru.sfedu.artsale.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.artsale.Constants;
import ru.sfedu.artsale.model.bean.*;
import ru.sfedu.artsale.utils.TestData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

abstract class AbstractTest extends TestData {
    AbstractDataProvider dataProvider;

    @BeforeEach
    void setUp() throws IOException {
        dataProvider.insertUser(u1);
        dataProvider.insertUser(u2);

        dataProvider.insertProduct(p1);
        dataProvider.insertProduct(p2);
        dataProvider.insertProduct(p3);
        dataProvider.insertProduct(p4);

        dataProvider.insertCreationKit(ck1);
        dataProvider.insertCreationKit(ck2);
        dataProvider.insertCreationKit(ck3);
        dataProvider.insertCreationKit(ck4);

        dataProvider.insertEndProduct(ep1);
        dataProvider.insertEndProduct(ep2);
        dataProvider.insertEndProduct(ep3);
        dataProvider.insertEndProduct(ep4);

        dataProvider.insertOrder(o1);
        dataProvider.insertOrder(o2);
        dataProvider.insertOrder(o3);
        dataProvider.insertOrder(o4);
    }

    @AfterEach
    void tearDown() {
        dataProvider.deleteUser(u1.getId());
        dataProvider.deleteUser(u2.getId());

        dataProvider.deleteProduct(p1.getId());
        dataProvider.deleteProduct(p2.getId());
        dataProvider.deleteProduct(p3.getId());
        dataProvider.deleteProduct(p4.getId());

        dataProvider.deleteCreationKit(ck1.getId());
        dataProvider.deleteCreationKit(ck2.getId());
        dataProvider.deleteCreationKit(ck3.getId());
        dataProvider.deleteCreationKit(ck4.getId());

        dataProvider.deleteEndProduct(ep1.getId());
        dataProvider.deleteEndProduct(ep2.getId());
        dataProvider.deleteEndProduct(ep3.getId());
        dataProvider.deleteEndProduct(ep4.getId());

        dataProvider.deleteOrder(o1.getId());
        dataProvider.deleteOrder(o2.getId());
        dataProvider.deleteOrder(o3.getId());
        dataProvider.deleteOrder(o4.getId());
    }

    @Test
    void viewProductsPos() {
        Assertions.assertEquals(List.of(p1, p2, p3, p4), dataProvider.viewProducts(Constants.PRODUCT, 0));
        Assertions.assertEquals(List.of(ck1, ck2, ck3, ck4), dataProvider.viewProducts(Constants.CREATIONKIT, 0));
        Assertions.assertEquals(List.of(ep1, ep2, ep3, ep4), dataProvider.viewProducts(Constants.ENDPRODUCT, 0));
    }

    @Test
    void viewProductsNeg() {
        Assertions.assertNotEquals(new ArrayList<>(), dataProvider.viewProducts(Constants.PRODUCT, 0));
        Assertions.assertNotEquals(new ArrayList<>(), dataProvider.viewProducts(Constants.CREATIONKIT, 0));
        Assertions.assertNotEquals(new ArrayList<>(), dataProvider.viewProducts(Constants.ENDPRODUCT, 0));
    }

    @Test
    void filterViewPos() {
        Assertions.assertEquals(List.of(p1, p2, p3, p4), dataProvider.filterView(Constants.PRODUCT));
        Assertions.assertEquals(List.of(ck1, ck2, ck3, ck4), dataProvider.filterView(Constants.CREATIONKIT));
        Assertions.assertEquals(List.of(ep1, ep2, ep3, ep4), dataProvider.filterView(Constants.ENDPRODUCT));
    }

    @Test
    void filterViewNeg() {
        Assertions.assertNotEquals(new ArrayList<>(), dataProvider.filterView(Constants.PRODUCT));
        Assertions.assertNotEquals(new ArrayList<>(), dataProvider.filterView(Constants.CREATIONKIT));
        Assertions.assertNotEquals(new ArrayList<>(), dataProvider.filterView(Constants.ENDPRODUCT));
    }

    @Test
    void orderProductPos() {
        Order expectedOrder = new Order(99, u2, p1);
        Order actualOrder = dataProvider.orderProduct(21).get();
        expectedOrder.setId(actualOrder.getId());
        Assertions.assertEquals(expectedOrder, actualOrder);
        dataProvider.deleteOrder(actualOrder.getId());
    }

    @Test
    void orderProductNeg() {
        Order expectedOrder = new Order(99, u2, p1);
        Order actualOrder = dataProvider.orderProduct(21).get();
        Assertions.assertNotEquals(expectedOrder, actualOrder);
        dataProvider.deleteOrder(actualOrder.getId());
    }

    @Test
    void showRelatedPos()  {
        Assertions.assertEquals(Set.of(ck1),dataProvider.showRelated(p1.getId()));
    }

    @Test
    void showRelatedNeg()  {
        Assertions.assertEquals(Set.of(), dataProvider.showRelated(0));
    }

    @Test
    void viewUserDataPos() {
        Assertions.assertEquals(u1, dataProvider.viewUserData(u1.getId(), false).get());
    }

    @Test
    void viewUserDataNeg() {
        Assertions.assertNotEquals(u1, dataProvider.viewUserData(u2.getId(), false).get());
    }

    @Test
    void viewUserOrdersPos() {
        List<Order> expectedOrders = List.of(o1, o3);
        List<Order> actualOrders = dataProvider.viewUserOrders(u1.getId());
        Assertions.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void viewUserOrdersNeg() {
        List<Order> expectedOrders = List.of(o1, o3);
        List<Order> actualOrders = dataProvider.viewUserOrders(u2.getId());
        Assertions.assertNotEquals(expectedOrders, actualOrders);
    }

    @Test
    void calculateAmountPos() {
        double expectedSum = p1.getPrice() + ck1.getPrice();
        double actualSum = dataProvider.calculateAmount(u1.getId());
        Assertions.assertEquals(expectedSum, actualSum);
    }

    @Test
    void calculateAmountNeg() {
        Assertions.assertEquals(0, dataProvider.calculateAmount(System.currentTimeMillis()));
    }

    @Test
    void getUsersPos() {
        Assertions.assertEquals(List.of(u1, u2), dataProvider.getUsers());
    }

    @Test
    void getUsersNeg() {
        Assertions.assertNotEquals(List.of(u1), dataProvider.getUsers());
    }

    @Test
    void getUserPos() {
        Assertions.assertEquals(u1, dataProvider.getUser(u1.getId()));
    }

    @Test
    void getUserNeg() {
        Assertions.assertNotEquals(u1, dataProvider.getUser(u2.getId()));
    }

    @Test
    void insertUserPos() {
        User user = new User(99, "James Doe", "123", "123", "123");
        Assertions.assertEquals(user.getId(), dataProvider.insertUser(user));
        dataProvider.deleteUser(user.getId());
    }

    @Test
    void insertUserNeg() {
        long oldId = u1.getId();
        Assertions.assertNotEquals(oldId, dataProvider.insertUser(u1));
        dataProvider.deleteUser(oldId);
    }

    @Test
    void deleteUserPos() {
        Assertions.assertTrue(dataProvider.deleteUser(u1.getId()));
    }

    @Test
    void deleteUserNeg() {
        Assertions.assertFalse(dataProvider.deleteUser(99));
    }

    @Test
    void updateUserPos() {
        u1.setName("James Doe");
        Assertions.assertTrue(dataProvider.updateUser(u1));
    }

    @Test
    void updateUserNeg() {
        User user = new User(99, "James Doe", "123", "123", "123");
        Assertions.assertFalse(dataProvider.updateUser(user));
    }

    @Test
    void getProductsPos() {
        Assertions.assertEquals(List.of(p1, p2, p3, p4), dataProvider.getProducts());
    }

    @Test
    void getProductsNeg() {
        Assertions.assertNotEquals(List.of(p1), dataProvider.getProducts());
    }

    @Test
    void getProductPos() {
        Assertions.assertEquals(p1, dataProvider.getProduct(p1.getId()));
    }

    @Test
    void getProductNeg() {
        Assertions.assertNotEquals(p1, dataProvider.getProduct(p2.getId()));
    }

    @Test
    void insertProductPos() {
        Product product = new Product(99, "Hummer", 10);
        Assertions.assertEquals(product.getId(), dataProvider.insertProduct(product));
        dataProvider.deleteProduct(product.getId());
    }

    @Test
    void insertProductNeg() {
        long oldId = p1.getId();
        Assertions.assertNotEquals(oldId, dataProvider.insertProduct(p1));
        dataProvider.deleteProduct(oldId);
    }

    @Test
    void deleteProductPos() {
        Assertions.assertTrue(dataProvider.deleteProduct(p1.getId()));
    }

    @Test
    void deleteProductNeg() {
        Assertions.assertFalse(dataProvider.deleteProduct(99));
    }

    @Test
    void updateProductPos() {
        p1.setName("James Doe");
        Assertions.assertTrue(dataProvider.updateProduct(p1));
    }

    @Test
    void updateProductNeg() {
        Product product = new Product(99, "Hummer", 10);
        Assertions.assertFalse(dataProvider.updateProduct(product));
    }

    @Test
    void getCreationKitsPos() {
        Assertions.assertEquals(List.of(ck1, ck2, ck3, ck4), dataProvider.getCreationKits());
    }

    @Test
    void getCreationKitsNeg() {
        Assertions.assertNotEquals(List.of(ck1), dataProvider.getCreationKits());
    }

    @Test
    void getCreationKitPos() {
        Assertions.assertEquals(ck1, dataProvider.getCreationKit(ck1.getId()));
    }

    @Test
    void getCreationKitNeg() {
        Assertions.assertNotEquals(ck1, dataProvider.getCreationKit(ck2.getId()));
    }

    @Test
    void insertCreationKitPos() {
        CreationKit creationKit = new CreationKit(99, "James Doe", 10, "123", "123");
        Assertions.assertEquals(creationKit.getId(), dataProvider.insertCreationKit(creationKit));
        dataProvider.deleteCreationKit(creationKit.getId());
    }

    @Test
    void insertCreationKitNeg() {
        long oldId = ck1.getId();
        Assertions.assertNotEquals(oldId, dataProvider.insertCreationKit(ck1));
        dataProvider.deleteCreationKit(oldId);
    }

    @Test
    void deleteCreationKitPos() {
        Assertions.assertTrue(dataProvider.deleteCreationKit(ck1.getId()));
    }

    @Test
    void deleteCreationKitNeg() {
        Assertions.assertFalse(dataProvider.deleteCreationKit(99));
    }

    @Test
    void updateCreationKitPos() {
        ck1.setName("James Doe");
        Assertions.assertTrue(dataProvider.updateCreationKit(ck1));
    }

    @Test
    void updateCreationKitNeg() {
        CreationKit creationKit = new CreationKit(99, "James Doe", 10, "123", "123");
        Assertions.assertFalse(dataProvider.updateCreationKit(creationKit));
    }

    @Test
    void getEndProductsPos() {
        Assertions.assertEquals(List.of(ep1, ep2, ep3, ep4), dataProvider.getEndProducts());
    }

    @Test
    void getEndProductsNeg() {
        Assertions.assertNotEquals(List.of(ep1), dataProvider.getEndProducts());
    }

    @Test
    void getEndProductPos() {
        Assertions.assertEquals(ep1, dataProvider.getEndProduct(ep1.getId()));
    }

    @Test
    void getEndProductNeg() {
        Assertions.assertNotEquals(ep1, dataProvider.getEndProduct(ep2.getId()));
    }

    @Test
    void insertEndProductPos() {
        EndProduct endProduct = new EndProduct(99, "James Doe", 10, 10);
        Assertions.assertEquals(endProduct.getId(), dataProvider.insertEndProduct(endProduct));
        dataProvider.deleteEndProduct(endProduct.getId());
    }

    @Test
    void insertEndProductNeg() {
        long oldId = ep1.getId();
        Assertions.assertNotEquals(oldId, dataProvider.insertEndProduct(ep1));
        dataProvider.deleteEndProduct(oldId);
    }

    @Test
    void deleteEndProductPos() {
        Assertions.assertTrue(dataProvider.deleteEndProduct(ep1.getId()));
    }

    @Test
    void deleteEndProductNeg() {
        Assertions.assertFalse(dataProvider.deleteEndProduct(99));
    }

    @Test
    void updateEndProductPos() {
        ep1.setName("James Doe");
        Assertions.assertTrue(dataProvider.updateEndProduct(ep1));
    }

    @Test
    void updateEndProductNeg() {
        EndProduct endProduct = new EndProduct(99, "James Doe", 10, 10);
        Assertions.assertFalse(dataProvider.updateEndProduct(endProduct));
    }
    @Test
    void getOrdersPos() {
        Assertions.assertEquals(List.of(o1, o2, o3, o4), dataProvider.getOrders());
    }

    @Test
    void getOrdersNeg() {
        Assertions.assertNotEquals(List.of(o1), dataProvider.getOrders());
    }

    @Test
    void getOrderPos() {
        Assertions.assertEquals(o1, dataProvider.getOrder(o1.getId()));
    }

    @Test
    void getOrderNeg() {
        Assertions.assertNotEquals(o1, dataProvider.getOrder(o2.getId()));
    }

    @Test
    void insertOrderPos() {
        Order order = new Order(99, u1, p1);
        Assertions.assertEquals(order.getId(), dataProvider.insertOrder(order));
        dataProvider.deleteOrder(order.getId());
    }

    @Test
    void insertOrderNeg() {
        long oldId = o1.getId();
        Assertions.assertNotEquals(oldId, dataProvider.insertOrder(o1));
        dataProvider.deleteOrder(oldId);
    }

    @Test
    void deleteOrderPos() {
        Assertions.assertTrue(dataProvider.deleteOrder(o1.getId()));
    }

    @Test
    void deleteOrderNeg() {
        Assertions.assertFalse(dataProvider.deleteOrder(99));
    }

    @Test
    void updateOrderPos() {
        o1.setCustomer(u2);
        Assertions.assertTrue(dataProvider.updateOrder(o1));
    }

    @Test
    void updateOrderNeg() {
        Order order = new Order(99, u1, p1);
        Assertions.assertFalse(dataProvider.updateOrder(order));
    }
}