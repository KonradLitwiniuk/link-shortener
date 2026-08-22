package io.github.KonradLitwiniuk.link_shortener.link;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final RandomCodeGenerator randomCodeGenerator;
    private final String baseUrl;
    public LinkService(
            LinkRepository linkRepository, RandomCodeGenerator randomCodeGenerator,
            @Value("${app.base-url}") String baseUrl){
        this.linkRepository = linkRepository;
        this.baseUrl = baseUrl;
        this.randomCodeGenerator = randomCodeGenerator;
    }
    public CreateLinkResponse createShortLink(String givenUrl){
        String code = "";
        for(int i = 0; i < 8; i++) {
            try {
                code = randomCodeGenerator.generateCode();
                Link newLink = new Link(givenUrl, code);
                Link saved = linkRepository.save(newLink);
                String fullShortenUrl = baseUrl + code;
                return new CreateLinkResponse(fullShortenUrl, givenUrl);
            } catch (DataIntegrityViolationException e) {
                System.out.println("Kolizja! Kod " + code + " już istnieje. Próbuję ponownie...");
                // Code already exists in the database, ignore and generate again in the next iteration
            }
        }
        throw new RuntimeException("Nie udało się wygenerować unikalnego kodu po 8 próbach");

    }
    public String getOriginalUrl(String code){
        return linkRepository.findByCode(code)
                .orElseThrow(() -> new LinkNotFoundException(code))
                .getUrl();
    }
}
