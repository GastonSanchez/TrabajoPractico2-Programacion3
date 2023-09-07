package interfaz;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class PanelMapa {

	private JFrame frame;
	private JTextField nombreLocalidadTxt;
	private JTextField latitudTxt;
	private JTextField longitudTxt;
	private Logica logica;
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
		logica = new Logica();
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(135, 206, 250));
		frame.setBounds(50, 50, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Mapa de Red Telefonica");
		
		//LABELS		
		JLabel lblCostoKM = new JLabel("Costo ($) por KM:");
		lblCostoKM.setBounds(10, 24, 89, 14);
		frame.getContentPane().add(lblCostoKM);
		
		JLabel lblMayorA300 = new JLabel("(%) mayor a 300KM:");
		lblMayorA300.setBounds(10, 65, 150, 14);
		frame.getContentPane().add(lblMayorA300);
		
		JLabel lblEntreProvincias = new JLabel("Costo ($) entre Provincias:");
		lblEntreProvincias.setBounds(10, 106, 150, 14);
		frame.getContentPane().add(lblEntreProvincias);
		
		JLabel lblSeleccionProvincia = new JLabel("Seleccionar Provincia:");
		lblSeleccionProvincia.setBounds(10, 216, 136, 14);
		frame.getContentPane().add(lblSeleccionProvincia);
		
		JLabel lblLocalidadNombre = new JLabel("Localidad(*):");
		lblLocalidadNombre.setBounds(10, 263, 89, 14);
		frame.getContentPane().add(lblLocalidadNombre);
		
		JLabel lblLatitud = new JLabel("Latitud(*):");
		lblLatitud.setBounds(10, 308, 89, 14);
		frame.getContentPane().add(lblLatitud);
		
		JLabel lblLongitud = new JLabel("Longitud(*):");
		lblLongitud.setBounds(10, 357, 89, 15);
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
		JComboBox<Object> comboProvincias = new JComboBox<Object>();
		comboProvincias.setEnabled(false);
		comboProvincias.setModel(new DefaultComboBoxModel<Object>(new String[] {
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
		CostoKMtxt = new JTextField();
		CostoKMtxt.setBounds(10, 37, 150, 22);
		frame.getContentPane().add(CostoKMtxt);
		CostoKMtxt.setText("10");
		CostoKMtxt.setColumns(10);

		mayorA300Txt = new JTextField();
		mayorA300Txt.setBounds(10, 78, 150, 22);
		frame.getContentPane().add(mayorA300Txt);
		mayorA300Txt.setText("50");
		mayorA300Txt.setColumns(10);
		
		entreProvinciasTxt = new JTextField();
		entreProvinciasTxt.setBounds(10, 120, 150, 22);
		frame.getContentPane().add(entreProvinciasTxt);
		entreProvinciasTxt.setText("1000");
		entreProvinciasTxt.setColumns(10);
		
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
		
		//MAPA
		mapa = new JMapViewer();
		mapa.setSize(606, 563);
		mapa.setLocation(180, 0);
		Coordinate inicio = new Coordinate(-40, -60);
		mapa.setDisplayPosition(inicio,4);
		frame.getContentPane().add(mapa);
		
		JLabel lblAviso = new JLabel("AVISO");
		lblAviso.setBounds(21, 194, 551, 113);
		lblAviso.setVisible(false);
		
		JButton btnAceptarAviso = new JButton("Aceptar");
		btnAceptarAviso.setBounds(421, 273, 89, 23);
		btnAceptarAviso.setVisible(false);
		mapa.add(btnAceptarAviso);
		btnAceptarAviso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblAviso.setVisible(false);
				btnAceptarAviso.setVisible(false);
			}
		});
		
		mapa.add(lblAviso);
		lblAviso.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblAviso.setForeground(Color.RED);
		lblAviso.setHorizontalAlignment(SwingConstants.CENTER);
		lblAviso.setBackground(Color.WHITE);
		
		//BOTONES
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setEnabled(false);
		btnAgregar.setBounds(10, 421, 150, 22);
		frame.getContentPane().add(btnAgregar);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Localidad localidadNueva = new Localidad (comboProvincias.getSelectedItem().toString(), nombreLocalidadTxt.getText(),  
							Double.parseDouble(latitudTxt.getText()), Double.parseDouble(longitudTxt.getText()));
					try {
						logica.agregarLocalidad(localidadNueva);
						mapa.addMapMarker(logica.crearPunto(localidadNueva));
					}
					catch (Exception e1) {
						lblAviso.setVisible(true);
						lblAviso.setText(e1.getMessage());
						btnAceptarAviso.setVisible(true);
					}
					nombreLocalidadTxt.setText("");
					latitudTxt.setText("");
					longitudTxt.setText("");
				}
				catch (Exception e1) {
					lblAviso.setVisible(true);
					lblAviso.setText("Campos obligatorios incompletos o incorrectos!");
					btnAceptarAviso.setVisible(true);
				}
			}
		});
		
		JButton btnCrearRed = new JButton("Crear Red");
		btnCrearRed.setEnabled(false);
		btnCrearRed.setBounds(10, 530, 150, 22);
		frame.getContentPane().add(btnCrearRed);
		btnCrearRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Grafo g = logica.crearGrafoDeLocalidades();
					List<MapPolygon> poligonos = logica.crearRedTelefonica(g);
					for (MapPolygon p : poligonos) {
						mapa.addMapPolygon(p);
					}
					lblCostoTotal.setVisible(true);
					lblCostoNro.setText("$"+logica.obtenerCostoTotal());
					lblCostoNro.setVisible(true);
				}
				catch (Exception e1) {
					lblAviso.setVisible(true);
					lblAviso.setText(e1.getMessage());
					btnAceptarAviso.setVisible(true);
				}
			}
		});
		
		JButton btnSaveConfig = new JButton("Guardar Configuracion");
		btnSaveConfig.setBounds(10, 153, 150, 22);
		frame.getContentPane().add(btnSaveConfig);
		btnSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logica.guardarConfiguracionCostos(Integer.parseInt(CostoKMtxt.getText()), Double.parseDouble(mayorA300Txt.getText()), Integer.parseInt(entreProvinciasTxt.getText()));
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
				catch (Exception e1) {
					lblAviso.setVisible(true);
					lblAviso.setText(e1.getMessage());
					btnAceptarAviso.setVisible(true);
				}
			}
		});
	}
}
