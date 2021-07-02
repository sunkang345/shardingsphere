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

package org.apache.shardingsphere.example.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.shardingsphere.sql.parser.api.SQLParserEngine;
import org.apache.shardingsphere.sql.parser.api.SQLVisitorEngine;

import java.util.Properties;

public class SQLParserFormatExample {

    public static void main(String[] args) {
        String sql = "select age as b, name n from table1 join table2 where id=1 and name='lu';";
        Properties props = new Properties();
        props.setProperty("parameterized", "false");
        SQLParserEngine parserEngine = new SQLParserEngine("MySQL");
        ParseTree tree = parserEngine.parse(sql, false);
        SQLVisitorEngine visitorEngine = new SQLVisitorEngine("MySQL", "FORMAT", props);
        String result = visitorEngine.visit(tree);
        System.out.println(result);
    }
}