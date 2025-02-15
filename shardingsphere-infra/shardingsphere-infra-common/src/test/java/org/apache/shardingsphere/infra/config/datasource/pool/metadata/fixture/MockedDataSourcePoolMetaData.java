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

package org.apache.shardingsphere.infra.config.datasource.pool.metadata.fixture;

import org.apache.shardingsphere.infra.config.datasource.pool.metadata.DataSourcePoolMetaData;
import org.apache.shardingsphere.test.mock.MockedDataSource;

import java.util.HashMap;
import java.util.Map;

public final class MockedDataSourcePoolMetaData implements DataSourcePoolMetaData {
    
    @Override
    public Map<String, Object> getDefaultProperties() {
        Map<String, Object> result = new HashMap<>(1, 1);
        result.put("maxPoolSize", 100);
        return result;
    }
    
    @Override
    public Map<String, Object> getInvalidProperties() {
        Map<String, Object> result = new HashMap<>(2, 1);
        result.put("maxPoolSize", -1);
        result.put("minPoolSize", -1);
        return result;
    }
    
    @Override
    public Map<String, String> getPropertySynonyms() {
        Map<String, String> result = new HashMap<>(2, 1);
        result.put("maxPoolSize", "maxPoolSize");
        result.put("minPoolSize", "minPoolSize");
        return result;
    }
    
    @Override
    public String getJdbcUrlFieldName() {
        return "url";
    }
    
    @Override
    public String getJdbcUrlPropertiesFieldName() {
        return "jdbcUrlProperties";
    }
    
    @Override
    public String getType() {
        return MockedDataSource.class.getName();
    }
}
