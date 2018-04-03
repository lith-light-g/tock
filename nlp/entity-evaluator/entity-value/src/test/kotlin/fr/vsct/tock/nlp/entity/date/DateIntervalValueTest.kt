/*
 * Copyright (C) 2017 VSCT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.vsct.tock.nlp.entity.date

import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.test.assertEquals

/**
 *
 */
class DateIntervalValueTest {

    @Test
    fun duration_withDifferentTimeZone_shouldGiveTheRightValue() {
        val actual = ZonedDateTime.now()
        val duration = Duration.ofHours(2)
        val end = actual.plusHours(1).withZoneSameInstant(ZoneId.of("America/Phoenix"))
        assertEquals(
            duration,
            DateIntervalEntityValue(
                DateEntityValue(actual, DateEntityGrain.hour),
                DateEntityValue(end, DateEntityGrain.hour)
            ).duration()
        )
    }
}