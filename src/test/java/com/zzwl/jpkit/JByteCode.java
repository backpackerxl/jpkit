package com.zzwl.jpkit;

import com.zzwl.jpkit.vo.StringVo;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

public class JByteCode extends AbstractProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> typeSet = new HashSet<>();
        typeSet.add(StringVo.class.getCanonicalName());
        return typeSet;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        System.out.println("HellO World");
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "AbstractProcessor init");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "AbstractProcessor process");
        return true;
    }
}
