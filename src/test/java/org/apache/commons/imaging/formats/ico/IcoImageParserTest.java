/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.imaging.formats.ico;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.test.TestResources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

class IcoImageParserTest {

    /**
     * For <a href="https://issues.apache.org/jira/browse/IMAGING-373">IMAGING-373</a>.
     * <p>There is a problem with loading bitmap stored in given ICO file, so the exception is originally thrown by BmpImageParser.</p>
     */
    @Test
    void testImageWithInvalidBmpHeaders() {
        final File ico = TestResources.resourceToFile("/IMAGING-373/OutOfMemory_epine.ico");
        final IcoImageParser parser = new IcoImageParser();
        assertThrows(ImagingException.class, () -> parser.getAllBufferedImages(ico));
    }

    // Helper method to set up reflection for the private method
    private Method getReadBitmapMethod() throws Exception {
        // Look through all methods in the class
        for (Method method : IcoImageParser.class.getDeclaredMethods()) {
            // Find the one with our exact name
            if (method.getName().equals("readBitmapIconData")) {
                method.setAccessible(true);
                return method;
            }
        }
        throw new NoSuchMethodException("Could not find readBitmapIconData method");
    }

    // Added test 1: Hits the 'size != 40' branch
    @Test
    public void testReadBitmapIconData_HitsSizeBranch() throws Exception {
        IcoImageParser parser = new IcoImageParser();
        byte[] badSizeData = new byte[40]; // Bytes 0-3 default to 0, which is not 40

        InvocationTargetException thrown = assertThrows(InvocationTargetException.class, () -> {
            getReadBitmapMethod().invoke(parser, badSizeData, null);
        });

        // Verify the exception message
        assertTrue(thrown.getCause().getMessage().contains("Wrong bitmap header size"));
    }

    // Added test 2: Hits the 'planes != 1' branch
    @Test
    public void testReadBitmapIconData_HitsPlanesBranch() throws Exception {
        IcoImageParser parser = new IcoImageParser();
        byte[] badPlanesData = new byte[40];
        badPlanesData[0] = 40; // Bypass the size check
        badPlanesData[12] = 2; // Set planes to 2 (not 1)

        InvocationTargetException thrown = assertThrows(InvocationTargetException.class, () -> {
            getReadBitmapMethod().invoke(parser, badPlanesData, null);
        });

        // Verify the exception message
        assertTrue(thrown.getCause().getMessage().contains("Planes can't be"));
    }
}
