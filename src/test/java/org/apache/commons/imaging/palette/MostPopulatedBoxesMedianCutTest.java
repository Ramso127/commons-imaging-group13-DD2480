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

package org.apache.commons.imaging.palette;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class MostPopulatedBoxesMedianCutTest {

    /**
     * This test triggers branch 3 when the colorGroups list is empty or if none of the groups have maxDiff > 0,
     * In this test it is hit by passing an empty arraylist to the method
     */
    @Test
    public void testBranch3() throws Exception {
        MostPopulatedBoxesMedianCut cut_branch3 = new MostPopulatedBoxesMedianCut();
        List<ColorGroup> groups_branch3 = new ArrayList<>();
        boolean result_branch3 = cut_branch3.performNextMedianCut(groups_branch3, false);

        assertEquals(0, groups_branch3.size());
        assertFalse(result_branch3);
    }

    /**
     * This test triggers branch 14 when performNextMedianCut looks for how good the split of color channels is.
     * Both colors have same alpha value which will result in 0 difference. Green has the same score but is checked after, which makes the bestColorComponent choose alpha.
     */
    @Test
    public void testBranch14() throws Exception{
        MostPopulatedBoxesMedianCut cut_branch14 = new MostPopulatedBoxesMedianCut();

        List<ColorCount> colorList = new ArrayList<>();
        ColorCount c1 = new ColorCount(0xFF000000);
        c1.count = 10;
        colorList.add(c1);

        ColorCount c2 = new ColorCount(0xFF00FF00);
        c2.count = 10;
        colorList.add(c2);

        ColorGroup newgroup = new ColorGroup(colorList, false);

        System.out.println("Group maxDiff: " + newgroup.maxDiff);
        System.out.println("Group totalPoints: " + newgroup.totalPoints);

        List<ColorGroup> groups_branch14 = new ArrayList<>();
        groups_branch14.add(newgroup);

        boolean result_branch14 = cut_branch14.performNextMedianCut(groups_branch14, false);

        assertTrue(result_branch14);
        assertEquals(2, groups_branch14.size());

    } 
    
}
