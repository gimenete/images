package images;

public class Images {
	
	private static ImagesAdapter adapter;
	
	public static ImagesAdapter getAdapter() {
		if(adapter == null) {
			try {
				adapter = new GaeImagesAdapter();
			} catch(NoClassDefFoundError e) {
				adapter = new AwtImagesAdapter();
			}
		}
		return adapter;
	}

}
