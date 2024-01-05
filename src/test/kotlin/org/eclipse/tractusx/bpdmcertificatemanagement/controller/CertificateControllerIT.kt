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
import org.eclipse.tractusx.bpdmcertificatemanagement.data.CertificateTestValues
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.*
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.PaginationRequest
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

    /** Retrieve an existing certificate document */
    @Test
    fun `retrieve a certificate document by UUID`() {

        createCertificateProcess()

        val retrievedCertificate = certificateRepository.findByBusinessPartnerNumber(CertificateTestValues.certificateDocumentRequest.businessPartnerNumber, PageRequest.of(0, 1))

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

        createCertificateProcess()

        val createdCertificate = certificateClient.certificateApi.getCertificatesByBpnPaginated(CertificateTestValues.certificateDocumentRequest.businessPartnerNumber, PaginationRequest(0, 2))

        assertNotEquals(createdCertificate.content, null)

    }

    /** Register a certificate document */
    @Test
    fun `insert a certificate document with wrong BPN formats`() {

        //Create a Type
        certificateClient.metadataApi.setCertificateType(CertificateTestValues.certificateType)

        try {
            certificateClient.certificateApi.setCertificateDocument(CertificateTestValues.certificateDocumentRequest.copy(businessPartnerNumber = "WrongFormatBPN"))
        } catch (e: WebClientResponseException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.statusCode)
        }

    }

    /** Checks whether a provided BPN is certified for a specific certificate type */
    @Test
    fun `check Certificate by Bpn and Type`() {

        createCertificateProcess()

        val createdCertificate = certificateClient.certificateApi.checkCertificateByBpnAndType(CertificateTestValues.certificateDocumentRequest.businessPartnerNumber,
            CertificateTestValues.certificateDocumentRequest.type.certificateType)

        assertThat(createdCertificate).usingRecursiveComparison().ignoringFieldsMatchingRegexes(".*createdAt*", ".*updatedAt*", ".*validFrom*", ".*validUntil*")
            .isEqualTo(listOf(CertificateTestValues.bpnCertifiedCertificateResponse))

    }

    /** Checks whether a provided BPN is certified for a specific certificate type, but
     * in this case the certificate type does not exist in the DB */
    @Test
    fun `check a non existent Certificate by Bpn and Type`() {

        createCertificateProcess()

        try {
            certificateClient.certificateApi.checkCertificateByBpnAndType("WrongFormatBPN", CertificateTestValues.certificateDocumentRequest.type.certificateType)
        } catch (e: WebClientResponseException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.statusCode)
        }

    }

    /** Retrieves all certificates available for the BPN are returned */
    @Test
    fun `get Certificate by Bpn`() {

        createCertificateProcess()

        val createdCertificate = certificateClient.certificateApi.getCertificatesByBpnPaginated(CertificateTestValues.certificateDocumentRequest.businessPartnerNumber, PaginationRequest(0, 1))

        assertThat(createdCertificate).usingRecursiveComparison().ignoringFieldsMatchingRegexes(".*createdAt*", ".*updatedAt*", ".*validFrom*", ".*validUntil*").isEqualTo(
            PageDto(
                totalElements = 1,
                totalPages = 1,
                page = 0,
                contentSize = 1,
                content = listOf(CertificateTestValues.certificateResponse)
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

        createCertificateProcess()

        val createdCertificate = certificateClient.certificateApi.getCertificateByTypeAndBpnPaginated(CertificateTestValues.certificateDocumentRequest.businessPartnerNumber, "ISO9001", PaginationRequest(0, 1))

        assertThat(createdCertificate).usingRecursiveComparison().ignoringFieldsMatchingRegexes(".*createdAt*", ".*updatedAt*", ".*validFrom*", ".*validUntil*").isEqualTo(
            PageDto(
                totalElements = 1,
                totalPages = 1,
                page = 0,
                contentSize = 1,
                content = listOf(CertificateTestValues.certificateResponse)
            )
        )
    }

    private fun createCertificateProcess() {

        //Create a Type
        certificateClient.metadataApi.setCertificateType(CertificateTestValues.certificateType)
        //Create the certificate
        certificateClient.certificateApi.setCertificateDocument(CertificateTestValues.certificateDocumentRequest)
    }

}