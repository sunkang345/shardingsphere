/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.config.datasource.pool.creator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.shardingsphere.infra.config.datasource.pool.metadata.DataSourcePoolMetaData;
import org.apache.shardingsphere.infra.config.datasource.pool.metadata.DataSourcePoolMetaDataFactory;
import org.apache.shardingsphere.infra.config.datasource.props.DataSourceProperties;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Data source pool creator.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataSourcePoolCreator {
    
    /**
     * Create data sources.
     *
     * @param dataSourcePropsMap data source properties map
     * @return created data sources
     */
    public static Map<String, DataSource> create(final Map<String, DataSourceProperties> dataSourcePropsMap) {
        return dataSourcePropsMap.entrySet().stream().collect(Collectors.toMap(Entry::getKey, entry -> create(entry.getValue()), (oldValue, currentValue) -> oldValue, LinkedHashMap::new));
    }
    
    /**
     * Create data source.
     * 
     * @param dataSourceProps data source properties
     * @return created data source
     */
    public static DataSource create(final DataSourceProperties dataSourceProps) {
        DataSource result = createDataSource(dataSourceProps.getDataSourceClassName());
        DataSourcePoolMetaData poolMetaData = DataSourcePoolMetaDataFactory.newInstance(dataSourceProps.getDataSourceClassName());
        DataSourceReflection dataSourceReflection = new DataSourceReflection(result);
        setDefaultFields(dataSourceReflection, poolMetaData);
        setConfiguredFields(dataSourceProps, dataSourceReflection, poolMetaData);
        dataSourceReflection.addDefaultDataSourceProperties(poolMetaData.getJdbcUrlPropertiesFieldName(), poolMetaData.getJdbcUrlFieldName());
        return result;
    }
    
    @SneakyThrows(ReflectiveOperationException.class)
    private static DataSource createDataSource(final String dataSourceClassName) {
        return (DataSource) Class.forName(dataSourceClassName).getConstructor().newInstance();
    }
    
    private static void setDefaultFields(final DataSourceReflection dataSourceReflection, final DataSourcePoolMetaData poolMetaData) {
        for (Entry<String, Object> entry : poolMetaData.getDefaultProperties().entrySet()) {
            dataSourceReflection.setField(entry.getKey(), entry.getValue());
        }
    }
    
    private static void setConfiguredFields(final DataSourceProperties dataSourceProps, final DataSourceReflection dataSourceReflection, final DataSourcePoolMetaData poolMetaData) {
        for (Entry<String, Object> entry : dataSourceProps.getAllProperties().entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            if (isValidProperty(fieldName, fieldValue, poolMetaData)) {
                dataSourceReflection.setField(fieldName, fieldValue);
            }
        }
    }
    
    private static boolean isValidProperty(final String key, final Object value, final DataSourcePoolMetaData poolMetaData) {
        return !poolMetaData.getInvalidProperties().containsKey(key) || null == value || !value.equals(poolMetaData.getInvalidProperties().get(key));
    }
}
