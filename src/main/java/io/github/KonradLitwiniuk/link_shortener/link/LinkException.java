package io.github.KonradLitwiniuk.link_shortener.link;

public class LinkException extends RuntimeException{
    public LinkException(String message){
        super(message);
    }
    public LinkException(String message, Throwable cause){
        super(message,cause);
    }
}

