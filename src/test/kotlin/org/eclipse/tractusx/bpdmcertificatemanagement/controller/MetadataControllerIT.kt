/*
 * Copyright (c) 2023, 2024 Contributors to the Eclipse Foundation
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

import org.assertj.core.api.Assertions.assertThat
import org.eclipse.tractusx.bpdmcertificatemanagement.client.CertificateManagementApiClient
import org.eclipse.tractusx.bpdmcertificatemanagement.data.CertificateTestValues
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.PaginationRequest
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.PageDto
import org.eclipse.tractusx.bpdmcertificatemanagement.repository.CertificateTypeRepository
import org.eclipse.tractusx.bpdmcertificatemanagement.util.DbTestHelpers
import org.eclipse.tractusx.bpdmcertificatemanagement.util.PostgreSQLContextInitializer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = [PostgreSQLContextInitializer::class])
internal class MetadataControllerIT @Autowired constructor(
    val certificateClient: CertificateManagementApiClient,
    private val certificateTypeRepository: CertificateTypeRepository,
    private val dbTestHelpers: DbTestHelpers
) {

    @BeforeEach
    fun beforeEach() {
        dbTestHelpers.truncateDbTables()
    }

    /** Register a new certificate type */
    @Test
    fun `insert Certificate Types`() {
        certificateClient.metadataApi.setCertificateType(CertificateTestValues.certificateType)
        val certificateType = certificateTypeRepository.findAll()
        Assertions.assertNotEquals(certificateType, null)
    }

    /** Register an already existing certificate type */
    @Test
    fun `insert duplicate Certificate Types`() {
        //Insert Once
        certificateClient.metadataApi.setCertificateType(CertificateTestValues.certificateType)

        try {
            certificateClient.metadataApi.setCertificateType(CertificateTestValues.certificateType)
        } catch (e: WebClientResponseException) {
            assertEquals(HttpStatus.CONFLICT, e.statusCode)
        }

    }

    /** Register a certificate type, but with wrong data */
    @Test
    fun `insert wrong Certificate Types`() {

        try {
            certificateClient.metadataApi.setCertificateType(CertificateTestValues.certificateType.copy(""))
        } catch (e: WebClientResponseException) {
            assertEquals(HttpStatus.BAD_REQUEST, e.statusCode)
        }

    }

    /** Gets a list of all currently registered certificate types */
    @Test
    fun `retrieve all Certificate Types`() {
        certificateClient.metadataApi.setCertificateType(CertificateTestValues.certificateType)
        val certificates = certificateClient.metadataApi.getCertificateTypes(PaginationRequest(0,1))

        assertThat(certificates).isEqualTo(
            PageDto(
                totalElements = 1,
                totalPages = 1,
                page = 0,
                contentSize = 1,
                content = listOf(CertificateTestValues.certificateType)
        ))
    }

}