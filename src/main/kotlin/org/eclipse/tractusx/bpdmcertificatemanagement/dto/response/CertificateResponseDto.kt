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

package org.eclipse.tractusx.bpdmcertificatemanagement.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.CertificateTypeDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.EnclosedSiteDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.TrustValidatorDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.openapidescription.CommonDescription
import java.time.Instant
import java.time.ZonedDateTime

data class CertificateResponseDto(
    val businessPartnerNumber: String? = null,
    val type: CertificateTypeDto,
    val registrationNumber: String? = null,
    val areaOfApplication: String? = null,
    val remark: String? = null,
    val enclosedSites: Collection<EnclosedSiteDto>? = emptyList(),
    val validFrom: ZonedDateTime? = null,
    val validUntil: ZonedDateTime? = null,
    val issuer: String? = null,
    val trustLevel: String? = null,
    val validator: TrustValidatorDto? = null,
    val uploader: String? = null,

    @get:Schema(description = CommonDescription.createdAt)
    val createdAt: Instant,

    @get:Schema(description = CommonDescription.updatedAt)
    val updatedAt: Instant
)