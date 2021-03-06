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

package fr.vsct.tock.bot.engine.config

import com.github.salomonbrys.kodein.instance
import fr.vsct.tock.bot.admin.bot.StoryDefinitionConfigurationDAO
import fr.vsct.tock.bot.engine.Bot
import fr.vsct.tock.shared.Executor
import fr.vsct.tock.shared.error
import fr.vsct.tock.shared.injector
import fr.vsct.tock.shared.longProperty
import mu.KotlinLogging
import java.time.Duration
import java.time.Instant
import java.util.concurrent.CopyOnWriteArrayList

/**
 *
 */
internal object BotConfigurationSynchronizer {

    private val logger = KotlinLogging.logger {}

    private val refreshDelay = longProperty("tock_bot_configuration_refresh", 10000)
    private val executor: Executor  by injector.instance()
    private val storyDAO: StoryDefinitionConfigurationDAO by injector.instance()
    private val botsToMonitor: MutableList<Bot> = CopyOnWriteArrayList()

    @Volatile
    private var lastUpdate: Instant? = null

    init {
        logger.info { "start bot configuration synchronizer" }
        executor.setPeriodic(Duration.ofMillis(refreshDelay)) {
            logger.trace { "refresh bots configuration" }
            botsToMonitor.forEach {
                refresh(it)
            }
        }
    }

    fun monitor(bot: Bot) {
        botsToMonitor.add(bot)
        refresh(bot)
    }

    private fun refresh(bot: Bot) {
        try {
            val botId = bot.botDefinition.botId
            val last = storyDAO.getLastUpdateTimestamp(botId)
            if (last != null && (lastUpdate == null || last > lastUpdate)) {
                logger.info { "refresh configured stories for bot $botId" }
                bot.botDefinition.updateStories(
                    storyDAO.getStoryDefinitions(bot.botDefinition.botId)
                        .map { ConfiguredStoryDefinition(it) }
                )
            }
        } catch (e: Exception) {
            //log & ignore
            logger.error(e)
        }
    }
}