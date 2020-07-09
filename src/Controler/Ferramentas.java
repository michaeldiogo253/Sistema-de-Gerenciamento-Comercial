
package Controler;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Ferramentas {
    
    
     public void LimpaCampo(JPanel container){
        Component components[] = container.getComponents();
        for(Component component : components){
            if(component instanceof JTextField){
                ((JTextField) component ).setText(null);
            }
        }
    }
}
