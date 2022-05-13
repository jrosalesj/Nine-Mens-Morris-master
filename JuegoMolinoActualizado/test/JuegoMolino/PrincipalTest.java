
package JuegoMolino;

import org.junit.Test;
import static org.junit.Assert.*;

public class PrincipalTest {
    Principal p = new Principal();
    
    @Test
    public void testGetCallTablero(){
        System.out.println("Obtener getCallTablero como llamado de que la clase Principal este funcionando correctamente");
        assertEquals(true, p.getCallTablero());
    }
    
}
