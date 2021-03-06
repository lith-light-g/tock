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

package fr.vsct.tock.bot.connector

/**
 * To provide a new [Connector] from a [ConnectorConfiguration].
 */
interface ConnectorProvider {

    /**
     * The connector type provided
     */
    val connectorType: ConnectorType

    /**
     * Provides a new [Connector] instance from the specified [ConnectorConfiguration].
     */
    fun connector(connectorConfiguration: ConnectorConfiguration): Connector

}