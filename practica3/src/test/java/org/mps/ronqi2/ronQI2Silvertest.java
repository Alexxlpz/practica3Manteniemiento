package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mps.dispositivo.DispositivoSilver;

public class ronQI2SilverTest {

    RonQI2Silver ronquidos;
    DispositivoSilver disp;

    @BeforeEach
    public void mockitoSetUp(){
        disp = Mockito.mock(DispositivoSilver.class);
        ronquidos = new RonQI2Silver();
        ronquidos.anyadirDispositivo(disp);
    }

    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */
    
    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */
    @Nested
    @DisplayName("funcion para inicializar ronQI2")
    public class ronQI2TestIncializar {
    
        @Test
        public void InicializarSinConectarPresion(){
            //Arrange
            when(disp.conectarSensorPresion()).thenReturn(false);
            //Act
            boolean res = ronquidos.inicializar();
            //Assert
            assertFalse(res);
        }

        @Test
        public void InicializarPresionConectadaPeroNoSonido(){
            //Arrange
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(false);
            //Act
            boolean res = ronquidos.inicializar();
            //Assert
            assertFalse(res);
        }

        @Test
        public void InicializarMalConfiguradaPresion(){
            //Arrange
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(false);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);
            //Act
            boolean res = ronquidos.inicializar();
            //Assert
            assertFalse(res);
        }

        @Test
        public void InicializarMalConfiguradoSonido(){
            //Arrange
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(false);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);
            //Act
            boolean res = ronquidos.inicializar();
            //Assert
            assertFalse(res);
        }

        @Test
        public void InicializarMalConfiguradoSonidoYPresion(){
            //Arrange
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(false);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(false);
            //Act
            boolean res = ronquidos.inicializar();
            //Assert
            assertFalse(res);
        }

        @Test
        public void InicializarCorrectamente(){
            //Arrange
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);
            //Act
            boolean res = ronquidos.inicializar();
            //Assert
            verify(disp).configurarSensorPresion();
            verify(disp).configurarSensorSonido();
            assertTrue(res);
        }
    }

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */
    @Nested
    @DisplayName("funcion para reconectar los sensores de presion y el sonido")
    public class ronQI2TestReconectar {
        
        @Test
        public void reconectarDispConectado(){
                        //Arrange
                        when(disp.estaConectado()).thenReturn(true);
                        //Act
                        boolean res = ronquidos.reconectar();
                        //Assert
                        assertFalse(res);
        }

        @Test
        public void reconectarDispNoConectadoPresionYSonidoNoConectados(){
                        //Arrange
                        when(disp.estaConectado()).thenReturn(false);
                        when(disp.conectarSensorPresion()).thenReturn(false);
                        when(disp.conectarSensorSonido()).thenReturn(false);
                        //Act
                        boolean res = ronquidos.reconectar();
                        //Assert
                        assertFalse(res);
        }

        @Test
        public void reconectarDispNoConectadoPresionConectado(){
                        //Arrange
                        when(disp.estaConectado()).thenReturn(false);
                        when(disp.conectarSensorPresion()).thenReturn(true);
                        when(disp.conectarSensorSonido()).thenReturn(false);
                        //Act
                        boolean res = ronquidos.reconectar();
                        //Assert
                        assertFalse(res);
        }

        @Test
        public void reconectarDispNoConectadoSonidoConectado(){
                        //Arrange
                        when(disp.estaConectado()).thenReturn(false);
                        when(disp.conectarSensorPresion()).thenReturn(false);
                        when(disp.conectarSensorSonido()).thenReturn(true);
                        //Act
                        boolean res = ronquidos.reconectar();
                        //Assert
                        assertFalse(res);
        }

        @Test
        public void reconectarDispNoConectadoPresionYSonidoConectados(){
                        //Arrange
                        when(disp.estaConectado()).thenReturn(false);
                        when(disp.conectarSensorPresion()).thenReturn(true);
                        when(disp.conectarSensorSonido()).thenReturn(true);
                        //Act
                        boolean res = ronquidos.reconectar();
                        //Assert
                        assertTrue(res);
        }
    }

    @Nested
    @DisplayName("funcion para comprobar si el dispositivo esta conectado")
    public class ronQI2TestEstaConectado {
        
        @Test
        public void estaConectadoTrue(){
                        //Arrange
                        when(disp.estaConectado()).thenReturn(true);
                        //Act
                        boolean res = ronquidos.estaConectado();
                        //Assert
                        assertTrue(res);
        }

        @Test
        public void estaConectadoFalse(){
                        //Arrange
                        when(disp.estaConectado()).thenReturn(false);
                        //Act
                        boolean res = ronquidos.estaConectado();
                        //Assert
                        assertFalse(res);
        }
    }

    @Nested
    @DisplayName("Pruebas de obtenerNuevaLectura")
    public class ObtenerNuevaLecturaTests{

        @Test
        @DisplayName("Lectura correcta")
        public void RonQI2SilverObtenerNuevaLecturaPrimeraLectura(){
            //Arrange
            //Act
            ronquidos.obtenerNuevaLectura();
            //Assert
            verify(disp).leerSensorPresion();
            verify(disp).leerSensorSonido();
        }

        @Test
        @DisplayName("Lectura correcta con lista llena")
        public void RonQI2SilverObtenerNuevaLecturaListaLlena(){
            //Arrange
            for(int i = 0; i < 5; i++){
                ronquidos.obtenerNuevaLectura();
            }
            //Act
            ronquidos.obtenerNuevaLectura();
            //Assert
            verify(disp, times(6)).leerSensorPresion();
            verify(disp, times(6)).leerSensorSonido();
        }
    }
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     * /
     
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */

     @Nested
     @DisplayName("Pruebas de evaluarApneaSuenyo")
     public class EvaluarApneaSuenyoTests{
 
         @Test
         @DisplayName("Evaluacion con lista vacia")
         public void RonQI2SilverEvaluarApneaSuenyoListaVacia(){
             //Arrange
             //Act
             boolean res = ronquidos.evaluarApneaSuenyo();
             //Assert
             assertFalse(res);
         }
 
         @Test
         @DisplayName("Evaluacion con mas de 0 lecturas")
         public void RonQI2SilverEvaluarApneaSuenyoConAlgunaLectura(){
             //Arrange
             when(disp.leerSensorPresion()).thenReturn(35.0f);
             when(disp.leerSensorSonido()).thenReturn(15.0f);
             ronquidos.obtenerNuevaLectura();
             //Act
             boolean res = ronquidos.evaluarApneaSuenyo();
             //Assert
             assertFalse(res);
         }
 
         @ParameterizedTest
         @ValueSource(ints = {4, 5, 10})
         public void RonQI2SilverEvaluarApneaSuenyoConVariasLecturas(int lecturas){
             //Arrange
             for(int i = 0; i < lecturas; i++){
                 when(disp.leerSensorPresion()).thenReturn(i + 17.0f);
                 when(disp.leerSensorSonido()).thenReturn(i + 27.0f);
                 ronquidos.obtenerNuevaLectura();
             }
             //Act
             boolean res = ronquidos.evaluarApneaSuenyo();
             //Assert
             assertEquals(lecturas >= 10, res);
         }
     }
}
