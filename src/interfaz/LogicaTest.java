package interfaz;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

public class LogicaTest {

	@Test
	public void costosNegativostest() {
		Logica l = new Logica();
		assertThrows(IllegalArgumentException.class, ()->l.guardarConfiguracionCostos(-1, -10, -1));
	}
	
	@Test
	public void costosCerotest() {
		Logica l = new Logica();
		assertThrows(IllegalArgumentException.class, ()->l.guardarConfiguracionCostos(0, 0, 0));
	}
	
	@Test
	public void agregarLocalidadTest() {
		Logica l = new Logica();
		Localidad loc= new Localidad("Provincia","Localidad",-34,-56);
		l.agregarLocalidad(loc);
		assertEquals(1, l.cantidadDeLocalidades());
	}
	
	@Test
	public void agregarDosLocalidadesTest() {
		Logica l = new Logica();
		Localidad loc1= new Localidad("Provincia1","Localidad1",-34,-56);
		Localidad loc2= new Localidad("Provincia2","Localidad2",-33,-55);
		l.agregarLocalidad(loc1);
		l.agregarLocalidad(loc2);
		assertEquals(2, l.cantidadDeLocalidades());
	}
	
	@Test
	public void agregarLocalidadRepetidaTest() {
		Logica l = new Logica();
		Localidad loc= new Localidad("Provincia","Localidad",-34,-56);
		l.agregarLocalidad(loc);
		assertThrows(IllegalArgumentException.class, ()->l.agregarLocalidad(loc));
	}
	
	@Test
	public void crearPuntoDeUnaLocalidadTest() {
		Logica l = new Logica();
		Localidad loc= new Localidad("Provincia","Localidad",-34,-56);
		l.agregarLocalidad(loc);
		Coordinate p = new Coordinate(loc.getLatitud(), loc.getLongitud());
		String nombrePunto = loc.getNombre() + "("+loc.getLatitud()+","+ loc.getLongitud()+")";
		MapMarkerDot punto = new MapMarkerDot(nombrePunto,p);
		punto.setColor(Color.RED);

		assertEquals(punto.getCoordinate(),(l.crearPunto(loc).getCoordinate()));
	}
	
	@Test
	public void crearGrafoSinLocalidesTest() {
		Logica l = new Logica();
		assertThrows(RuntimeException.class, ()->l.crearGrafoDeLocalidades());
	}

}
