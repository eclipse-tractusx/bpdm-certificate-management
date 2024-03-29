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

import org.eclipse.tractusx.bpdmcertificatemanagement.dto.CertificateTypeDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.PaginationRequest
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.PageDto
import org.eclipse.tractusx.bpdmcertificatemanagement.service.MetadataService
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.RestController

@RestController
class MetadataController(
    val metadataService: MetadataService
): MetadataApi {

    override fun setCertificateType(certificateTypeDto: CertificateTypeDto): CertificateTypeDto {
        return metadataService.createCertificateType(certificateTypeDto)
    }

    override fun getCertificateTypes(paginationRequest: PaginationRequest): PageDto<CertificateTypeDto> {
        return metadataService.getCertificateTypes(PageRequest.of(paginationRequest.page, paginationRequest.size))
    }

}
