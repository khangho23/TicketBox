package com.example.demo.config;

import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.AnnotateWith;

@AnnotateWith(annotations = { @Annotation(target = AnnotationTarget.CONSTRUCTOR, type = javax.inject.Inject.class),
		@Annotation(target = AnnotationTarget.CONSTRUCTOR_PARAMETER, type = javax.inject.Named.class, elements = "\"config\"") })
public @interface InjectConfig {
}
