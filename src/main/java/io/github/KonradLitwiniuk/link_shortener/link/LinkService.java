package io.github.KonradLitwiniuk.link_shortener.link;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LinkService {
    private final LinkRepository linkRepository;
    private final ClickRepository clickRepository;
    private final RandomCodeGenerator randomCodeGenerator;
    private final String baseUrl;
    public LinkService(
            LinkRepository linkRepository, RandomCodeGenerator randomCodeGenerator, ClickRepository clickRepository,
            @Value("${app.base-url}") String baseUrl){
        this.linkRepository = linkRepository;
        this.baseUrl = baseUrl;
        this.randomCodeGenerator = randomCodeGenerator;
        this.clickRepository = clickRepository;
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
                // Code already exists in the database, ignore and generate again in the next iteration
            }
        }
        throw new LinkCollisionException();    }
    private void saveClick(Link link){
        Click click = new Click();
        click.setLink(link);
        click.setClickedAt(Instant.now());
        clickRepository.save(click);
    }
    public String getOriginalUrl(String code){
        Link foundLink = linkRepository.findByCode(code)
                .orElseThrow(() -> new LinkNotFoundException(code));
        saveClick(foundLink);
        return foundLink.getUrl();
    }


}
