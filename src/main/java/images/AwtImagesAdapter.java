package images;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class AwtImagesAdapter implements ImagesAdapter {

	public byte[] resize(byte[] oldImageData, int maxwidth, int maxheight) {
		try {
			final String mimeType = "image/jpeg";
			File to = File.createTempFile("resized", "");
			BufferedImage source = ImageIO.read(new ByteArrayInputStream(oldImageData));
			int width = source.getWidth();
			int height = source.getHeight();
			double ratio = (double) width / height;

			int dwidth = (int) (maxheight * ratio);
			int dheight = maxheight;
			
			if(dwidth > maxwidth) {
				dwidth = maxwidth;
				dheight = (int) (maxwidth / ratio);
			}
			
			if(dwidth <= 0) dwidth = maxwidth;
			
			int paddingx = (maxwidth - dwidth) / 2;
			int paddingy = (maxheight - dheight) / 2;

			// out
			BufferedImage dest = new BufferedImage(maxwidth, maxheight, BufferedImage.TYPE_INT_RGB);
			Image srcSized = source.getScaledInstance(dwidth, dheight, Image.SCALE_SMOOTH);
			
			Graphics g = dest.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, maxwidth, maxheight);
			g.drawImage(srcSized, paddingx, paddingy, null);
			
			ImageWriter writer = ImageIO.getImageWritersByMIMEType(mimeType).next();
			ImageWriteParam params = writer.getDefaultWriteParam();
			writer.setOutput(new FileImageOutputStream(to));
			IIOImage image = new IIOImage(dest, null, null);
			writer.write(null, image, params);
			
			return toArray(to);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private byte[] toArray(File to) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int read;
		byte[] buff = new byte[102400];
		
		FileInputStream fis = new FileInputStream(to);
		do {
			read = fis.read(buff);
			if(read == -1) break;
			baos.write(buff, 0, read);
		} while(true);
		
		return baos.toByteArray();
	}

}
