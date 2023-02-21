package com.mflix.annotation;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("com.mflix.annotation.ResponseConverter")
@AutoService(Processor.class)
public class ConverterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedClasses = roundEnv.getElementsAnnotatedWith(annotation);
            List<String> annotatedFullClassNames = annotatedClasses.stream()
                    .map(element -> element.asType().toString())
                    .collect(Collectors.toList());
            List<String> annotatedClassNames = annotatedClasses
                    .stream()
                    .map(element -> element.getSimpleName().toString())
                    .collect(Collectors.toList());
            try {
                JavaFileObject configurationFile = processingEnv.getFiler().createSourceFile("ConverterConfiguration");
                try (PrintWriter out = new PrintWriter(configurationFile.openWriter())) {
                    out.println("package com.mflix.core.common;");
                    out.println();
                    for (String annotatedFullClassName : annotatedFullClassNames) {
                        out.println(String.format("import %s;", annotatedFullClassName));
                    }
                    out.println("import org.springframework.context.annotation.Bean;");
                    out.println("import org.springframework.context.annotation.Configuration;");
                    out.println();
                    out.println("@Configuration");
                    out.println("public class ConverterConfiguration {");
                    out.println();
                    out.println("@Bean");
                    out.println("public ConverterRegistry converterRegistry() {");
                    out.println("\tConverterRegistry converterRegistry = new ConverterRegistry();");
                    for (String annotatedClassName : annotatedClassNames) {
                        out.println(String.format("\tconverterRegistry.addConverter(new %s());", annotatedClassName));
                    }
                    out.println("\treturn converterRegistry;");
                    out.println("}");
                    out.println("}");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
