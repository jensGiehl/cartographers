package de.agiehl.games.cartographers.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SvgProviderService {

    static final int DEFAULT_WIDTH = 42;
    static final int DEFAULT_HEIGHT = 42;

    @Cacheable("image")
	public String getImageAsSvgString(String imageName) {
        return createSvgHeader() + createSvgImage(imageName) + "</svg>";
    }
    
    private String createSvgImage(String imageName) {
        return String.format("<image x=\"0\" y=\"0\" width=\"%d\" height=\"%d\" xlink:href=\"%s.svg\" />", DEFAULT_WIDTH, DEFAULT_HEIGHT, imageName);
    }

    private String createSvgHeader() {
        return String.format("<svg width=\"100%%\" height=\"100%%\" viewBox=\"0 0 %d %d\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">", DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
}