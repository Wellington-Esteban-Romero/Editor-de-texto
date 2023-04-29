package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JMenuItem;

import editor.Editor;

public class EdicionActionController implements ActionListener {

	private Editor editor;

	public EdicionActionController(Editor editor) {
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JMenuItem menuItem = ((JMenuItem)e.getSource());

		switch (menuItem.getText()) {
		case "Hora y Fecha":
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm dd/MM/yyyy");
			this.editor.getEditor().setText(this.editor.getEditor().getText() + sd.format(new Date()));
			break;
		}
	}
}
