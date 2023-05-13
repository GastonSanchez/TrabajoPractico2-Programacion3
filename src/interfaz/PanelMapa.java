package interfaz;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import javax.swing.JLayeredPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;

public class PanelMapa {

	private JFrame frame;
	private JTextField nombreLocalidadTxt;
	private JTextField latitudTxt;
	private JTextField longitudTxt;
	private static List<Localidad> localidades;
	private JMapViewer mapa;
	private JTextField CostoKMtxt;
	private JTextField mayorA300Txt;
	private JTextField entreProvinciasTxt;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelMapa window = new PanelMapa();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PanelMapa() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(135, 206, 250));
		frame.setBounds(50, 50, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Mapa de Red Telefonica");
		
		//LABELS
		JLabel lblSeleccionProvincia = new JLabel("Seleccionar Provincia:");
		lblSeleccionProvincia.setBounds(10, 216, 136, 14);
		frame.getContentPane().add(lblSeleccionProvincia);
		
		JLabel lblLocalidadNombre = new JLabel("Localidad:");
		lblLocalidadNombre.setBounds(10, 263, 68, 14);
		frame.getContentPane().add(lblLocalidadNombre);
		
		JLabel lblLatitud = new JLabel("Latitud:");
		lblLatitud.setBounds(10, 308, 50, 14);
		frame.getContentPane().add(lblLatitud);
		
		JLabel lblLongitud = new JLabel("Longitud:");
		lblLongitud.setBounds(10, 357, 70, 15);
		frame.getContentPane().add(lblLongitud);
		
		JLabel lblCostoTotal = new JLabel("Costo total de la red:");
		lblCostoTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCostoTotal.setBounds(20, 454, 140, 14);
		lblCostoTotal.setVisible(false);
		frame.getContentPane().add(lblCostoTotal);
		
		JLabel lblCostoNro = new JLabel("");
		lblCostoNro.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCostoNro.setBounds(41, 479, 89, 14);
		lblCostoNro.setVisible(false);
		frame.getContentPane().add(lblCostoNro);
		
		//COMBOBOX DE PROVINCIAS
		JComboBox comboProvincias = new JComboBox();
		comboProvincias.setEnabled(false);
		comboProvincias.setModel(new DefaultComboBoxModel(new String[] {
				"Buenos Aires", 
				"Ciudad Autonoma de Buenos Aires", 
				"Catamarca", "Chaco", "Chubut", 
				"Cordoba", "Corrientes", "Entre Rios",
				"Formosa", "Jujuy", "La Pampa",
				"La Rioja", "Mendoza", "Misiones",
				"Neuquen", "Rio Negro", "Salta",
				"San Juan", "San Luis", "Santa Cruz",
				"Santa Fe", "Santiago del Estero",
				"Tierra del Fuego", "Tucuman"}));
		comboProvincias.setBounds(10, 230, 150, 22);
		frame.getContentPane().add(comboProvincias);
		
		//TEXTFIELDS
		nombreLocalidadTxt = new JTextField();
		nombreLocalidadTxt.setEnabled(false);
		nombreLocalidadTxt.setBounds(10, 277, 150, 22);
		frame.getContentPane().add(nombreLocalidadTxt);
		nombreLocalidadTxt.setColumns(10);
		
		latitudTxt = new JTextField();
		latitudTxt.setEnabled(false);
		latitudTxt.setBounds(10, 322, 150, 22);
		frame.getContentPane().add(latitudTxt);
		latitudTxt.setColumns(10);
		
		longitudTxt = new JTextField();
		longitudTxt.setEnabled(false);
		longitudTxt.setBounds(10, 371, 150, 22);
		frame.getContentPane().add(longitudTxt);
		longitudTxt.setColumns(10);
		
		localidades = new ArrayList<Localidad>();
		
		//COSTOS
		JLabel lblCostoKM = new JLabel("Costo ($) por KM:");
		lblCostoKM.setBounds(10, 24, 89, 14);
		frame.getContentPane().add(lblCostoKM);
		
		CostoKMtxt = new JTextField();
		CostoKMtxt.setBounds(10, 37, 150, 22);
		frame.getContentPane().add(CostoKMtxt);
		CostoKMtxt.setText("10");
		CostoKMtxt.setColumns(10);
		
		JLabel lblMayorA300 = new JLabel("(%) mayor a 300KM:");
		lblMayorA300.setBounds(10, 65, 150, 14);
		frame.getContentPane().add(lblMayorA300);
		
		mayorA300Txt = new JTextField();
		mayorA300Txt.setBounds(10, 78, 150, 22);
		frame.getContentPane().add(mayorA300Txt);
		mayorA300Txt.setText("50");
		mayorA300Txt.setColumns(10);
		
		JLabel lblEntreProvincias = new JLabel("Costo ($) entre Provincias:");
		lblEntreProvincias.setBounds(10, 106, 150, 14);
		frame.getContentPane().add(lblEntreProvincias);
		
		entreProvinciasTxt = new JTextField();
		entreProvinciasTxt.setBounds(10, 120, 150, 22);
		frame.getContentPane().add(entreProvinciasTxt);
		entreProvinciasTxt.setText("1000");
		entreProvinciasTxt.setColumns(10);
				
		//BOTONES
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setEnabled(false);
		btnAgregar.setBounds(10, 421, 150, 22);
		frame.getContentPane().add(btnAgregar);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Localidad agregar = new Localidad (
					comboProvincias.getSelectedItem().toString(),
					nombreLocalidadTxt.getText(),  
					latitudTxt.getText(),
					longitudTxt.getText());
				localidades.add(agregar);
				
				nombreLocalidadTxt.setText("");  
				latitudTxt.setText("");
				longitudTxt.setText("");

				Coordinate p = new Coordinate(agregar.getLatitud(), agregar.getLongitud());
				String nombrePunto = agregar.getNombre() + "("+agregar.getLatitud()+","+ agregar.getLongitud()+")";
				MapMarkerDot punto = new MapMarkerDot(nombrePunto,p);
				punto.setColor(Color.RED);
				mapa.addMapMarker(punto);
			}
		});
		//MAPA
		mapa = new JMapViewer();
		mapa.setSize(606, 563);
		mapa.setLocation(180, 0);
		Coordinate inicio = new Coordinate(-40, -60);
		mapa.setDisplayPosition(inicio,4);
		frame.getContentPane().add(mapa);
		
		//CREAR RED
		JButton btnCrearRed = new JButton("Crear Red");
		btnCrearRed.setEnabled(false);
		btnCrearRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grafo g = new Grafo(localidades.size());
				int costoKM = Integer.parseInt(CostoKMtxt.getText());
				double costo300KM = Double.parseDouble(mayorA300Txt.getText());
				int costoEntreProvincias = Integer.parseInt(entreProvinciasTxt.getText());
				int posicion = 0;
				double costoTotalRed = 0;
				
				for (Localidad l : localidades) {
					g.agregarLocalidad(l, posicion,costoKM, costo300KM, costoEntreProvincias);
					posicion++;
				}
				
				double [][] red = g.calcularArbol();
				
				for(int fila = 0; fila < red.length; fila++) {
					for(int col = 0; col < red.length; col++) {
						if(red[fila][col]!=0) {
							ArrayList<Coordinate> conexion = new ArrayList<Coordinate>();
							conexion.add(new Coordinate(localidades.get(fila).getLatitud(), localidades.get(fila).getLongitud()));
							conexion.add(new Coordinate(localidades.get(fila).getLatitud(), localidades.get(fila).getLongitud()));
							conexion.add(new Coordinate(localidades.get(col).getLatitud(), localidades.get(col).getLongitud()));
							MapPolygon linea = new MapPolygonImpl(conexion);
							linea.getStyle().setColor(Color.BLUE);
							mapa.addMapPolygon(linea);
							
							costoTotalRed = costoTotalRed + red[fila][col];
						}
					}
				}
				lblCostoTotal.setVisible(true);
				lblCostoNro.setText("$"+costoTotalRed);
				lblCostoNro.setVisible(true);
			}
		});
		btnCrearRed.setBounds(10, 530, 150, 22);
		frame.getContentPane().add(btnCrearRed);
		
		//GUARDAR CONFIGURACION COSTOS
		JButton btnSaveConfig = new JButton("Guardar Configuracion");
		btnSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CostoKMtxt.setEnabled(false);
				mayorA300Txt.setEnabled(false);
				entreProvinciasTxt.setEnabled(false);
				btnSaveConfig.setEnabled(false);
				
				comboProvincias.setEnabled(true);
				nombreLocalidadTxt.setEnabled(true);
				latitudTxt.setEnabled(true);
				longitudTxt.setEnabled(true);
				btnAgregar.setEnabled(true);
				btnCrearRed.setEnabled(true);
			}
		});
		btnSaveConfig.setBounds(10, 153, 150, 22);
		frame.getContentPane().add(btnSaveConfig);
		
	}
}
