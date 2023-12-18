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

package org.eclipse.tractusx.bpdmcertificatemanagement.exception

import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CustomErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.ZonedDateTime

@ControllerAdvice
class CertificateControllerExceptionHandler {

    @ExceptionHandler(CertificateTypeNotExists::class)
    fun handleCertificateTypeNotExists(ex: CertificateTypeNotExists): ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            timestamp = ZonedDateTime.now(),
            status = HttpStatus.UNPROCESSABLE_ENTITY,
            error = ex.message.toString(),
            path = "/api/catena/certificate/document"
        )
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse)
    }

    @ExceptionHandler(InvalidBpnFormatException::class)
    fun handleBusinessPartnerDetails(ex: InvalidBpnFormatException): ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            timestamp = ZonedDateTime.now(),
            status = HttpStatus.BAD_REQUEST,
            error = ex.message.toString(),
            path = "/api/catena/certificate/{bpn}"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(CertificateNotExists::class)
    fun handleCertificateNotExists(ex: CertificateNotExists): ResponseEntity<CustomErrorResponse> {
        val errorResponse = CustomErrorResponse(
            timestamp = ZonedDateTime.now(),
            status = HttpStatus.NOT_FOUND,
            error = ex.message.toString(),
            path = "/api/catena/certificate/{bpn}"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
}
