package training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksij.onysymchuk@gmail on 03.01.2017.
 */
public class PCFacade {

    public static void main(String[] args) {
        List<Media> drives = new ArrayList<>();
        drives.add(new MediaImpl("system hdd", new Windows()));
        drives.add(new MediaImpl("removable media", new Dos()));
        drives.add(new MediaImpl("archive drive", null));
        BootManager bootManager = new BootManagerImpl();
        PCTower pc = new PCMiddleTower(new PowerSupplyImpl(), drives, bootManager);
        pc.connectAllDevices();
        pc.on();
        pc.reset();
        pc.off();
        pc.on();
        bootManager.selectNextDrive();
        pc.on();
        pc.off();


    }


}

interface PCTower {
    void on();

    void off();

    void reset();

    void connectAllDevices();
}

interface PowerConsumer {
    void on();

    void off();
}

interface PowerSupply extends PowerConsumer {
    void connectDevice(PowerConsumer powerConsumer);

    void disconnectDevice(PowerConsumer powerConsumer);
}

interface Media extends PowerConsumer {
    InstalledSystem load();

    boolean isSystemInstalled();

    String getName();
}

interface InstalledSystem {
    void start();

    void stop();
}

interface BootManager {
    void addMedia(Media media);

    InstalledSystem loadSystemFromSelectedMedia();

    void selectNextDrive();

}

/////////// IMPLEMENTATIONS  //////////////////

class PCMiddleTower implements PCTower {
    PowerSupply powerSupply;
    List<Media> drives;
    BootManager bootManager;
    InstalledSystem system;


    public PCMiddleTower(PowerSupply powerSupply, List<Media> drives, BootManager bootManager) {
        this.powerSupply = powerSupply;
        this.drives = drives;
        this.bootManager = bootManager;

    }

    @Override
    public void on() {
        powerSupply.on();
        system = bootManager.loadSystemFromSelectedMedia();
        system.start();
    }

    @Override
    public void off() {
        system.stop();
        powerSupply.off();
    }

    @Override
    public void reset() {
        System.out.println("RESET BUTTON HAS BEEN PRESSED!!!! HARD RESET PERFORMED!!!");
        system = bootManager.loadSystemFromSelectedMedia();
        system.start();
    }

    @Override
    public void connectAllDevices() {
        for (Media media : drives) {
            powerSupply.connectDevice(media);
            bootManager.addMedia(media);
        }
    }
}

class BootManagerImpl implements BootManager {
    private List<Media> drives = new ArrayList<>();
    private int selectedDriveIndex = -1;

    @Override
    public void addMedia(Media media) {
        if (media.isSystemInstalled()) {
            drives.add(media);
        }
        setDefaultSelected();
    }

    private void setDefaultSelected() {
        if (!drives.isEmpty()) {
            selectedDriveIndex = 0;
        } else {
            selectedDriveIndex = -1;
        }
    }

    @Override
    public InstalledSystem loadSystemFromSelectedMedia() {
        if (selectedDriveIndex < 0) {
            throw new RuntimeException("No loadable media has been found....");
        }
        return drives.get(selectedDriveIndex).load();
    }

    @Override
    public void selectNextDrive() {
        selectedDriveIndex++;
        if (selectedDriveIndex > drives.size() - 1) {
            selectedDriveIndex = 0;
        } else if (selectedDriveIndex == -1) {
            setDefaultSelected();
        }
        System.out.println("BOOT MEDIA HAS BEEN SWITCHED TO NEXT IN LIST");
    }
}

class PowerSupplyImpl implements PowerSupply {
    private List<PowerConsumer> powerConsumers = new ArrayList<>();

    @Override
    public void on() {
        System.out.println("POWER ON BUTTON HAS BEEN PRESSED");
        System.out.println("POWER UNIT IS ON-LINE");
        powerConsumers.forEach(PowerConsumer::on);
        System.out.println("All devices powered on");
    }

    @Override
    public void off() {
        powerConsumers.forEach(PowerConsumer::off);
        System.out.println("All devices powered off");
        System.out.println("POWER UNIT IS OFF-LINE");
    }

    @Override
    public void connectDevice(PowerConsumer powerConsumer) {
        powerConsumers.add(powerConsumer);
    }

    @Override
    public void disconnectDevice(PowerConsumer powerConsumer) {
        powerConsumers.remove(powerConsumer);
    }
}

class MediaImpl implements Media {
    private String name;
    private InstalledSystem systemInstalled;

    public MediaImpl(String name, InstalledSystem systemInstalled) {
        this.name = name;
        this.systemInstalled = systemInstalled;
    }

    @Override
    public void on() {
        System.out.println("Device " + name + " is powered on");

    }

    @Override
    public void off() {
        System.out.println("Device " + name + " is powered off");

    }

    @Override
    public InstalledSystem load() {
        if (!isSystemInstalled()) {
            throw new UnsupportedOperationException();
        } else {
            return systemInstalled;
        }
    }

    @Override
    public boolean isSystemInstalled() {
        return !(systemInstalled == null);
    }

    @Override
    public String getName() {
        return name;
    }
}

class Dos implements InstalledSystem {
    @Override
    public void start() {
        System.out.println("System DOS 6.0 has been loaded.");

    }

    @Override
    public void stop() {
        System.out.println("SOFTWARE POWER OFF BUTTON HAS BEEN PRESSED");
        System.out.println("Simply exiting...");
    }
}

class Windows implements InstalledSystem {
    @Override
    public void start() {
        System.out.println("Starting Windows....");
        System.out.println("Here should be sleep for 250 seconds.......loading.....loading...loading...........");
        System.out.println("Showing desktop. Windows has been loaded.");
    }

    @Override
    public void stop() {
        System.out.println("SOFTWARE POWER OFF BUTTON HAS BEEN PRESSED");
        System.out.println("Preparing for Windows shutdown....");
        System.out.println("Here should be sleep for 250 seconds.......saving.....saving...saving...........");
        System.out.println("Now power can be switched off.");
    }
}

