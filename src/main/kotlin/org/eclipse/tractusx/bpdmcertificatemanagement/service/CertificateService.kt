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
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.CertificateDocumentRequestDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateDocumentResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.PageDto
import org.eclipse.tractusx.bpdmcertificatemanagement.entity.CertificateTypeDB
import org.eclipse.tractusx.bpdmcertificatemanagement.exception.CertificateNotExists
import org.eclipse.tractusx.bpdmcertificatemanagement.exception.CertificateTypeNotExists
import org.eclipse.tractusx.bpdmcertificatemanagement.exception.InvalidBpnFormatException
import org.eclipse.tractusx.bpdmcertificatemanagement.repository.CertificateRepository
import org.eclipse.tractusx.bpdmcertificatemanagement.repository.CertificateTypeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class CertificateService(
    val certificateRepository: CertificateRepository,
    val certificateTypeRepository: CertificateTypeRepository,
    val certificateMapping: CertificateMapping
) {

    private val logger = KotlinLogging.logger { }

    fun createCertificate(certificateDocumentRequestDto: CertificateDocumentRequestDto): CertificateDocumentResponseDto? {
        logger.debug { "Executing createCertificate() with parameters $certificateDocumentRequestDto" }

        val certificateType =
            certificateTypeRepository.findByCertificateTypeAndCertificateVersion(
                certificateDocumentRequestDto.type.certificateType,
                certificateDocumentRequestDto.type.certificateVersion
            )
                ?: throw CertificateTypeNotExists(
                    CertificateTypeDB::class.simpleName!!,
                    certificateDocumentRequestDto.type.certificateType
                )

        val entity = certificateMapping.toCertificateDB(certificateDocumentRequestDto, certificateType)

        val result = certificateRepository.save(entity)
        return certificateMapping.toCertificateDocumentResponseDto(result)

    }

    fun getCertificatesByBpn(bpn: String, pageRequest: PageRequest): PageDto<CertificateResponseDto> {
        if (bpn.isBlank())
            throw IllegalArgumentException("Provided business partner number is null or empty")

        return when {
            bpn.startsWith("BPNL") -> {
                val certificates = certificateRepository.findByBusinessPartnerNumber(bpn, pageRequest)
                if (certificates.totalElements == 0L) {
                    throw CertificateNotExists("Legal Entity", bpn)
                }
                certificates.toPageDto(certificateMapping::toCertificateResponseDto)
            }

            bpn.startsWith("BPNS") -> {
                val certificates = certificateRepository.findByEnclosedSitesSiteBpn(bpn, pageRequest)
                if (certificates.totalElements == 0L) {
                    throw CertificateNotExists("Site", bpn)
                }
                certificates.toPageDto(certificateMapping::toCertificateResponseDto)
            }

            else -> {
                throw InvalidBpnFormatException(bpn)
            }
        }

    }

    fun getCertificateByTypeAndBpn(bpn: String, certificateType: String, pageRequest: PageRequest): PageDto<CertificateResponseDto> {
        if (bpn.isBlank() || certificateType.isBlank()) {
            throw IllegalArgumentException("Provided business partner number or certificate type is null or empty")
        }

        val certificateTypes = certificateTypeRepository.findByCertificateType(certificateType)
        if (certificateTypes.isEmpty()) {
            throw CertificateTypeNotExists(CertificateTypeDB::class.simpleName!!, certificateType)
        }

        return when {
            bpn.startsWith("BPNL") -> {
                val certificates = certificateRepository.findByBusinessPartnerNumberAndTypeCertificateType(bpn, certificateType, pageRequest)
                if (certificates.totalElements == 0L) {
                    throw CertificateNotExists("Legal Entity", bpn)
                }
                certificates.toPageDto(certificateMapping::toCertificateResponseDto)
            }

            bpn.startsWith("BPNS") -> {
                val certificates = certificateRepository.findByEnclosedSitesSiteBpnAndTypeCertificateType(bpn, certificateType, pageRequest)
                if (certificates.totalElements == 0L) {
                    throw CertificateNotExists("Site", bpn)
                }
                certificates.toPageDto(certificateMapping::toCertificateResponseDto)
            }

            else -> {
                throw InvalidBpnFormatException(bpn)
            }
        }
    }

}