
package JuegoMolino;

import org.junit.Test;
import static org.junit.Assert.*;

public class ContrincanteTest {
    Contrincante c = new Contrincante();
    
    @Test
    public void testGetCallContrincante() {
        System.out.println("Obtener getCallContrincante como llamado de que la clase este funcionando correctamente");
        assertEquals(true,c.getCallContrincante());
    }
}
