package org.xixi.utils;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageUtils {

	public static void cut(String src, String dest, String formatType) throws IOException {
		File srcImageFile = new File(src);
		File output = new File(dest);
		InputStream in = new FileInputStream(srcImageFile);
		byte[] buffer = new byte[102400];
		in.read(buffer);
		OutputStream out = new FileOutputStream(output);
		cut(buffer, out, formatType);
		in.close();
	}

	public static void cut(byte[] buffer, OutputStream out) throws IOException {
		cut(buffer, out, "png");
	}

	public static void cut(byte[] buffer, OutputStream out, String formatType) throws IOException {
		InputStream in = null;
		ImageInputStream imageIn = null;
		try {
			in = new ByteArrayInputStream(buffer);
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(formatType);
			ImageReader reader = it.next();
			imageIn = ImageIO.createImageInputStream(in);
			reader.setInput(imageIn, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(0, 0, reader.getWidth(0), reader.getHeight(0) - 10);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, formatType, out);
		} finally {
			if (imageIn != null)
				imageIn.close();
			if (in != null)
				in.close();
		}
	}
}
