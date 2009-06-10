package images;

public interface ImagesAdapter {
	
	public byte[] resize(byte[] oldImageData, int maxwidth, int maxheight);

}
