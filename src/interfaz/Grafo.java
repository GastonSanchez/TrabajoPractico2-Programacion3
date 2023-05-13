package interfaz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Grafo {
	private double [][] D;
	private double [][] Arbol;
	private Localidad [] localidades;
	private ArrayList<Integer> posicionesLoc;
	
	public Grafo (int vertices) 
	{
		D = new double[vertices][vertices];
		Arbol = new double[vertices][vertices];
		localidades = new Localidad[vertices];
	}	
	
	public void agregarLocalidad(Localidad l, int posicion, int costoKM, double costo300KM, int costoEntreProvincias)
	{
		localidades[posicion] = l;
		costo300KM = (costo300KM/100) + 1;
		
		for(int i = 0; i < localidades.length; ++i) if (i != posicion && localidades[i] != null) {
			double distancia = calcularDistancia(localidades[posicion].getLatitud(), localidades[posicion].getLongitud(), localidades[i].getLatitud(), localidades[i].getLongitud());
			double costoTotal = distancia * costoKM;
			
			if(distancia > 300) {
				costoTotal = costoTotal * costo300KM;
			}
			
			if(localidades[posicion].getProvincia() != localidades[i].getProvincia()) {
				costoTotal = costoTotal + costoEntreProvincias; 
			}
			
			D[posicion][i] = costoTotal;
			D[i][posicion] = costoTotal;
		}
		
	}
	
	public double[][] calcularArbol() {
		posicionesLoc = new ArrayList<Integer>();
		posicionesLoc.add(0);
		for (int i = 0; i < localidades.length - 1; i++) {
			crearArbol();
		}
		
		return Arbol;
	}
	
	public void crearArbol () {
		double distanciaMinima = Double.MAX_VALUE;
		int filaActual = 0;
		int columnaActual = 0;
		
		for (int posiciones : posicionesLoc) {
			for (int i = 0; i<D.length; ++i) if (!posicionesLoc.contains(i)) {
				if (D[posiciones][i]<distanciaMinima) {
					distanciaMinima = D[posiciones][i];
					filaActual = posiciones;
					columnaActual = i;
				}
			}
		}
		
		posicionesLoc.add(columnaActual);
		Arbol[filaActual][columnaActual] = distanciaMinima;
	}
	
	public double calcularDistancia (double latitud1, double longitud1, double latitud2, double longitud2) {
		
		double diferencia = longitud1 - longitud2;
		double distancia = 60 * 1.1515 * (180/Math.PI) * Math.acos(
				Math.sin(latitud1 * (Math.PI/180)) * Math.sin(latitud2 * (Math.PI/180)) +
				Math.cos(latitud1 * (Math.PI/180)) * Math.cos(latitud2 * (Math.PI/180)) *
				Math.cos(diferencia * (Math.PI/180)));
		
		return Math.round(distancia * 1.609344);	
	}
}
