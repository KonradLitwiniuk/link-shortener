package io.github.KonradLitwiniuk.link_shortener.link;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.UUID;
@Component

public class RandomCodeGenerator {
    private final String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final SecureRandom rand = new SecureRandom();
    public String generateCode(){
                StringBuilder sb = new StringBuilder();
                for(int j = 0; j < 6; j++){
                    int randomIndex = rand.nextInt(62);
                    sb.append(alphabet.charAt(randomIndex));
                }
                return sb.toString();

    }
}
