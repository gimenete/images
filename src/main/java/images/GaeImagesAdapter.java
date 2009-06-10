package images;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class GaeImagesAdapter implements ImagesAdapter {
	
	private ImagesService imagesService;
	
	public GaeImagesAdapter() {
		imagesService = ImagesServiceFactory.getImagesService();
	}
	
	public byte[] resize(byte[] oldImageData, int maxwidth, int maxheight) {
		Image oldImage = ImagesServiceFactory.makeImage(oldImageData);
		Transform resize = ImagesServiceFactory.makeResize(maxwidth, maxheight);
		
		Image newImage = imagesService.applyTransform(resize, oldImage);

		return newImage.getImageData();
	}

}
