package interfaz;

public class Localidad {
	
	private String nombre;
	private String provincia;
	private double longitud;
	private double latitud;
	
	public Localidad (String provincia, String nombre, double latitud, double longitud) {
		this.provincia = provincia;
		this.nombre = nombre;
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public String getNombre() {
		return nombre;
	}

	public String getProvincia() {
		return provincia;
	}

	public double getLongitud() {
		return longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	@Override
	public String toString() {
		return "Localidad:" + nombre + ", Provincia de:" + provincia + ", Longitud:" + longitud + ", Latitud:" + latitud;
	}
	
}
