package interfaz;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

public class Logica {
	private List<Localidad> localidades;
	private int costoKM;
	private double costo300KM;
	private int costoEntreProvincias;
	private double costoTotalRed;
	
	public Logica() {
		this.localidades = new ArrayList<Localidad>();
		this.costoTotalRed = 0;
	}
	
	public void guardarConfiguracionCostos(int costoKM, double costo300KM, int costoEntreProvincias) {
		if(costoKM <=0 || costo300KM <=0 || costoEntreProvincias <=0)
			throw new IllegalArgumentException("Los costos deben ser mayores a 0!");
		this.costoKM = costoKM;
		this.costo300KM = (costo300KM/100) + 1; //Porcentaje
		this.costoEntreProvincias = costoEntreProvincias;
	}

	public void agregarLocalidad (Localidad l) {
		if(this.localidades.contains(l))
			throw new IllegalArgumentException("La localidad ya se encuentra agregada!");
		this.localidades.add(l);
	}
	
	public int cantidadDeLocalidades() {
		return localidades.size();
	}
	
	public MapMarkerDot crearPunto(Localidad loc) {
		Coordinate p = new Coordinate(loc.getLatitud(), loc.getLongitud());
		String nombrePunto = loc.getNombre() + "("+loc.getLatitud()+","+ loc.getLongitud()+")";
		MapMarkerDot punto = new MapMarkerDot(nombrePunto,p);
		punto.setColor(Color.RED);
		return punto;
	}
	
	public Grafo crearGrafoDeLocalidades() {
		if(localidades.isEmpty())
			throw new RuntimeException("Agregue localidades para crear una red!");
		
		try {
			crearArchivoDeLocalidades();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Grafo g = new Grafo(localidades.size());
		agregarLocalidadesAlGrafo(g);
		return g;
	}
	
	private void agregarLocalidadesAlGrafo(Grafo g)
	{		
		int fila = 0;
		for (Localidad l : localidades) {
			for(int col = fila; col < localidades.size(); ++col) if (col != fila) {
				double distancia = calcularDistancia(l.getLatitud(), l.getLongitud(), localidades.get(col).getLatitud(), localidades.get(col).getLongitud());
				double costoEntreLocalidades = distancia * costoKM;
				
				if(distancia > 300)
					costoEntreLocalidades = costoEntreLocalidades * costo300KM;
				
				if(l.getProvincia() != localidades.get(col).getProvincia())
					costoEntreLocalidades = costoEntreLocalidades + costoEntreProvincias; 
				
				g.agregarArista(fila, col, costoEntreLocalidades);
			}
			fila++;
		}
	}
	
	private void crearArchivoDeLocalidades() throws FileNotFoundException {
		try {
		FileOutputStream fos = new FileOutputStream("Localidades.txt");
		OutputStreamWriter out = new OutputStreamWriter(fos);
		
		
			for (Localidad localidad : localidades) {
				out.write(localidad.toString() + "\r\n");
				}
			out.close();
		}
		catch (Exception ex) {
		}
	}

	public List<MapPolygon> crearRedTelefonica(Grafo g) {
		costoTotalRed = 0;
		List<MapPolygon> lineasDeLaRed = new ArrayList<MapPolygon>();
		double [][] redTelefonica = g.calcularArbol();
		
		for(int fila = 0; fila < redTelefonica.length; fila++) {
			for(int col = 0; col < redTelefonica.length; col++) {
				if(redTelefonica[fila][col]!=0) {
					ArrayList<Coordinate> conexion = new ArrayList<Coordinate>();
					conexion.add(new Coordinate(localidades.get(fila).getLatitud(), localidades.get(fila).getLongitud()));
					conexion.add(new Coordinate(localidades.get(fila).getLatitud(), localidades.get(fila).getLongitud()));
					conexion.add(new Coordinate(localidades.get(col).getLatitud(), localidades.get(col).getLongitud()));
					MapPolygon linea = new MapPolygonImpl(conexion);
					linea.getStyle().setColor(Color.BLUE);
					lineasDeLaRed.add(linea);
					
					costoTotalRed = costoTotalRed + redTelefonica[fila][col];
				}
			}
		}
		
		return lineasDeLaRed;
	}
	
	public double obtenerCostoTotal() {
		return costoTotalRed;
	}
	
	private double calcularDistancia (double latitud1, double longitud1, double latitud2, double longitud2) {
		
		double diferencia = longitud1 - longitud2;
		double distancia = 60 * 1.1515 * (180/Math.PI) * Math.acos(
				Math.sin(latitud1 * (Math.PI/180)) * Math.sin(latitud2 * (Math.PI/180)) +
				Math.cos(latitud1 * (Math.PI/180)) * Math.cos(latitud2 * (Math.PI/180)) *
				Math.cos(diferencia * (Math.PI/180))
				);
		
		return Math.round(distancia * 1.609344);	
	}
}
