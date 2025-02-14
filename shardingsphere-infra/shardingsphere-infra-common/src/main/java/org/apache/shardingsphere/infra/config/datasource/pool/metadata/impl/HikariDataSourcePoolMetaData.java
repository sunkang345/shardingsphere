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

package org.apache.shardingsphere.infra.config.datasource.pool.metadata.impl;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.apache.shardingsphere.infra.config.datasource.pool.metadata.DataSourcePoolMetaData;

import java.util.HashMap;
import java.util.Map;

/**
 * Hikari data source pool meta data.
 */
@Getter
public final class HikariDataSourcePoolMetaData implements DataSourcePoolMetaData {
    
    private final Map<String, Object> defaultProperties = new HashMap<>(6, 1);
    
    private final Map<String, Object> invalidProperties = new HashMap<>(2, 1);
    
    private final Map<String, String> propertySynonyms = new HashMap<>(2, 1);
    
    public HikariDataSourcePoolMetaData() {
        buildDefaultProperties();
        buildInvalidProperties();
        buildPropertySynonyms();
    }
    
    private void buildDefaultProperties() {
        defaultProperties.put("connectionTimeout", 30 * 1000L);
        defaultProperties.put("idleTimeout", 60 * 1000L);
        defaultProperties.put("maxLifetime", 30 * 70 * 1000L);
        defaultProperties.put("maximumPoolSize", 50);
        defaultProperties.put("minimumIdle", 1);
        defaultProperties.put("readOnly", false);
    }
    
    private void buildInvalidProperties() {
        invalidProperties.put("minimumIdle", -1);
        invalidProperties.put("maximumPoolSize", -1);
    }
    
    private void buildPropertySynonyms() {
        propertySynonyms.put("connectionTimeoutMilliseconds", "connectionTimeout");
        propertySynonyms.put("idleTimeoutMilliseconds", "idleTimeout");
        propertySynonyms.put("maxLifetimeMilliseconds", "maxLifetime");
        propertySynonyms.put("maxPoolSize", "maximumPoolSize");
        propertySynonyms.put("minPoolSize", "minimumIdle");
    }
    
    @Override
    public String getJdbcUrlFieldName() {
        return "jdbcUrl";
    }
    
    @Override
    public String getJdbcUrlPropertiesFieldName() {
        return "dataSourceProperties";
    }
    
    @Override
    public String getType() {
        return HikariDataSource.class.getCanonicalName();
    }
}
