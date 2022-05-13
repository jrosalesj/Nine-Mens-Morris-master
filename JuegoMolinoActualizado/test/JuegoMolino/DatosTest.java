
package JuegoMolino;

import org.junit.Test;
import static org.junit.Assert.*;

public class DatosTest {
    Datos d = new Datos();
    
    @Test
    public void testGetCallDatos() {
        System.out.println("Obtener getCallDatos como llamado de que la clase Datos este funcionando correctamente");
        assertEquals(true, d.getCallDatos());
    }
}
