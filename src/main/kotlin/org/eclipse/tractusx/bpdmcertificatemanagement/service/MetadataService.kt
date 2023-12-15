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

package org.eclipse.tractusx.bpdmcertificatemanagement.service

import mu.KotlinLogging
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.CertificateTypeDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.PageDto
import org.eclipse.tractusx.bpdmcertificatemanagement.entity.CertificateTypeDB
import org.eclipse.tractusx.bpdmcertificatemanagement.exception.CertificateAlreadyExists
import org.eclipse.tractusx.bpdmcertificatemanagement.repository.CertificateTypeRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.eclipse.tractusx.bpdmcertificatemanagement.service.toDto

@Service
class MetadataService(
    val certificateTypeRepository: CertificateTypeRepository,
) {

    private val logger = KotlinLogging.logger { }

    fun createCertificateType(certificateTypeDto: CertificateTypeDto): CertificateTypeDto {
        if (certificateTypeRepository.findByCertificateTypeAndCertificateVersion(
                certificateTypeDto.certificateType,
                certificateTypeDto.certificateVersion
            ) != null
        )
            throw CertificateAlreadyExists(CertificateTypeDB::class.simpleName!!, certificateTypeDto.certificateType)

        logger.info { "Create new Certificate Type with name ${certificateTypeDto.certificateType} and version ${certificateTypeDto.certificateVersion}" }

        val entity = CertificateTypeDB(
            certificateType = certificateTypeDto.certificateType,
            certificateVersion = certificateTypeDto.certificateVersion
        )

        return certificateTypeRepository.save(entity).toDto()
    }

    fun getCertificateTypes(pageRequest: Pageable): PageDto<CertificateTypeDto> {
        val page = certificateTypeRepository.findAll(pageRequest)
        return page.toDto(page.content.map { it.toDto() })
    }

}
