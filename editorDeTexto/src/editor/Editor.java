package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;

import constantes.Const;
import controlador.ArchivoActionController;
import controlador.AyudaActionController;
import controlador.EdicionActionController;
//import controlador.KeyController;
import controlador.ZoomActionController;

public class Editor extends JFrame {

	private Double width = 500d;
	private Double height = 400d;

	private static final String TITULO = Const.NOMBRE_APLICACION;

	private final JMenuBar menuPrincipal = new JMenuBar();

	private JEditorPane editorPanel = new JEditorPane();

	private JScrollPane scrollPane = new JScrollPane(editorPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

	//JMenu
	private final JMenu archivo =  new JMenu(Const.ARCHIVO);

	private final JMenu editar =  new JMenu(Const.EDITAR);

	private final JMenu ver =  new JMenu(Const.VER);

	private final JMenu ayuda =  new JMenu(Const.AYUDA);

	//JMenuItem - Archivo
	private final JMenuItem nuevo = new JMenuItem(Const.NUEVO);

	private final JMenuItem nuevoVentana = new JMenuItem(Const.NUEVOVENTANA);

	private final JMenuItem abrir = new JMenuItem(Const.ABRIR);

	private final JMenuItem guardar = new JMenuItem(Const.GUARDAR);

	private final JMenuItem guardarComo = new JMenuItem(Const.GUARDARCOMO);

	private final JMenuItem salir = new JMenuItem(Const.SALIR);

	//JMenuItem - Edicion

	private final JMenuItem deshacer = new JMenuItem(getEditor().getActionMap().get(DefaultEditorKit.deletePrevWordAction));

	private final JMenuItem cortar = new JMenuItem(new DefaultEditorKit.CutAction());

	private final JMenuItem copiar = new JMenuItem(new DefaultEditorKit.CopyAction());

	private final JMenuItem pegar = new JMenuItem(new DefaultEditorKit.PasteAction());

	private final JMenuItem eliminar = new JMenuItem(Const.ELIMINAR);

	private final JMenuItem buscar = new JMenuItem(Const.BUSCAR);

	private final JMenuItem seleccionarTodo = new JMenuItem(getEditor().getActionMap().get(DefaultEditorKit.selectAllAction));

	private final JMenuItem hoy = new JMenuItem(Const.HORA_FECHA);

	//Items de JMenuFormato
	//	private final JMenuItem itemAjusteLinea = new JMenuItem("Ajuste de línea");
	//
	//	private final JMenuItem itemFuente = new JMenuItem("Fuente...");

	//Items de JMenuVer
	private final JMenu zoom = new JMenu("Zoom");

	private final JMenuItem itemAcercaZoom = new JMenuItem("Acerca");

	private final JMenuItem itemAlejaZoom = new JMenuItem("Aleja");

	private final JMenuItem itemRestaurar= new JMenuItem("Restaurar zoom predeterminado");

	private final JMenuItem itemBarraEstado = new JMenuItem("Barra de estado");

	//Items de JMenuAyuda
	private final JMenuItem itemAyuda = new JMenuItem("Ver la Ayuda");

	private final JMenuItem enviarComentarios = new JMenuItem("Enviar Comentarios");

	private final JMenuItem acerca = new JMenuItem("Acerca del Block de notas");

	private String nombreArchivo = Const.NOMBRE_ARCHIVO;

	private File fichero;

	int con = 15;

	private static final long serialVersionUID = 1L;

	public Editor() {

		setMenu(archivo);
		setMenu(editar);
		//		setMenu(formato);
		setMenu(ver);
		setMenu(ayuda);

		//JMenuItem - JMenu archivo

		setMenuItem(archivo, nuevo);
		setMenuItem(archivo, nuevoVentana);
		setMenuItem(archivo, abrir);
		setMenuItem(archivo, guardar);
		setMenuItem(archivo, guardarComo);
		archivo.add(new JSeparator());
		setMenuItem(archivo, salir);

		addAccelerator(nuevo, KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
		addAccelerator(abrir, KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK);
		addAccelerator(guardar, KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK);
		addAccelerator(guardarComo, KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);


		//JMenuItem - JMenu Editar

		cortar.setText(Const.CORTAR);
		copiar.setText(Const.COPIAR);
		pegar.setText(Const.PEGAR);
		deshacer.setText(Const.DESHACER);
		seleccionarTodo.setText("Seleccionar todo");

		setMenuItemEditar(editar, deshacer);
		editar.add(new JSeparator());
		setMenuItemEditar(editar, cortar);
		setMenuItemEditar(editar, copiar);
		setMenuItemEditar(editar, pegar);
		setMenuItemEditar(editar, eliminar);
		editar.add(new JSeparator());
		setMenuItemEditar(editar, buscar);
		editar.add(new JSeparator());
		setMenuItemEditar(editar, seleccionarTodo);
		setMenuItemEditar(editar, hoy);

		addAccelerator(deshacer, KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK);
		addAccelerator(cortar, KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK);
		addAccelerator(copiar, KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK);
		addAccelerator(pegar, KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK);
		addAccelerator(buscar, KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK);
		addAccelerator(seleccionarTodo, KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK);

		//JMenuItem - ver
		setMenuZoom(zoom);
		setMenuItemVer(zoom, itemAcercaZoom);
		setMenuItemVer(zoom, itemAlejaZoom);
		setMenuItemVer(zoom, itemRestaurar);
		setMenuItemVer(ver, itemBarraEstado);

		//JMenuItem - Ayuda
		setMenuItemAyuda(ayuda, itemAyuda);
		setMenuItemAyuda(ayuda, enviarComentarios);
		ayuda.add(new JSeparator());
		setMenuItemAyuda(ayuda, acerca);

		//configuración por defecto edicion panel
		configuracionEditorPanel();

		//add menuBar al JFrame
		menuPrincipal.setBackground(Color.WHITE);
		setJMenuBar(menuPrincipal);

		addActionVentana();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		setBounds((int) (d.getWidth() - width) / 2,
				(int) (d.getHeight() - height) / 2, width.intValue(),
				height.intValue());

		//Mostrar JFrame
		initPantalla();
	}

	protected void initPantalla() {

		setTitle(nombreArchivo + TITULO);
		setLocationRelativeTo(null);
		setBounds(400, 150, 700, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		getScrollPane().setEnabled(true);
		add(getScrollPane());
	}

	protected void setMenu(JMenu menu) {
		menuPrincipal.add(menu);
	}

	protected void setMenuZoom(JMenu menu) {
		ver.add(menu);
	}

	protected void setMenuItem(JMenu menu, JMenuItem menuItem) {
		menu.add(menuItem);
		menuItem.addActionListener(new ArchivoActionController(this));
	}
	protected void setMenuItemEditar(JMenu menu, JMenuItem menuItem) {
		menu.add(menuItem);
		menuItem.addActionListener(new EdicionActionController(this));
	}
	protected void setMenuItemAyuda(JMenu menu, JMenuItem menuItem) {
		menu.add(menuItem);
		menuItem.addActionListener(new AyudaActionController(this));
	}
	protected void setMenuItemFormato(JMenu menu, JMenuItem menuItem) {
		menu.add(menuItem);
	}
	protected void setMenuItemVer(JMenu menu, JMenuItem menuItem) {
		menu.add(menuItem);
		menuItem.addActionListener(new ZoomActionController(this));
	}
	private void addAccelerator (JMenuItem menuItem, int keyEvent, int inputEvent) {
		menuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, inputEvent));
	}

	private void addActionVentana () {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

			}
		});
	}

	protected void configuracionEditorPanel () {
		editorPanel.setFont(new Font("Consolas", Font.PLAIN, 14));
	}

	public void guardarFichero (Path path, String contenido, StandardOpenOption standard) throws IOException {
		Files.write(path, contenido.getBytes(), standard);
	}

	public final void openFile() {

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Abrir");
		FileNameExtensionFilter txt = new FileNameExtensionFilter("*.TXT","txt");
		fc.setFileFilter(txt);

		int seleccion = fc.showOpenDialog(this); //Abrimos el JFileChooser y guardamos el resultado en seleccion
		if (seleccion == JFileChooser.APPROVE_OPTION) { //Si ha pulsado la opción Aceptar
			setFichero(fc.getSelectedFile());
			this.nombreArchivo = fichero.toPath().getFileName().toString();

			try {
				editorPanel.setText(Files.readString(fichero.toPath()));
				setTitle(nombreArchivo + ":" + TITULO);
				getEditor().setFont(new Font("Consolas", Font.PLAIN, 14));
				setCon(14);
			} catch (IOException ex) {
				JOptionPane.showInternalMessageDialog(null, ex);
				ex.printStackTrace();
			}
		}
	}

	public final void guardarComo () {
		JFileChooser guardar = new JFileChooser();
		guardar.setDialogTitle("Guardar como");
		FileNameExtensionFilter txt = new FileNameExtensionFilter("*.TXT","txt");
		guardar.setFileFilter(txt);
		guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int i = guardar.showSaveDialog(this);
		setFichero(guardar.getSelectedFile());
		System.out.println(getFichero());
		confirmar(guardar,i);

	}

	private final void confirmar (JFileChooser guardar, int i) {

		if (guardar.getSelectedFile() != null && !guardar.getSelectedFile().exists()) {
			setFichero(guardar.getSelectedFile());
			return;
		}
		if (guardar.getSelectedFile() != null && guardar.getSelectedFile().exists() && i == JFileChooser.APPROVE_OPTION) {
			int opcion = JOptionPane.showConfirmDialog(null, "¿Desea guardar los cambios de " + nombreArchivo + "?", "Confirmar guardar como", JOptionPane.YES_NO_OPTION , JOptionPane.WARNING_MESSAGE);
			if (opcion == JOptionPane.OK_OPTION)
				setFichero(guardar.getSelectedFile());
			if(opcion == JOptionPane.NO_OPTION)
				guardarComo ();
		} else
			setFichero(null);
	}

	public static String getTitulo() {
		return TITULO;
	}

	public JMenuItem getNuevo() {
		return nuevo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public JMenuBar getMenuPrincipal() {
		return menuPrincipal;
	}

	public JMenu getArchivo() {
		return archivo;
	}

	public JMenu getEditar() {
		return editar;
	}

	public JMenu getVer() {
		return ver;
	}

	public JMenu getAyuda() {
		return ayuda;
	}

	public JMenuItem getNuevoVentana() {
		return nuevoVentana;
	}

	public JMenuItem getAbrir() {
		return abrir;
	}

	public JMenuItem getGuardar() {
		return guardar;
	}

	public JMenuItem getGuardarComo() {
		return guardarComo;
	}

	public JMenuItem getSalir() {
		return salir;
	}

	public JMenuItem getCortar() {
		return cortar;
	}

	public JMenuItem getCopiar() {
		return copiar;
	}

	public JMenuItem getPegar() {
		return pegar;
	}

	public JMenuItem getBuscar() {
		return buscar;
	}

	public JEditorPane getEditor() {
		return editorPanel;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JEditorPane getEditorPanel() {
		return editorPanel;
	}

	public void setEditorPanel(JEditorPane editorPanel) {
		this.editorPanel = editorPanel;
	}

	public int getCon() {
		return con;
	}

	public void setCon(int con) {
		this.con = con;
	}
}
