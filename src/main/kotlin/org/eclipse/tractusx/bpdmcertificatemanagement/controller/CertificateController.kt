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

import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.CertificateDocumentRequestDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.PaginationRequest
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateDocumentResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.PageDto
import org.eclipse.tractusx.bpdmcertificatemanagement.service.CertificateService
import org.eclipse.tractusx.bpdmcertificatemanagement.service.toPageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
class CertificateController(
    val certificateService: CertificateService
): CertificateApi {

    override fun setCertificateDocument(certificateDocumentRequestDto: CertificateDocumentRequestDto): ResponseEntity<CertificateDocumentResponseDto> {
        val result = certificateService.createCertificate(certificateDocumentRequestDto)
        return ResponseEntity
            .created(URI.create("/certificate/${result?.documentID}")) // Set the resource URI
            .body(result)
    }

    override fun getCertificateDocument(cdID: UUID): ResponseEntity<CertificateDocumentResponseDto> {
        return certificateService.retrieveCertificate(cdID)
    }

    override fun getCertificatesByBpnPaginated(
        bpn: String,
        paginationRequest: PaginationRequest
    ): PageDto<CertificateResponseDto> {
        return certificateService.getCertificatesByBpn(bpn, paginationRequest.toPageRequest())
    }

    override fun getCertificateByTypeAndBpnPaginated(
        bpn: String,
        certificateType: String,
        paginationRequest: PaginationRequest
    ): PageDto<CertificateResponseDto> {
        return certificateService.getCertificateByTypeAndBpn(bpn, certificateType, paginationRequest.toPageRequest())
    }

}
