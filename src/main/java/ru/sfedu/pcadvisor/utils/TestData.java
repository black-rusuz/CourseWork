package ru.sfedu.pcadvisor.utils;

import ru.sfedu.pcadvisor.model.bean.*;

import java.util.List;

public class TestData {
    public final Cpu c1 = new Cpu(11, "Intel Core i3-9100F", 7490, 3600, 4, "LGA 1151");
    public final Cpu c2 = new Cpu(12, "AMD Ryzen 5 3600", 9490, 3600, 6, "AM4");

    public final Motherboard m1 = new Motherboard(21, "Gigabyte H410 S2H", 6990, "LGA 1151", 4);
    public final Motherboard m2 = new Motherboard(22, "Gigabyte B450 S2H", 3990, "AM4", 4);

    public final Ram r1 = new Ram(31, "HyperX Fury", 2990, 8, 4);
    public final Ram r2 = new Ram(32, "Crucial Ballistix", 3990, 16, 4);

    public final List<Part> p1 = List.of(c1, m1, r1);
    public final List<Part> p2 = List.of(c2, m2);

    public final Order o1 = new Order(41, "Working PC", p1, p1.stream().mapToDouble(Part::getPrice).sum());
    public final Order o2 = new Order(42, "Home PC", p2, p2.stream().mapToDouble(Part::getPrice).sum());
}
