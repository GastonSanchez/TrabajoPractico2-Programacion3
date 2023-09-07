package interfaz;

import java.util.ArrayList;

public class Grafo {
	private double [][] Matriz;
	private double [][] Arbol;
	private ArrayList<Integer> localidadesAgregadas;
	
	public Grafo (int vertices) 
	{
		Matriz = new double[vertices][vertices];
		Arbol = new double[vertices][vertices];
	}
	
	public void agregarArista(int fila, int col, double valor) {
		if(valor <=0 )
			throw new IllegalArgumentException();
		Matriz[fila][col] = valor;
		Matriz[col][fila] = valor;
	}
	
	public double[][] calcularArbol() {
		localidadesAgregadas = new ArrayList<Integer>();
		localidadesAgregadas.add(0);
		for (int i = 0; i < Matriz.length - 1; i++) {
			prim();
		}
		
		return Arbol;
	}
	
	private void prim () {
		double costoMinimo = Double.MAX_VALUE;
		int localidadEnElArbol = 0;
		int localidadAConectar = 0;
		
		for (int l : localidadesAgregadas) {
			for (int loc = 0; loc<Matriz.length; ++loc) if (!localidadesAgregadas.contains(loc)) {
				if (Matriz[l][loc]<costoMinimo) {
					costoMinimo = Matriz[l][loc];
					localidadEnElArbol = l;
					localidadAConectar = loc;
				}
			}
		}
		
		localidadesAgregadas.add(localidadAConectar);
		Arbol[localidadEnElArbol][localidadAConectar] = costoMinimo;
	}
	
}
