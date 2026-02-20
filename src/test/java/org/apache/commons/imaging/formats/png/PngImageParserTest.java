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

package org.apache.commons.imaging.formats.png;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.common.AllocationRequestException;
import org.junit.jupiter.api.Test;

class PngImageParserTest extends AbstractPngTest {

    private static byte[] getPngImageBytes(final BufferedImage image, final PngImagingParameters params) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            new PngWriter().writeImage(image, os, params, null);
            return os.toByteArray();
        }
    }

    @Test
    void testGetImageSize() {
        final byte[] bytes = {
                // Header
                (byte) 0x89, 'P', 'N', 'G', '\r', '\n', 0x1A, '\n',
                // (Too large) Length
                (byte) 0b0111_1111, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF - 10,
                // Chunk type
                'I', 'H', 'D', 'R', };
        assertThrows(AllocationRequestException.class, () -> new PngImageParser().getImageSize(bytes));
    }

    @Test
    void testNoPalette() throws IOException {
        final BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        image.setRGB(1, 1, 0x00FFffFF);
        final PngImagingParameters params = new PngImagingParameters();

        final byte[] bytes = getPngImageBytes(image, params);
        final ImageInfo imageInfo = new PngImageParser().getImageInfo(bytes, null);
        assertFalse(imageInfo.usesPalette());
    }

    @Test
    void testPalette() throws IOException {
        final BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        image.setRGB(1, 1, 0x00FFffFF);
        final PngImagingParameters params = new PngImagingParameters();
        params.setForceIndexedColor(true);

        final byte[] bytes = getPngImageBytes(image, params);
        final ImageInfo imageInfo = new PngImageParser().getImageInfo(bytes, null);
        assertTrue(imageInfo.usesPalette());
    }

/**
 * Tests that getImageInfo throws ImagingException when the PNG file
 * contains no recognized chunks. The byte array has a valid PNG signature
 * followed immediately by an IEND chunk, so readChunks returns an empty
 * list and the "PNG: no chunks" error path is triggered.
 */
    @Test
    void testGetImageInfoNoChunks() {
        final byte[] pngWithNoChunks = {
            // PNG signature (8 bytes)
            (byte) 0x89, 'P', 'N', 'G', '\r', '\n', 0x1A, '\n',
            0x00, 0x00, 0x00, 0x00,
            // IEND chunk is always the last chunk of PNG file, The chunk's data field is empty.
            'I', 'E', 'N', 'D',
            0x00, 0x00, 0x00, 0x00
        };

        assertThrows(ImagingException.class, () -> new PngImageParser().getImageInfo(pngWithNoChunks, null));
    }

/**
 * Tests that getImageInfo throws ImagingException when the PNG file
 * contains more than one IHDR chunk. The byte array has a valid PNG
 * signature followed by two complete IHDR chunks and an IEND chunk,
 * so filterChunks returns a list of size 2 and the "PNG contains more
 * than one Header" error path is triggered.
 */
    @Test
    void testGetImageInfoMoreThanOneHeader() {
        final byte[] pngWithMoreThanOneHeader = {
            // PNG signature (8 bytes)
            (byte) 0x89, 'P', 'N', 'G', '\r', '\n', 0x1A, '\n',

            // First IHDR chunk
            0x00, 0x00, 0x00, 0x0D,
            'I', 'H', 'D', 'R',
            0x00, 0x00, 0x00, 0x01,  // width 
            0x00, 0x00, 0x00, 0x01,  // height 
            0x08,                     // bit depth
            0x02,                     // color type 
            0x00,                     // compression 
            0x00,                     // filter 
            0x00,                     // interlace
            0x00, 0x00, 0x00, 0x00, // CRC

            // Second IHDR chunk
            0x00, 0x00, 0x00, 0x0D,
            'I', 'H', 'D', 'R',
            0x00, 0x00, 0x00, 0x01,
            0x00, 0x00, 0x00, 0x01,
            0x08, 0x02, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00,

            // IEND chunk
            0x00, 0x00, 0x00, 0x00,
            'I', 'E', 'N', 'D',
            0x00, 0x00, 0x00, 0x00  
        };
        assertThrows(ImagingException.class, () -> new PngImageParser().getImageInfo(pngWithMoreThanOneHeader, null));
    }

}
