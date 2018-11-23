package com.tc.website.common.db.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    String value() default DataSource.dataSource;

    /**
     * 数据源1
     */
    static String dataSource = "dataSource";

    /**
     * 数据源2
     */
    static String dataSource2 = "dataSource2";
}
