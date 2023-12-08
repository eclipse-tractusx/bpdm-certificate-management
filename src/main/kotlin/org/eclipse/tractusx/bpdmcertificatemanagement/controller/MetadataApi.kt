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
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.CertificateTypeDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.PaginationRequest
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.PageDto
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import javax.validation.Valid

@RequestMapping("/api/catena", produces = [MediaType.APPLICATION_JSON_VALUE])
@HttpExchange("/api/catena")
interface MetadataApi {

    @Operation(
        summary = "Register a new certificate type",
        operationId = "setCertificateType",
        description = "Register a new certificate type.",
        responses = [
            ApiResponse(responseCode = "200", description = "Successfully created"),
            ApiResponse(responseCode = "400", description = "Malformed URL", content = [Content()]),
            ApiResponse(responseCode = "409", description = "Type already exists", content = [Content()])
        ]
    )
    @PostMapping("/certificate-types")
    @PostExchange("/certificate-types")
    fun setCertificateType(@Parameter(description = "", required = true) @Valid @RequestBody certificateTypeDto: CertificateTypeDto): CertificateTypeDto


    @Operation(
        summary = "Get certificate types",
        operationId = "getCertificateTypes",
        description = "Get a list of all currently registered certificate types.",
        responses = [
            ApiResponse(responseCode = "200", description = "List of registered certificate types, or can be empty"),
            ApiResponse(responseCode = "400", description = "Malformed URL")
        ]
    )
    @GetMapping("/certificate-types")
    @GetExchange("/certificate-types")
    fun getCertificateTypes(@ParameterObject paginationRequest: PaginationRequest): PageDto<CertificateTypeDto>

}
