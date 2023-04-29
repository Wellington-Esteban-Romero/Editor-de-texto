package controlador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import constantes.Const;
import editor.Editor;

public class ZoomActionController implements ActionListener {

	private Editor editor;

	int con = 15;

	public ZoomActionController(Editor editor) {
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JMenuItem menuItem = ((JMenuItem)e.getSource());

		switch (menuItem.getText()) {

		case Const.ZOOM_ACERCA:
			System.out.println("prueba");
			int i = this.editor.getCon() + 2;
			this.editor.setCon(i);
			System.out.println(this.editor.getEditor().getText());
			if (this.editor.getCon() <= 46)
				this.editor.getEditor().setFont(new Font("Consolas", Font.PLAIN, this.editor.getCon()));
			break;
		case Const.ZOOM_ALEJA:
			int j = this.editor.getCon() - 2;
			this.editor.setCon(j);
			System.out.println(this.editor.getEditor().getText());
			if (this.editor.getCon() >= 8)
				this.editor.getEditor().setFont(new Font("Consolas", Font.PLAIN, this.editor.getCon()));

			break;
		case Const.RESTAURAR_ZOOM_PREDETERMINADO:
			this.editor.getEditor().setFont(new Font("Consolas", Font.PLAIN, 14));
			this.editor.setCon(14);
			break;
		}
	}
}