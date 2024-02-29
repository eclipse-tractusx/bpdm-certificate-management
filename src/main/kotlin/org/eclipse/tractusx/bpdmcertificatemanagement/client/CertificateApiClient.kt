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

package org.eclipse.tractusx.bpdmcertificatemanagement.client

import org.eclipse.tractusx.bpdmcertificatemanagement.controller.CertificateApi
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.CertificateDocumentRequestDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.PaginationRequest
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.BpnCertifiedCertificateResponse
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateDocumentResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.PageDto
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import java.util.*

@HttpExchange("/api/catena")
interface CertificateApiClient: CertificateApi {

    @PostExchange("/certificate/document")
    override fun setCertificateDocument(@RequestBody certificateDocumentRequestDto: CertificateDocumentRequestDto): ResponseEntity<CertificateDocumentResponseDto>

    @GetExchange("/certificate/document/{cdID}")
    override fun getCertificateDocument(@PathVariable cdID: UUID): ResponseEntity<CertificateDocumentResponseDto>

    @GetExchange("/certificate/{bpn}")
    override fun getCertificatesByBpnPaginated(
        @PathVariable("bpn") bpn: String,
        @ParameterObject paginationRequest: PaginationRequest
    ): PageDto<CertificateResponseDto>

    @GetExchange("/certificate/{bpn}/{certificateType}")
    override fun getCertificateByTypeAndBpnPaginated(
        @PathVariable("bpn") bpn: String,
        @PathVariable("certificateType") certificateType: String,
        @ParameterObject paginationRequest: PaginationRequest
    ): PageDto<CertificateResponseDto>

    @GetExchange("/certificate/simple/{bpn}/{certificateType}")
    override fun checkCertificateByBpnAndType(
        @PathVariable bpn: String,
        @PathVariable certificateType: String
    ): List<BpnCertifiedCertificateResponse>

}