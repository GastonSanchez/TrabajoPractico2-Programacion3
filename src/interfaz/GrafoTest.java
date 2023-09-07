package interfaz;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class GrafoTest {

	@Test
	public void agregarAristaNegativaTest() {
		Grafo g = new Grafo(5);
		assertThrows(IllegalArgumentException.class, ()->g.agregarArista(0, 0, -1));
	}
	
	@Test
	public void agregarAristaCeroTest() {
		Grafo g = new Grafo(5);
		assertThrows(IllegalArgumentException.class, ()->g.agregarArista(0, 0, 0));
	}
	
	@Test
	public void calcularArbolTest() {
		Grafo g = new Grafo(4);
		g.agregarArista(0, 1, 5);
		g.agregarArista(0, 2, 3);
		g.agregarArista(0, 3, 4);
		g.agregarArista(1, 2, 1);
		g.agregarArista(1, 3, 6);
		g.agregarArista(2, 3, 8);
		//[0][5][3][4]
		//[5][0][1][6]
		//[3][1][0][8]
		//[4][6][8][0]
		
		double [][] matrizTest = {{0,0,3,4},
								  {0,0,0,0},
								  {0,1,0,0},
								  {0,0,0,0}};

		assertTrue(Arrays.deepEquals(matrizTest, g.calcularArbol()));
	}
}
