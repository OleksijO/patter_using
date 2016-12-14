package training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksij.onysymchuk@gmail on 14.12.2016.
 */
public class WritingPen {


    public static void main(String[] args) {
        WritingPen test = new WritingPen();
        Writer writer = test.new Writer(test.new Human("Human's name"), test.new PenImpl());
        Text text1 = test.new Text("12345");
        Text text2 = test.new Text("67890");
        Text text3 = test.new Text("FFFFF");
        Paper sheet = test.new Paper();
        System.out.println("Paper is clear: " + sheet);
        writer.write(sheet, text1);
        System.out.println(text1 + " was written on paper: " + sheet);
        writer.write(sheet, text2);
        System.out.println(text2 + " was written on paper: " + sheet);
        try {
            writer.write(sheet, text3);
        } catch (Exception e) {
            System.out.println(text3 + " wasn't written on paper: " + sheet);
            System.out.println("Got an Error = "+e.getMessage());
        }
    }


    class Human {
        String name;

        public Human(String name) {
            this.name = name;
        }
    }

    class Writer {
        Human numan;
        Pen pen;

        public Writer(Human numan, Pen pen) {
            this.numan = numan;
            this.pen = pen;
        }

        public Paper write(Paper paper, Text text) {
            paper.checkFirePlace(text);
            return pen.write(paper, text);
        }
    }

    class Paper {
        List<Text> texts = new ArrayList<>();
        int maxLength = 10;

        public void checkFirePlace(Text text) {
            int finalLength = texts.stream().mapToInt(txt -> txt.getString().length()).sum()
                    + text.getString().length();
            if (maxLength < finalLength) {
                throw new RuntimeException("Unsufficient space for text!");
            }
        }

        public void add(Text text) {
            texts.add(text);
        }

        @Override
        public String toString() {
            return "Paper{" +
                    "texts=" + texts +
                    ", maxLength=" + maxLength +
                    '}';
        }
    }

    class Text {
        String string;

        public Text(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }

        @Override
        public String toString() {
            return "Text{" +
                    "string='" + string + '\'' +
                    '}';
        }
    }

    interface Pen {
        Paper write(Paper paper, Text text);
    }

    class PenImpl implements Pen {
        @Override
        public Paper write(Paper paper, Text text) {
            paper.add(text);
            return paper;
        }
    }
}



