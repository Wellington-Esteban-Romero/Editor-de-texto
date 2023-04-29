package utils;

import java.io.File;

public final class Utils {
	
	private Utils() {}
	
	public static String getNombreArchivo(File file) {
		return file.getPath().toString().substring(file.getPath().toString().lastIndexOf("\\")+1);
	}
}
