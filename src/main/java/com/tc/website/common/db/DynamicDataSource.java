package com.tc.website.common.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 获取数据源（依赖于spring）
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected Object determineCurrentLookupKey() {
		String dataSource = DataSourceHolder.getDataSource();
		// logger.info("当前数据源 determineCurrentLookupKey  dataSource = " + dataSource);
		return dataSource;
	}
}