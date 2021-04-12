package com.vikas.desktopapplications.bulkxmlgenerator;

public class ObjectGenerator implements Runnable {
	
	private ObjectXMLGeneratorService oxgService;
	
	public ObjectGenerator(ObjectXMLGeneratorService oxgService) {
		this.oxgService = oxgService;
	}

	@Override
	public void run() {
		try {
			this.oxgService.generateObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
