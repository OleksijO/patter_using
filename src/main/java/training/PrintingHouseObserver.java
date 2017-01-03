package training;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by oleksij.onysymchuk@gmail on 03.01.2017.
 */
public class PrintingHouseObserver {

    public static void main(String[] args) {
        Subscriber citySubscriber1 = new SubscriberImpl("CitySubscriber1", new Address(SettlementType.CITY));
        Subscriber villageSubscriber1 = new SubscriberImpl("VillageSubscriber1", new Address(SettlementType.VILLAGE));
        Subscriber citySubscriber2 = new SubscriberImpl("CitySubscriber2", new Address(SettlementType.CITY));
        Subscriber villageSubscriber2 = new SubscriberImpl("VillageSubscriber2", new Address(SettlementType.VILLAGE));
        Subscriber villageSubscriber3 = new SubscriberImpl("VillageSubscriber3", new Address(SettlementType.VILLAGE));
        Publisher printingHouse = new PrintingHouse();
        printingHouse.subscribe(citySubscriber1);
        printingHouse.subscribe(villageSubscriber1);
        printingHouse.subscribe(citySubscriber2);
        printingHouse.subscribe(villageSubscriber2);
        printingHouse.subscribe(villageSubscriber3);

        printingHouse.printEdition("News Today", "NewspaperContent", 10);
        Predicate<Subscriber> onlyVillegers =
                subscriber -> subscriber.getAddress().getSettlementType() == SettlementType.VILLAGE;
        printingHouse
                .sendEditionTo(onlyVillegers);
    }

}

interface Publisher {

    void subscribe(Subscriber subscriber);

    void unSubscribe(Subscriber subscriber);

    void printEdition(String name, String content, int quantity);

    void sendEditionToAll();

    void sendEditionTo(Predicate<Subscriber> predicate);
}

interface Subscriber {

    Address getAddress();

    void receivePrint(Print print);
}

interface Print {
}

interface Edition {
    void addPrint(Print print);

    Print popPrint();

    boolean hasPrint();
}

class Address {
    private SettlementType settlementType;

    public Address(SettlementType settlementType) {
        this.settlementType = settlementType;
    }

    public SettlementType getSettlementType() {
        return settlementType;
    }

    @Override
    public String toString() {
        return settlementType.toString();
    }
}

enum SettlementType {
    CITY,
    VILLAGE
}

class SubscriberImpl implements Subscriber {
    private String name;
    private Address address;

    public SubscriberImpl(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void receivePrint(Print print) {
        System.out.println(name + ", " + address + ", received print " + print);
    }
}

class Newspaper implements Print {
    private String name;
    private long id;

    public Newspaper(String name, long id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Newspaper{ " + name + ", id=" + id + '}';
    }
}

class EditionImpl implements Edition {
    LinkedList<Print> prints = new LinkedList<>();

    @Override
    public void addPrint(Print print) {
        prints.add(print);
    }

    @Override
    public Print popPrint() {
        return prints.pop();
    }

    @Override
    public boolean hasPrint() {
        return !prints.isEmpty();
    }
}

class PrintingHouse implements Publisher {
    private Edition edition;
    private List<Subscriber> subscribers = new ArrayList<>();

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unSubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void printEdition(String name, String content, int quantity) {
        Edition newEdition = new EditionImpl();
        for (int i = 0; i < quantity; i++) {
            newEdition.addPrint(new Newspaper(name, i));
        }
        edition = newEdition;
        System.out.println("New edition '" + name + "' has been printed. Quantity = " + quantity);
    }

    @Override
    public void sendEditionToAll() {
        Objects.requireNonNull(edition);
        sendEditionTo(subscriber -> true);
    }

    @Override
    public void sendEditionTo(Predicate<Subscriber> predicate) {
        Objects.requireNonNull(edition);
        subscribers
                .stream()
                .filter(predicate)
                .forEach(subscriber -> subscriber.receivePrint(edition.popPrint()));
        System.out.println("Edition has been sent");
    }
}

