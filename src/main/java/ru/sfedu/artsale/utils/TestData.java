package ru.sfedu.artsale.utils;

import ru.sfedu.artsale.model.bean.*;

public class TestData {
    public User u1 = new User(11, "John Doe", "+7 999 123-45-67", "mail@box.com", "Rostov-on-Don");
    public User u2 = new User(12, "Anna Doe", "+1 234 567-89-00", "address@gmail.com", "Albuquerque");

    public Product p1 = new Product(21, "Wood", 50);
    public Product p2 = new Product(22, "Steel", 100);
    public Product p3 = new Product(23, "Glue stick", 25);
    public Product p4 = new Product(24, "Screw", 250);

    public CreationKit ck1 = new CreationKit(31, "Kit 1", 100, "Home", "Screwdriver, Hummer");
    public CreationKit ck2 = new CreationKit(32, "Kit 2", 150, "Art", "Brush, paint");
    public CreationKit ck3 = new CreationKit(33, "Kit 3", 200, "DIY", "Glue gun, glue sticks");
    public CreationKit ck4 = new CreationKit(34, "Kit 4", 250, "Sculpture", "Hummer, dust");

    public EndProduct ep1 = new EndProduct(41, "Painting", 1000, 6);
    public EndProduct ep2 = new EndProduct(42, "Sculpture", 2500, 12);
    public EndProduct ep3 = new EndProduct(43, "Carpet", 2000, 24);
    public EndProduct ep4 = new EndProduct(44, "Vase", 750, 36);

    public Order o1 = new Order(51, u1, p1);
    public Order o2 = new Order(52, u2, p2);
    public Order o3 = new Order(53, u1, ck1);
    public Order o4 = new Order(54, u2, ep1);
}
