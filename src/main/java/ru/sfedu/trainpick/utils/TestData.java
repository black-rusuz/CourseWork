package ru.sfedu.trainpick.utils;

import ru.sfedu.trainpick.model.bean.*;

public class TestData {
    public final ChildPassenger cp1 = new ChildPassenger(11, "Dyadka Valentin Viktorovich", "21.01.2019", "6022 989898", false);
    public final ChildPassenger cp2 = new ChildPassenger(12, "Pushnoy Fedor Ivanovich", "14.02.2022", "6022 121212", true);

    public final DiscountPassenger dp1 = new DiscountPassenger(21, "Sechenov Dmitriy Petrovich", "12.01.1969", "6019 324234", 0.1, "Pensioner");
    public final DiscountPassenger dp2 = new DiscountPassenger(22, "Mendeleev Anton Pavlovich", "26.02.1985", "6012 445345", 0.5, "Soldier");

    public final Passenger ps1 = new Passenger(31, "Volya Pavel Igorevich", "16.04.2000", "6014 566556");
    public final Passenger ps2 = new Passenger(32, "Poslov Semen Daskovich", "11.07.1992", "6012 878778");

    public final Train tr1 = new Train(41, "Moscow", "Rostov-on-Don", "21.03.2022//00:00", "21.03.2022//19:00", 6000);
    public final Train tr2 = new Train(42, "Adler", "Ekaterinburg", "22.03.2022//00:00", "23.03.2022//19:00", 3500);

    public final Ticket ti1 = new Ticket(51, ps1, tr1, "", 6000, false);
    public final Ticket ti2 = new Ticket(52, dp2, tr1, "", 3000, true);
}
