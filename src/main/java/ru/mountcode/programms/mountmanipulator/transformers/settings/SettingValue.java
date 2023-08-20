package ru.mountcode.programms.mountmanipulator.transformers.settings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingValue {

  String name();

  String description() default "";

  String section();
}
