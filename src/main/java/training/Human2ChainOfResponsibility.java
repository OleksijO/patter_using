package training;

/**
 * Created by oleksij.onysymchuk@gmail on 13.12.2016.
 *
 * Создать необходимые сущности и сымитировать изменения состояния объекта «Человек» в «Охотник», «Рыбак», «Грибник» в зависимости от его состояния:
 * - находится  - на берегу реки,
 * - находится  - на грибной поляне,
 * - видит зверя.
 * Учесть возможность наложения событий – человек находится на грибной поляне (например) и увидел зверя.
 */
public class Human2ChainOfResponsibility {


    public static void main(String[] args) {

        Person person = new Human2ChainOfResponsibility().new Person();
        System.out.println("Just person:");
        person.performAction();
        System.out.println();

        System.out.println("Only Hunter:");
        person.setStateSeeingBeast(true);
        person.performAction();
        System.out.println();

        System.out.println("Only Fisher:");
        person.setStateSeeingBeast(false);
        person.setPositionOnRiverBank(true);
        person.performAction();
        System.out.println();

        System.out.println("Fisher, Hunter and Mushroomer are active:");
        person.setStateSeeingBeast(true);
        person.setPositionOnRiverBank(true);
        person.setPositionOnMushRoomMeadow(true);
        person.performAction();
        System.out.println();

        System.out.println("Just human again....");
        person.setStateSeeingBeast(false);
        person.setPositionOnRiverBank(false);
        person.setPositionOnMushRoomMeadow(false);
        person.performAction();



    }


    class Person {
        private String name;

        private Role fisher = new Fisher();
        private Role hunter = new Hunter();
        private Role mushroomer = new Mushroomer();
        private Role human = new Human();

        private Role firstRole;

        public Person() {
            this.firstRole = fisher;
            fisher.setNext(hunter);
            hunter.setNext(mushroomer);
            mushroomer.setNext(human);
        }

        public void performAction() {
            firstRole.performAction(false);
        }

        public void setPositionOnRiverBank(boolean active) {
            fisher.setActive(active);
        }

        public void setPositionOnMushRoomMeadow(boolean active) {
            mushroomer.setActive(active);
        }

        public void setStateSeeingBeast(boolean active) {
           hunter.setActive(active);
        }

    }

    interface Role {
        void performAction(boolean anyActionPerformed);

        void setNext(Role role);

        void setActive(boolean active);
    }

    abstract class AbstractRole implements Role {
        private Role next;
        private boolean active;

        protected abstract void performActionImplementation();

        @Override
        public void performAction(boolean anyActionPerformed) {
            if (active) {
                performActionImplementation();
                anyActionPerformed=true;
            }
            if (next != null) {
                next.performAction(anyActionPerformed);
            }
        }

        @Override
        public void setNext(Role nextRole) {
            this.next = nextRole;

        }

        @Override
        public void setActive(boolean active) {
            this.active = active;

        }
    }

    /**
     * This Role MUST be the last one in a chain
     */
    class Human extends AbstractRole {

        @Override
        public void performAction(boolean anyActionPerformed) {
           if (!anyActionPerformed){
               performActionImplementation();
           }
        }

        @Override
        public void setNext(Role nextRole) {
            throw new UnsupportedOperationException("This action must be the las one in chain!");
        }

        @Override
        protected void performActionImplementation() {
            System.out.println("I am usual human...and I am doing nothing");
        }


    }

    class Hunter extends AbstractRole {

        protected void performActionImplementation() {
            System.out.println("Ooooouh....Mushrooms....Gather, gather...gather...");
        }
    }

    class Fisher extends AbstractRole {

        protected void performActionImplementation() {
            System.out.println("I'll catch a very bih fish now...");
        }
    }

    class Mushroomer extends AbstractRole {

        protected void performActionImplementation() {
            System.out.println("This is a very tasty wild pig....we'll have a delicious dinner...");
        }
    }


}





