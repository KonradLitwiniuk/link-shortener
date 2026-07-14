package io.github.KonradLitwiniuk.link_shortener.link;

public class LinkNotFoundException extends RuntimeException{
    public LinkNotFoundException(String code){
        super("Link with code " + code + " not found");
    }
}
