package io.github.KonradLitwiniuk.link_shortener;

import io.github.KonradLitwiniuk.link_shortener.link.Link;
import io.github.KonradLitwiniuk.link_shortener.link.LinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LinkShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkShortenerApplication.class, args);
	}


}
