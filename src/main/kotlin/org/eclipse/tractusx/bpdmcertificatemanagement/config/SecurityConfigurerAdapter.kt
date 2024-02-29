/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.tractusx.bpdmcertificatemanagement.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class SecurityConfigurerAdapter(
    val securityConfigProperties: SecurityConfigProperties
) {

    fun configure(http: HttpSecurity) {
        http.cors {}
        http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        http.authorizeHttpRequests {
            it.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS, "/api/**")).permitAll()
            it.requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll() // forwards to swagger
            it.requestMatchers(AntPathRequestMatcher.antMatcher("/docs/api-docs/**")).permitAll()
            it.requestMatchers(AntPathRequestMatcher.antMatcher("/ui/swagger-ui/**")).permitAll()
            it.requestMatchers(AntPathRequestMatcher.antMatcher("/actuator/health/**")).permitAll()
            it.requestMatchers(AntPathRequestMatcher.antMatcher("/error")).permitAll()
            it.requestMatchers(AntPathRequestMatcher.antMatcher("/api/**")).authenticated()
        }
        http.oauth2ResourceServer {
            it.jwt { jwt ->
                jwt.jwtAuthenticationConverter(CustomJwtAuthenticationConverter(securityConfigProperties.clientId))
            }
        }
    }
}