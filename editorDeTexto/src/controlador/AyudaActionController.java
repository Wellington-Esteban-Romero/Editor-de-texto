package controlador;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import constantes.Const;
import editor.Editor;

public class AyudaActionController implements ActionListener {

	private Editor editor;

	public AyudaActionController(Editor editor) {
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JMenuItem menuItem = ((JMenuItem)e.getSource());

		switch (menuItem.getText()) {
		case Const.VER_AYUDA:
			try {

				Desktop.getDesktop().browse(new URI("http://www.google.com/search?q=" + URLEncoder.encode("obtener ayuda con el bloc de notas en windows 10",StandardCharsets.UTF_8) ));
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}

			break;

		case Const.ENVIAR_COMENTARIOS:

			try {
				Desktop.getDesktop().open(new File("bloc.txt"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			break;

		case Const.ACERCA_NOTAS:
			
			JOptionPane.showMessageDialog(this.editor, "Demo Block de Notas", "Acerca de ....", JOptionPane.PLAIN_MESSAGE);

			break;

		}

	}

}
