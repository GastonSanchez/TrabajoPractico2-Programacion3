package interfaz;

public class Localidad {
	
	private String nombre;
	private String provincia;
	private double longitud;
	private double latitud;
	
	public Localidad (String provincia, String nombre, String latitud, String longitud) {
		this.provincia = provincia;
		this.nombre = nombre;
		this.latitud = Double.parseDouble(latitud);
		this.longitud = Double.parseDouble(longitud);
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
}
