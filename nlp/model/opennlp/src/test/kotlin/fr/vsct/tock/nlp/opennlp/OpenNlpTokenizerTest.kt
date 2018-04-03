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

package fr.vsct.tock.nlp.opennlp

import fr.vsct.tock.nlp.core.NlpEngineType
import fr.vsct.tock.nlp.model.TokenizerContext
import fr.vsct.tock.nlp.model.service.engine.TokenizerModelHolder
import org.junit.jupiter.api.Test
import java.util.Locale
import kotlin.test.assertEquals

/**
 *
 */
internal class OpenNlpTokenizerTest {

    val tokenizer = OpenNlpTokenizer(TokenizerModelHolder(Locale.FRENCH))
    val context = TokenizerContext(Locale.FRENCH, NlpEngineType.Companion.opennlp)

    @Test
    fun tokenize_wordsWithDash_areSplitted() {
        val tokens = tokenizer.tokenize(context, "Paris-Lyon du 25 au 28 Février")
        assertEquals("Paris", tokens[0])
        assertEquals("Lyon", tokens[2])
        assertEquals(8, tokens.size)
    }

    @Test
    fun tokenize_wordsWithSimpleQuote_areSplitted() {
        val tokens = tokenizer.tokenize(context, "Cap d'Agde")
        assertEquals("Cap", tokens[0])
        assertEquals("Agde", tokens[3])
        assertEquals(4, tokens.size)
    }
}