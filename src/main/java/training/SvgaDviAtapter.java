package training;

/**
 * Created by oleksij.onysymchuk@gmail on 15.12.2016.
 */
public class SvgaDviAtapter {

    public static void main(String[] args) {
        SvgaDviAtapter test = new SvgaDviAtapter();
        PC pc = test.new PC();
        Display display = test.new Display();
        
        //display.plugSignalConnectorToJack(pc);

        DviSvgaAdapter adapter = test.new DviSvgaAdapter(pc);

        display.plugSignalConnectorToJack(adapter);
        
        
    }
    
    
    
    
    
    interface DVIConnector {
        void insertDVIConnector();
    }
    
    class Display {
        public void plugSignalConnectorToJack(DVIConnector jack) {
            System.out.print("DVI Jack: ");
            jack.insertDVIConnector();
        }
    }
    
    interface SVGAJack {
        void insertSVGAConnector();
    }
    
    class PC implements SVGAJack {
        @Override
        public void insertSVGAConnector() {
            System.out.println(" SVGAJack connector is connected.");
        }
    }
    
    class DviSvgaAdapter implements DVIConnector {
        SVGAJack jack;

        public DviSvgaAdapter(SVGAJack jack) {
            this.jack = jack;
        }

        @Override
        public void insertDVIConnector() {
            jack.insertSVGAConnector();
        }
    }
    
    
    
}
