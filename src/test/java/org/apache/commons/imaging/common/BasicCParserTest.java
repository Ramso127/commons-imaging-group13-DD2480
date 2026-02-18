/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.imaging.common;

import org.apache.commons.imaging.DIYCoverageTracker;
import org.apache.commons.imaging.ImagingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BasicCParserTest {

    private void parseString(String content) throws IOException, ImagingException {
        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
        BasicCParser parser = new BasicCParser(is);
        while (parser.nextToken() != null) {
            // just loop
        }
    }

    @Test
    public void testHitBranch4() throws IOException, ImagingException {
        // I am targeting branch_4 here which handles the backslash character ('\').
        // Backslashes are used for escaping in Java strings as well.
        // To send a single literal backslash to the parser, I have to write four of them:
        // "test \\\\ escape" in Java code it  becomes "test \\ escape" in memory.
        parseString("\"test \\\\ escape\"");
    }

    @Test
    public void testHitBranch15() throws IOException, ImagingException {
        // I am targeting branch_15, which handles a specific edge case.
        // Usually, an identifier (like a variable name) ends with a space or a symbol.
        // But here I am testing what happens if the file ends immediately after the word my_var.
        // The parser needs to realize it has finished reading a valid word and return it,
        // rather than crashing or returning null.
        parseString("my_var"); 
    }

    @AfterAll
    public static void tearDown() {
        DIYCoverageTracker.printReport();
    }
}