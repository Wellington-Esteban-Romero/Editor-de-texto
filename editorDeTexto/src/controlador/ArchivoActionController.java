package controlador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import constantes.Const;
import editor.Editor;
import utils.Utils;

public class ArchivoActionController implements ActionListener {

	private Editor editor;

	public ArchivoActionController(Editor editor) {
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JMenuItem menuItem = ((JMenuItem)e.getSource());

		switch (menuItem.getText()) {

		case Const.NUEVO:
			this.editor.setFichero(null);
			this.editor.getEditor().setText("");
			this.editor.getEditor().setFont(new Font("Consolas", Font.PLAIN, 14));
			this.editor.setNombreArchivo("Sin título:");
			this.editor.setTitle(this.editor.getNombreArchivo() + Editor.getTitulo());

			break;
		case Const.NUEVOVENTANA:
			new Editor();
			break;

		case Const.ABRIR:
			this.editor.openFile();
			break;

		case Const.GUARDAR:

			StandardOpenOption option;

			try {
				if (this.editor.getFichero() != null && this.editor.getFichero().exists()) {
					this.editor.guardarFichero(this.editor.getFichero().toPath(), this.editor.getEditor().getText(), StandardOpenOption.WRITE);
					this.editor.setTitle(this.editor.getNombreArchivo() + Editor.getTitulo());
					return;
				}

				this.editor.guardarComo();

				if (this.editor.getFichero()!=null && this.editor.getFichero().exists())
					option = StandardOpenOption.WRITE;
				else
					option = StandardOpenOption.CREATE;

				if (this.editor.getFichero()!=null)
					this.editor.guardarFichero(this.editor.getFichero().toPath(), this.editor.getEditor().getText(), option);

				String tituloArchivo = Utils.getNombreArchivo(this.editor.getFichero());

				this.editor.setTitle( tituloArchivo == null? this.editor.getNombreArchivo() : tituloArchivo + Editor.getTitulo());

			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "No se ha podido guardar el fichero : " + Utils.getNombreArchivo(this.editor.getFichero()));
				ex.printStackTrace();
			}
			break;
		case Const.GUARDARCOMO:
			try {
				this.editor.guardarComo();
				String tituloArchivo = Utils.getNombreArchivo(this.editor.getFichero());
				if (this.editor.getFichero() != null && !this.editor.getFichero().exists()) {
					this.editor.guardarFichero(this.editor.getFichero().toPath(), this.editor.getEditor().getText(), StandardOpenOption.CREATE);
					this.editor.setNombreArchivo(this.editor.getFichero().getName());
					this.editor.setTitle(tituloArchivo == null? this.editor.getNombreArchivo() : tituloArchivo + Editor.getTitulo());
				} else {
					this.editor.guardarFichero(this.editor.getFichero().toPath(), this.editor.getEditor().getText(), StandardOpenOption.WRITE);
					this.editor.setTitle(tituloArchivo == null? this.editor.getNombreArchivo() : tituloArchivo + Editor.getTitulo());
				} 
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "No se ha podido guardar el fichero : " + Utils.getNombreArchivo(this.editor.getFichero()));
				ex.printStackTrace();
			}
			break;
		case Const.SALIR:
			System.exit(0);
			break;
		}
	}
}
