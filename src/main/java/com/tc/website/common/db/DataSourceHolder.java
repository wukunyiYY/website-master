package com.tc.website.common.db;

import com.tc.website.common.db.annotation.DataSource;

/**
 * 数据源操作
 */
public class DataSourceHolder {

    //线程本地环境
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>(){
        @Override
        protected String initialValue(){
            return DataSource.dataSource;
        }
    };
    //设置数据源
    public static void setDataSource(String customerType) {
        dataSources.set(customerType);
    }
    //获取数据源
    public static String getDataSource() {
        return (String) dataSources.get();
    }
    //清除数据源
    public static void resetDataSource() {
        setDataSource(DataSource.dataSource);
    }
}
