package training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksij.onysymchuk@gmail on 13.12.2016.
 */
public class BattlePlanePattern {

    public static void main(String[] args) {
        BattlePlane plane = new BattlePlanePattern().new BattlePlane();
        plane.land();
        Weapon gun = new BattlePlanePattern().new Weapon("Пушка 30мм");
        Weapon rockets = new BattlePlanePattern().new Weapon("Неуправляемые ракеты");
        Weapon machineGun = new BattlePlanePattern().new Weapon("Пулемет 7,62, очередями");
        plane.addWeapon(gun);
        plane.addWeapon(rockets);
        plane.addWeapon(machineGun);

        // one should select weapon from the list of weapons: plane.selectWeapon(plane.getWeapons.get(i));
        System.out.println("==================================================================");
        System.out.println("Стрельба на земле из всего не заряженного:");
        plane.fireAll();
        System.out.println("==================================================================");
        System.out.println("Перезарядка на 2 выстрела всех:");
        plane.rechargeAll(2);
        System.out.println("==================================================================");
        System.out.println("Стрельба на земле из всего заряженного:");
        plane.fireAll();
        System.out.println("==================================================================");
        System.out.println("Стрельба на земле из пулемета:");
        plane.selectWeapon(machineGun);
        plane.fireSelectedWeapons();
        System.out.println("==================================================================");
        System.out.println("Взлетаем.....В ВОЗДУХЕ!");
        plane.takeOff();
        System.out.println("==================================================================");
        System.out.println("Стрельба в воздухе из пулемета:");
        plane.selectWeapon(machineGun);
        plane.fireSelectedWeapons();
        System.out.println("==================================================================");
        System.out.println("Стрельба в воздухе из всего:");
        plane.fireAll();
        System.out.println("==================================================================");
        System.out.println("Стрельба в воздухе из всего (пулемет закончился):");
        plane.fireAll();
        System.out.println("==================================================================");
        System.out.println("Стрельба в воздухе из всего (все закончилось):");
        plane.fireAll();
        System.out.println("==================================================================");
        System.out.println("Перезарядка в воздухе:");
        plane.rechargeAll(5);
        System.out.println("==================================================================");
        System.out.println("Идем на посадку.....Приземление...");
        plane.land();
        System.out.println("==================================================================");
        System.out.println("Перезарядка на 1 выстрел ракет и пушки:");
        plane.selectWeapon(rockets);
        plane.selectWeapon(gun);
        plane.rechargeSelectedWeapons(1);
        System.out.println("==================================================================");
        System.out.println("Стрельба на земле из всего заряженного:");
        plane.fireAll();
        System.out.println("==================================================================");
        System.out.println("Взлетаем.....В ВОЗДУХЕ!");
        plane.takeOff();
        System.out.println("==================================================================");
        System.out.println("Стрельба в воздухе из пулемета (незаряжен):");
        plane.selectWeapon(machineGun);
        plane.fireSelectedWeapons();
        System.out.println("==================================================================");
        System.out.println("Стрельба в воздухе из пушки:");
        plane.selectWeapon(gun);
        plane.fireSelectedWeapons();
        System.out.println("==================================================================");
        System.out.println("Идем на посадку.....Приземление...");
        plane.land();
        System.out.println("==================================================================");
        System.out.println("Снимаем вооружение.....СНЯТО...");
        plane.land();
        plane.removeAllWeapons();
        System.out.println("==================================================================");
        System.out.println("Взлетаем.....В ВОЗДУХЕ!");
        plane.takeOff();
        System.out.println("==================================================================");
        System.out.println("Стрельба в воздухе из всего (вооружение снято):");
        plane.fireAll();
        System.out.println("==================================================================");
        System.out.println("Идем на посадку.....Приземление...");
        plane.land();

    }


    class BattlePlane {
        List<Weapon> weapons = new ArrayList<>();
        WeaponController weaponController = new WeaponController();

        void takeOff() {
            weaponController.setActualState(weaponController.getStateInAir());
        }

        void land() {
            weaponController.setActualState(weaponController.getStateOnGround());
        }

        public void fireSelectedWeapons() {
            weaponController.fireSelectedWeapons();
        }

        public void rechargeSelectedWeapons(int shots) {
            weaponController.rechargeSelectedWeapons(shots);
        }

        public void selectWeapon(Weapon weapon) {
            weaponController.addWeaponToSelected(weapon);
        }

        public void fireAll() {
            weaponController.selectedWeapons.addAll(weapons);
            weaponController.fireSelectedWeapons();
        }

        public void rechargeAll(int shots) {
            weaponController.selectedWeapons.addAll(weapons);
            weaponController.rechargeSelectedWeapons(shots);
        }

        public void addWeapon(Weapon weapon) {
            weapons.add(weapon);
        }

        public void removeWeapon(Weapon weapon) {
            weapons.remove(weapon);
        }

        public void removeAllWeapons() {
            weapons.clear();
        }

        public List<Weapon> getWeapons() {
            return weapons;
        }
    }


    interface WeaponControllerSate {

        void recharge(int shots);

        void fire();

    }


    class Weapon {
        private String name;
        private int shots = 0;

        private WeaponFireState stateArmed = new WeaponFireStateArmed(this);
        private WeaponFireState stateNoAmmo = new WeaponFireStateNoAmmo(this);

        private WeaponFireState actualState = stateNoAmmo;

        public Weapon(String name) {
            this.name = name;
        }

        public void recharge(int shots) {
            this.shots += shots;
            if (shots > 0) {
                System.out.println(name + " заряжено. Боезапас - " + shots);
                actualState = stateArmed;
            }
        }

        public void fire() {
            actualState.fire();
        }

        public String getName() {
            return name;
        }

        public int getShots() {
            return shots;
        }

        public void setShots(int shots) {
            this.shots = shots;
        }

        public WeaponFireState getStateArmed() {
            return stateArmed;
        }

        public WeaponFireState getStateNoAmmo() {
            return stateNoAmmo;
        }

        public void setActualState(WeaponFireState actualState) {
            this.actualState = actualState;
        }
    }

    interface WeaponFireState {
        void fire();
    }

    class WeaponFireStateArmed implements WeaponFireState {
        Weapon weapon;

        public WeaponFireStateArmed(Weapon weapon) {
            this.weapon = weapon;
        }

        @Override
        public void fire() {
            weapon.setShots(weapon.getShots() - 1);
            System.out.println(weapon.getName() + ": стрельба произведена!");
            if (weapon.getShots() == 0) {
                weapon.setActualState(weapon.getStateNoAmmo());
            }

        }
    }

    class WeaponFireStateNoAmmo implements WeaponFireState {
        Weapon weapon;

        public WeaponFireStateNoAmmo(Weapon weapon) {
            this.weapon = weapon;
        }

        @Override
        public void fire() {
            System.out.println(weapon.getName() + ": стрельба невозможна. Нет боеприпаса!");
        }
    }

    class WeaponController {
        private List<Weapon> selectedWeapons = new ArrayList<>();

        private WeaponControllerSate stateOnGround = new WeaponControllerStandByState(this);
        private WeaponControllerSate stateInAir = new WeaponControllerStandToState(this);
        private WeaponControllerSate actualState = stateOnGround;


        public void fireSelectedWeapons() {
            actualState.fire();
        }

        public void rechargeSelectedWeapons(int shots) {
            actualState.recharge(shots);
        }

        public void addWeaponToSelected(Weapon weapon) {
            selectedWeapons.add(weapon);
        }

        public List<Weapon> getSelectedWeapons() {
            return selectedWeapons;
        }

        public void clearWeaponSelection() {
            selectedWeapons.clear();
        }

        public WeaponControllerSate getStateOnGround() {
            return stateOnGround;
        }

        public WeaponControllerSate getStateInAir() {
            return stateInAir;
        }

        public void setActualState(WeaponControllerSate actualState) {
            this.actualState = actualState;
        }


    }

    class WeaponControllerStandToState implements WeaponControllerSate {
        private WeaponController controller;

        public WeaponControllerStandToState(WeaponController controller) {
            this.controller = controller;
        }

        @Override
        public void recharge(int shots) {
            System.out.println("Заряжание оружия в небе невозможно!");
            controller.clearWeaponSelection();
        }

        @Override
        public void fire() {
            if (controller.getSelectedWeapons().size() == 0) {
                System.out.println("Отсутствует вооружение. Стрельба невозможна!");
            }
            for (Weapon weapon : controller.getSelectedWeapons()) {
                weapon.fire();
            }
            controller.clearWeaponSelection();
        }
    }

    class WeaponControllerStandByState implements WeaponControllerSate {
        private WeaponController controller;

        public WeaponControllerStandByState(WeaponController controller) {
            this.controller = controller;
        }

        @Override
        public void recharge(int shots) {
            for (Weapon weapon : controller.getSelectedWeapons()) {
                weapon.recharge(shots);
            }
            controller.clearWeaponSelection();
        }

        @Override
        public void fire() {
            controller.clearWeaponSelection();
            System.out.println("Стрельба на земле невозможна!");
        }

    }

}
