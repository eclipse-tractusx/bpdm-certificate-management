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

package org.eclipse.tractusx.bpdmcertificatemanagement.controller

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.eclipse.tractusx.bpdmcertificatemanagement.config.CertificateClient
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.*
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.CertificateDocumentRequestDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.PaginationRequest
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.BpnCertifiedCertificateResponse
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.PageDto
import org.eclipse.tractusx.bpdmcertificatemanagement.repository.CertificateRepository
import org.eclipse.tractusx.bpdmcertificatemanagement.util.DbTestHelpers
import org.eclipse.tractusx.bpdmcertificatemanagement.util.PostgreSQLContextInitializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.time.Instant
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = [PostgreSQLContextInitializer::class])
internal class CertificateControllerIT @Autowired constructor(
    val certificateClient: CertificateClient,
    private val certificateRepository: CertificateRepository,
    private val dbTestHelpers: DbTestHelpers
) {

    @BeforeEach
    fun beforeEach() {
        dbTestHelpers.truncateDbTables()
    }

    val certificateValue = CertificateTypeDto("ISO9001", "0.0.1")

    /** Retrieve an existing certificate document */
    @Test
    fun `retrieve a certificate document by UUID`() {
        val certificate = createCertificateDocumentRequestDto()

        createCertificateProcess()

        val retrievedCertificate = certificateRepository.findByBusinessPartnerNumber(certificate.businessPartnerNumber, PageRequest.of(0, 1))

        val certificateByUUID =  certificateClient.certificateApi.getCertificateDocument(retrievedCertificate.content.first().documentID)

        assertEquals(HttpStatus.OK, certificateByUUID.statusCode)

    }

    /** Retrieve an non existing certificate document */
    @Test
    fun `retrieve a non certificate document by UUID`() {

        try {
            certificateClient.certificateApi.getCertificateDocument(UUID.fromString("50f574fc-373c-4732-8c5b-cfd3fbb8e76d"))
        } catch (e: WebClientResponseException) {
            assertEquals(HttpStatus.NOT_FOUND, e.statusCode)
        }

    }

    /** Register a certificate document */
    @Test
    fun `insert a certificate document`() {

        val certificate = createCertificateDocumentRequestDto()

        createCertificateProcess()

        val createdCertificate = certificateClient.certificateApi.getCertificatesByBpnPaginated(certificate.businessPartnerNumber, PaginationRequest(0, 2))

        assertNotEquals(createdCertificate.content, null)

    }

    /** Register a certificate document */
    @Test
    fun `insert a certificate document with wrong BPN formats`() {

        val certificate = createCertificateDocumentRequestDto()

        //Create a Type
        certificateClient.metadataApi.setCertificateType(certificateValue)

        try {
            certificateClient.certificateApi.setCertificateDocument(certificate.copy(businessPartnerNumber = "WrongFormatBPN"))
        } catch (e: WebClientResponseException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.statusCode)
        }

    }

    /** Checks whether a provided BPN is certified for a specific certificate type */
    @Test
    fun `check Certificate by Bpn and Type`() {

        val certificate = createCertificateDocumentRequestDto()
        val response = createBpnCertifiedCertificateResponse()

        createCertificateProcess()

        val createdCertificate = certificateClient.certificateApi.checkCertificateByBpnAndType(certificate.businessPartnerNumber, certificate.type.certificateType)

        assertThat(createdCertificate).usingRecursiveComparison().ignoringFieldsMatchingRegexes(".*createdAt*", ".*updatedAt*", ".*validFrom*", ".*validUntil*")
            .isEqualTo(listOf(response))

    }

    /** Checks whether a provided BPN is certified for a specific certificate type, but
     * in this case the certificate type does not exist in the DB */
    @Test
    fun `check a non existent Certificate by Bpn and Type`() {

        val certificate = createCertificateDocumentRequestDto()

        createCertificateProcess()

        try {
            certificateClient.certificateApi.checkCertificateByBpnAndType("WrongFormatBPN", certificate.type.certificateType)
        } catch (e: WebClientResponseException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.statusCode)
        }

    }

    /** Retrieves all certificates available for the BPN are returned */
    @Test
    fun `get Certificate by Bpn`() {

        val certificate = createCertificateDocumentRequestDto()
        val response = createCertificateResponseDto()

        createCertificateProcess()

        val createdCertificate = certificateClient.certificateApi.getCertificatesByBpnPaginated(certificate.businessPartnerNumber, PaginationRequest(0, 1))

        assertThat(createdCertificate).usingRecursiveComparison().ignoringFieldsMatchingRegexes(".*createdAt*", ".*updatedAt*", ".*validFrom*", ".*validUntil*").isEqualTo(
            PageDto(
                totalElements = 1,
                totalPages = 1,
                page = 0,
                contentSize = 1,
                content = listOf(response)
            )
        )

    }

    /** Retrieves all certificates available for the BPN, but in this case the BPN does not exist */
    @Test
    fun `get Certificate by non existent Bpn`() {

        createCertificateProcess()

        try {
            certificateClient.certificateApi.getCertificatesByBpnPaginated("BPNL_NonExistent", PaginationRequest(0, 1))
        } catch (e: WebClientResponseException) {
            assertEquals(HttpStatus.NOT_FOUND, e.statusCode)
        }

    }

    /** Retrieves certificate based on certificate type for provided business partner number */
    @Test
    fun `get Certificate by Bpn and Type paginated`() {

        val certificate = createCertificateDocumentRequestDto()
        val response = createCertificateResponseDto()

        createCertificateProcess()

        val createdCertificate = certificateClient.certificateApi.getCertificateByTypeAndBpnPaginated(certificate.businessPartnerNumber, "ISO9001", PaginationRequest(0, 1))

        assertThat(createdCertificate).usingRecursiveComparison().ignoringFieldsMatchingRegexes(".*createdAt*", ".*updatedAt*", ".*validFrom*", ".*validUntil*").isEqualTo(
            PageDto(
                totalElements = 1,
                totalPages = 1,
                page = 0,
                contentSize = 1,
                content = listOf(response)
            )
        )
    }

    private fun createCertificateProcess() {
        val certificate = createCertificateDocumentRequestDto()

        //Create a Type
        certificateClient.metadataApi.setCertificateType(certificateValue)
        //Create the certificate
        certificateClient.certificateApi.setCertificateDocument(certificate)
    }

    private fun createBpnCertifiedCertificateResponse(): BpnCertifiedCertificateResponse {

        return BpnCertifiedCertificateResponse(
            businessPartnerNumber = "BPNL_001",
            isCertified = true,
            validUntil = null,
            trustLevel = TrustLevelType.None,
            type = CertificateTypeDto("ISO9001", "0.0.1")
        )

    }

    private fun createCertificateDocumentRequestDto(): CertificateDocumentRequestDto {

        return CertificateDocumentRequestDto(
            businessPartnerNumber = "BPNL_001",
            type = CertificateTypeDto("ISO9001", "0.0.1"),
            registrationNumber = "RegistrationNumber01",
            areaOfApplication = "AreaTest01",
            remark = "Remark01",
            enclosedSites = emptyList(),
            validFrom = null,
            validUntil = null,
            issuer = "BPNS_001",
            trustLevel = TrustLevelType.None,
            validator = TrustValidatorDto("NameTest01", "BPNS_001"),
            uploader = "BPNL_001",
            document = DocumentDto("RandomValue", FileType.PDF)
        )
    }

    private fun createCertificateResponseDto(): CertificateResponseDto {

        return CertificateResponseDto(
            businessPartnerNumber = "BPNL_001",
            type = CertificateTypeDto("ISO9001", "0.0.1"),
            registrationNumber = "RegistrationNumber01",
            areaOfApplication = "AreaTest01",
            remark = "Remark01",
            enclosedSites = emptyList(),
            validFrom = null,
            validUntil = null,
            issuer = "BPNS_001",
            trustLevel = TrustLevelType.None,
            validator = TrustValidatorDto("NameTest01", "BPNS_001"),
            uploader = "BPNL_001",
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }

}