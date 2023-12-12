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

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.CertificateDocumentRequestDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateDocumentResponseDto
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange

@RequestMapping("/api/catena", produces = [MediaType.APPLICATION_JSON_VALUE])
@HttpExchange("/api/catena")
interface CertificateApi {

    @Operation(
        summary = "Provide a specific certificate document for a given BPN.",
        operationId = "setCertificateDocument",
        description = "Use this API call to provide a specific certificate document for a given BPN.",
        responses = [
            ApiResponse(responseCode = "201", description = "Certificate created successfully"),
            ApiResponse(responseCode = "400", description = "Malformed URL", content = [Content()]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [Content()]),
            ApiResponse(responseCode = "422", description = "Unprocessable Entity ", content = [Content()]),
            ApiResponse(responseCode = "406", description = "Document not available", content = [Content()]),
            ApiResponse(responseCode = "503", description = "Service not available", content = [Content()])
        ]
    )
    @PostMapping("/certificate/document")
    @PostExchange("/certificate/document")
    fun setCertificateDocument(@RequestBody certificateDocumentRequestDto: CertificateDocumentRequestDto):ResponseEntity<CertificateDocumentResponseDto>

}