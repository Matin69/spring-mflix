package com.mflix.app.common;

import com.mflix.app.comment.CommentConverter;
import com.mflix.app.movie.MovieConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfigurer {

    @Bean
    public ConverterRegistry converterRegistry() {
        ConverterRegistry converterRegistry = new ConverterRegistry();
        converterRegistry.addConverter(new CommentConverter());
        converterRegistry.addConverter(new MovieConverter());
        return converterRegistry;
    }
}
