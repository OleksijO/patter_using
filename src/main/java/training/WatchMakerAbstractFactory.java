package training;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oleksij.onysymchuk@gmail on 30.12.2016.
 */
public class WatchMakerAbstractFactory {

    public static void main(String[] args) {
        WatchMakerAbstractFactory This = new WatchMakerAbstractFactory();
        FactoryOfWatchFactories factoryOfWatchFactories = This.new FactoryOfWatchFactories();

        WatchFactory watchFactory = factoryOfWatchFactories.getFactory(WatchFactoryType.CLASSIC);
        Watch watch = watchFactory.createWatch();
        watch.showTime();

        watchFactory = factoryOfWatchFactories.getFactory(WatchFactoryType.DIGITAL);
        watch = watchFactory.createWatch();
        watch.showTime();
    }


    interface Watch {
        void showTime();
    }

    interface WatchFactory {
        Watch createWatch();
    }

    class FactoryOfWatchFactories {
        private Map<WatchFactoryType, WatchFactory> factories = new HashMap<WatchFactoryType, WatchFactory>() {{
            put(WatchFactoryType.CLASSIC, new ClassicWatchFactory());
            put(WatchFactoryType.DIGITAL, new DigitalWatchFactory());
        }};

        public WatchFactory getFactory(WatchFactoryType type) {
            return factories.get(type);
        }
    }

    enum WatchFactoryType {
        DIGITAL, CLASSIC
    }

    class DigitalWatchFactory implements WatchFactory {
        @Override
        public Watch createWatch() {
            return new DigitalWatch();
        }
    }

    class DigitalWatch implements Watch {
        @Override
        public void showTime() {
            System.out.println(this + " : " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }
    }

    class ClassicWatchFactory implements WatchFactory {
        @Override
        public Watch createWatch() {
            return new ClassicWatch();
        }
    }

    class ClassicWatch implements Watch {
        @Override
        public void showTime() {
            Date now = new Date();
            String hours = new SimpleDateFormat("hh").format(now);
            String minutes = new SimpleDateFormat("mm").format(now);
            System.out.println(this + " : " + "Маленькая стрелка указывает на " + hours + ", большая - на " + minutes);
        }
    }


}
