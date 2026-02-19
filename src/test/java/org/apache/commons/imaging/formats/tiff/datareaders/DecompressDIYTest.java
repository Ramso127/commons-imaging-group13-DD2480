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
package org.apache.commons.imaging.formats.tiff.datareaders;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Rectangle;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.imaging.DIYCoverageTracker;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.common.ImageBuilder;
import org.apache.commons.imaging.formats.tiff.AbstractTiffRasterData;
import org.apache.commons.imaging.formats.tiff.TiffDirectory;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffPlanarConfiguration;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.fieldtypes.AbstractFieldType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class DecompressDIYTest {

    /*
     * Subclass of AbstractImageDataReader so we can test decompress().
     * AbstractImageDataReader is abstract so we cannot instantiate it directly.
     * readImageData() and readRasterData() are required stubs that we never call.
     */
    static class TestableReader extends AbstractImageDataReader {

        TestableReader(final TiffDirectory directory) {
            super(directory, null, new int[]{8}, 1, 1, 1, 1, 1, TiffPlanarConfiguration.CHUNKY);
        }

        @Override
        public ImageBuilder readImageData(final Rectangle r, final boolean a, final boolean b) {
            return null;
        }

        @Override
        public AbstractTiffRasterData readRasterData(final Rectangle r) {
            return null;
        }
    }

    /*
     * Creates a TiffDirectory with no fields.
     * decompress() will use default values for all metadata lookups.
     */
    private static TiffDirectory emptyDir() {
        return new TiffDirectory(TiffDirectoryConstants.DIRECTORY_TYPE_ROOT,
                new ArrayList<>(), 0, 0, ByteOrder.BIG_ENDIAN);
    }

    @AfterAll
    public static void tearDown() {
        DIYCoverageTracker.printReport();
    }

    /*
     * Branch 24
     * Contract: decompress must reject unknown compression types by throwing ImagingException.
     * Input: An empty TiffDirectory and compression ID 9999, which does not match any known TIFF compression constant.
     * Output: ImagingException is thrown from the default switch case.
     */
    @Test
    void testUnknownCompression() {
        final TestableReader reader = new TestableReader(emptyDir());
        assertThrows(ImagingException.class, () -> reader.decompress(new byte[]{0}, 9999, 1, 1, 1));
    }

    /*
     * Branch 6
     * Contract: decompress must reject invalid fill order values by throwing ImagingException.
     * Input: A TiffDirectory with a FILL_ORDER field set to 99 (valid values are NORMAL=1 and REVERSED=2).
     * Output: ImagingException is thrown from the else clause of the fill order check.
     */
    @Test
    void testInvalidFillOrder() {
        final byte[] valueBytes = {0, 99}; 

        final List<TiffField> fields = new ArrayList<>();
        fields.add(new TiffField(TiffTagConstants.TIFF_TAG_FILL_ORDER.tag,
                0, AbstractFieldType.SHORT, 1, 0, valueBytes, ByteOrder.BIG_ENDIAN, 0));

        final TiffDirectory dir = new TiffDirectory(0, fields, 0, 0, ByteOrder.BIG_ENDIAN);
        final TestableReader reader = new TestableReader(dir);

        assertThrows(ImagingException.class,
                () -> reader.decompress(new byte[]{0}, TiffConstants.COMPRESSION_UNCOMPRESSED, 1, 1, 1));
    }
}
