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
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.CertificateDocumentRequestDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.BpnCertifiedCertificateResponse
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateDocumentResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.PageDto
import org.eclipse.tractusx.bpdmcertificatemanagement.entity.CertificateDB
import org.eclipse.tractusx.bpdmcertificatemanagement.entity.CertificateTypeDB
import org.eclipse.tractusx.bpdmcertificatemanagement.exception.*
import org.eclipse.tractusx.bpdmcertificatemanagement.repository.CertificateRepository
import org.eclipse.tractusx.bpdmcertificatemanagement.exception.*
import org.eclipse.tractusx.bpdmcertificatemanagement.repository.CertificateTypeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class CertificateService(
    val certificateRepository: CertificateRepository,
    val certificateTypeRepository: CertificateTypeRepository,
    val certificateMapping: CertificateMapping
) {

    private val logger = KotlinLogging.logger { }

    var objectTypeLegalEntity = "Legal Entity"
    var objectTypeSite = "Site"

    fun createCertificate(certificateDocumentRequestDto: CertificateDocumentRequestDto): CertificateDocumentResponseDto? {
        logger.debug { "Executing createCertificate() with parameters $certificateDocumentRequestDto" }

        validateCertificateBeforeProcess(certificateDocumentRequestDto)
        val certificateType = findCertificateType(certificateDocumentRequestDto.type)

        val entity = certificateMapping.toCertificateDB(certificateDocumentRequestDto, certificateType)
        val result = certificateRepository.save(entity)

        return certificateMapping.toCertificateDocumentResponseDto(result)

    }

    fun retrieveCertificate(cdID: UUID): ResponseEntity<CertificateDocumentResponseDto> {
        logger.debug { "Executing retrieveCertificate() with parameters $cdID" }

        val certificate = certificateRepository.findByDocumentID(cdID)
            ?: throw CertificateDocumentIdNotFound(cdID.toString())

        return ResponseEntity.ok(certificateMapping.toCertificateDocumentResponseDto(certificate))

    }

    fun checkCertificateByBpnAndType(bpn: String, certificateType: String): List<BpnCertifiedCertificateResponse> {

        //Checks if certificate type exists in the DB
        val certificateTypes = certificateTypeRepository.findByCertificateType(certificateType)
        if (certificateTypes.isEmpty()) {
            throw CertificateTypeNotExists(CertificateTypeDB::class.simpleName!!, certificateType)
        }

        return when {
            bpn.startsWith("BPNL") -> findCertificateByBusinessPartnerNumber(bpn, certificateType)
            bpn.startsWith("BPNS") -> findCertificateByEnclosedSitesSiteBpn(bpn, certificateType)
            else -> throw InvalidBpnLSAException(bpn)
        }

    }

    private fun findCertificateByBusinessPartnerNumber(bpn: String, certificateType: String): List<BpnCertifiedCertificateResponse> {
        val certificates = certificateRepository.findByBusinessPartnerNumber(bpn)

        if (certificates.isEmpty()) {
            throw CertificateNotExists(objectTypeLegalEntity, bpn)
        }

        return createCertifiedCertificateResponse(certificates, bpn, certificateType)
    }

    private fun findCertificateByEnclosedSitesSiteBpn(bpn: String, certificateType: String): List<BpnCertifiedCertificateResponse> {
        val certificates = certificateRepository.findByEnclosedSitesSiteBpn(bpn)

        if (certificates.isEmpty()) {
            throw CertificateNotExists(objectTypeSite, bpn)
        }

        return createCertifiedCertificateResponse(certificates, bpn, certificateType)
    }

    private fun createCertifiedCertificateResponse(certificates: List<CertificateDB>, bpn: String, certificateType: String): List<BpnCertifiedCertificateResponse> {

        val matchingCertificate = certificates.filter { it.type.certificateType == certificateType }

        return if (matchingCertificate.isNotEmpty()) {
            val updatedCleanCertificate = matchingCertificate.map {
                certificateMapping.toBpnCertifiedCertificateResponse(it, true)
            }
            updatedCleanCertificate
        } else {
            listOf(BpnCertifiedCertificateResponse(bpn, false))
        }

    }

    fun getCertificatesByBpn(bpn: String, pageRequest: PageRequest): PageDto<CertificateResponseDto> {
        if (bpn.isBlank())
            throw IllegalArgumentException("Provided business partner number is null or empty")

        return when {
            bpn.startsWith("BPNL") -> {
                val certificates = certificateRepository.findByBusinessPartnerNumber(bpn, pageRequest)
                if (certificates.totalElements == 0L) {
                    throw CertificateNotExists(objectTypeLegalEntity, bpn)
                }
                certificates.toPageDto(certificateMapping::toCertificateResponseDto)
            }

            bpn.startsWith("BPNS") -> {
                val certificates = certificateRepository.findByEnclosedSitesSiteBpn(bpn, pageRequest)
                if (certificates.totalElements == 0L) {
                    throw CertificateNotExists(objectTypeSite, bpn)
                }
                certificates.toPageDto(certificateMapping::toCertificateResponseDto)
            }

            else -> {
                throw InvalidBpnLSAException(bpn)
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
                    throw CertificateNotExists(objectTypeLegalEntity, bpn)
                }
                certificates.toPageDto(certificateMapping::toCertificateResponseDto)
            }

            bpn.startsWith("BPNS") -> {
                val certificates = certificateRepository.findByEnclosedSitesSiteBpnAndTypeCertificateType(bpn, certificateType, pageRequest)
                if (certificates.totalElements == 0L) {
                    throw CertificateNotExists(objectTypeSite, bpn)
                }
                certificates.toPageDto(certificateMapping::toCertificateResponseDto)
            }

            else -> {
                throw InvalidBpnLSAException(bpn)
            }
        }
    }

    private fun validateCertificateBeforeProcess(certificateDocumentRequestDto: CertificateDocumentRequestDto) {

        validateBPNLFormat (certificateDocumentRequestDto.businessPartnerNumber)
        certificateDocumentRequestDto.issuer?.let { validateBPNLFormat (it) }
        certificateDocumentRequestDto.uploader?.let { validateBPNLFormat (it) }
        certificateDocumentRequestDto.validator?.validatorBpn?.let {  validateBPNLFormat (it)}

        certificateDocumentRequestDto.enclosedSites?.forEach { enclosed ->
            if (enclosed.siteBpn.length != 16 || !enclosed.siteBpn.startsWith("BPNS") || !enclosed.siteBpn.substring(4, 12).all { it.isDigit() } ||
                !enclosed.siteBpn.substring(12).all { it.isLetterOrDigit() }) {
                throw InvalidSiteFormatException(enclosed.siteBpn)
            }
        }

    }

    private fun validateBPNLFormat(bpn: String) {

        if (bpn.isBlank() || bpn.length != 16 || !bpn.startsWith("BPNL") || !bpn.substring(4, 12).all { it.isDigit() } ||
            !bpn.substring(12).all { it.isLetterOrDigit() }) {
            throw InvalidBpnLegalEntityException(bpn)
        }

    }

    private fun findCertificateType(typeDto: CertificateTypeDto): CertificateTypeDB {
        return certificateTypeRepository.findByCertificateTypeAndCertificateVersion(
            typeDto.certificateType,
            typeDto.certificateVersion
        ) ?: throw CertificateTypeNotExists(
            CertificateTypeDB::class.simpleName!!,
            typeDto.certificateType
        )
    }

}
