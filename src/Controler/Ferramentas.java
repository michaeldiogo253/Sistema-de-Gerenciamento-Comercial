package Controler;

import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Ferramentas {

    public void LimpaCampo(JPanel container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText(null);
            }
        }
    }

    public String DataAmericana() {
        Date data = new Date();
        DateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        String date = formato.format(data);

        return date;

    }
}
