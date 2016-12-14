package training;

/**
 * Created by oleksij.onysymchuk@gmail on 13.12.2016.
 *
 * Создать необходимые сущности и сымитировать изменения состояния объекта «Человек» в «Охотник», «Рыбак», «Грибник» в зависимости от его состояния:
 * - находится  - на берегу реки,
 * - находится  - на грибной поляне,
 * - видит зверя.
 */
public class Human1State {

    class Person implements Role {
        private String name;

        private Role fisher = new Fisher();
        private Role hunter = new Hunter();
        private Role mushroomer = new Mushroomer();
        private Role human = new Human();

        private Role actualRole = human;


        public void performAction() {
            actualRole.performAction();
        }

        public void setPositionOnRiverBank(boolean active) {
            if (active) {
                actualRole = fisher;
            } else {
                actualRole = human;
            }
        }

        public void setPositionOnMushRoomMeadow(boolean active) {
            if (active) {
                actualRole = mushroomer;
            } else {
                actualRole = human;
            }
        }

        public void setStateSeeingBeast(boolean active) {
            if (active) {
                actualRole = hunter;
            } else {
                actualRole = human;
            }
        }

    }

    interface Role {
        void performAction();
    }

    class Human implements Role {

        public void performAction() {
            System.out.println("I am usual human...and I am doing nothing");
        }
    }

    class Hunter implements Role {

        public void performAction() {
            System.out.println("Ooooouh....Mushrooms....Gather, gather...gather...");
        }
    }

    class Fisher implements Role {

        public void performAction() {
            System.out.println("I'll catch a very bih fish now...");
        }
    }

    class Mushroomer implements Role {

        public void performAction() {
            System.out.println("This is a very tasty wild pig....we'll have a delicious dinner...");
        }
    }
}

