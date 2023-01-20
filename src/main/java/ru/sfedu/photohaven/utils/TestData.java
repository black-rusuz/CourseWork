package ru.sfedu.photohaven.utils;

import ru.sfedu.photohaven.model.bean.*;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    public final Accessory a1 = new Accessory(11, "Tripod AURA Stand", 1990, "Tripod to stand cameras with EU bracket");
    public final Accessory a2 = new Accessory(12, "Stabilizer RED STAR S-60", 6840, "Stabilizer to stand cameras with EU bracket");
    public final Accessory a3 = new Accessory(13, "Camera Flash Canon FDX-45", 9990, "Canon classic flash");

    public final Camera c1 = new Camera(21, "Nicon CX-5300", 7890, true);
    public final Camera c2 = new Camera(22, "ZENIT M", 14990, false);
    public final Camera c3 = new Camera(23, "GoPro 4", 6490, true);

    public final Material m1 = new Material(31, "Camera Roll FOMAPAN 100 Classic", 550, 36, "frames");
    public final Material m2 = new Material(32, "Developer Ilford Ilfotec DD-X", 3290, 1, "liter");
    public final Material m3 = new Material(33, "Photo Paper Aceline Gloss", 99, 50, "papers");

    List<Product> p1 = new ArrayList<>(List.of(a1, c1, m1));
    List<Product> p2 = new ArrayList<>(List.of(a2, c2, m2));
    List<Product> p3 = new ArrayList<>(List.of(a3, c3, m3));

    public final Basket b1 = new Basket(41, p1);
    public final Basket b2 = new Basket(42, p2);
    public final Basket b3 = new Basket(43, p3);
}
