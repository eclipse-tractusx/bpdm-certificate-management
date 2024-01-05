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

package org.eclipse.tractusx.bpdmcertificatemanagement.data

import org.eclipse.tractusx.bpdmcertificatemanagement.dto.*
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.CertificateDocumentRequestDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.BpnCertifiedCertificateResponse
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateResponseDto
import java.time.Instant

object CertificateTestValues {

    val certificateType = CertificateTypeDto(
        certificateType = "ISO9001",
        certificateVersion = "0.0.1"
    )

    val certificateDocumentRequest =  CertificateDocumentRequestDto(
        businessPartnerNumber = "BPNL_001",
        type = certificateType,
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

    val certificateResponse = CertificateResponseDto(
        businessPartnerNumber = "BPNL_001",
        type = certificateType,
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

    val bpnCertifiedCertificateResponse = BpnCertifiedCertificateResponse(
        businessPartnerNumber = "BPNL_001",
        isCertified = true,
        validUntil = null,
        trustLevel = TrustLevelType.None,
        type = certificateType
    )

}
