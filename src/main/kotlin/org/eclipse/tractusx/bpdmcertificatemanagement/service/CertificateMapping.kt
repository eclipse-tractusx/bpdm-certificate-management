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

package org.eclipse.tractusx.bpdmcertificatemanagement.service

import org.eclipse.tractusx.bpdmcertificatemanagement.dto.CertificateTypeDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.EnclosedSiteDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.TrustValidatorDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.request.CertificateDocumentRequestDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateDocumentResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.response.CertificateResponseDto
import org.eclipse.tractusx.bpdmcertificatemanagement.entity.CertificateDB
import org.eclipse.tractusx.bpdmcertificatemanagement.entity.CertificateTypeDB
import org.eclipse.tractusx.bpdmcertificatemanagement.entity.EnclosedSiteDB
import org.eclipse.tractusx.bpdmcertificatemanagement.entity.TrustValidatorDB
import org.springframework.stereotype.Service

@Service
class CertificateMapping {

    fun toCertificateDB(dto: CertificateDocumentRequestDto, certificateType: CertificateTypeDB): CertificateDB {
        return CertificateDB(
            businessPartnerNumber = dto.businessPartnerNumber,
            type = certificateType,
            registrationNumber = dto.registrationNumber,
            areaOfApplication = dto.areaOfApplication,
            remark = dto.remark,
            enclosedSites = dto.enclosedSites?.mapNotNull { toEnclosedSitesDB(it) }?.toSortedSet(),
            validFrom = dto.validFrom,
            validUntil = dto.validUntil,
            issuer = dto.issuer,
            trustLevel = dto.trustLevel,
            validator = dto.validator?.let { toTrustValidatorDB(it) },
            uploader = dto.uploader
        )
    }

    private fun toCertificateTypeDB(dto: CertificateTypeDto) =
        CertificateTypeDB(
            certificateType = dto.certificateType,
            certificateVersion = dto.certificateVersion
        )

    private fun toEnclosedSitesDB(dto: EnclosedSiteDto) =
        EnclosedSiteDB(
            siteBpn = dto.siteBpn,
            areaOfApplication = dto.areaOfApplication
        )

    private fun toTrustValidatorDB(dto: TrustValidatorDto?): TrustValidatorDB? {
        return dto?.let {
            TrustValidatorDB(
                validatorName = it.validatorName,
                validatorBpn = it.validatorBpn
            )
        }
    }

    fun toCertificateDocumentResponseDto(entity: CertificateDB): CertificateDocumentResponseDto {
        return CertificateDocumentResponseDto(
            businessPartnerNumber = entity.businessPartnerNumber,
            type = toCertificateTypeDto(entity.type),
            registrationNumber = entity.registrationNumber,
            areaOfApplication = entity.areaOfApplication,
            remark = entity.remark,
            enclosedSites = entity.enclosedSites?.map { toEnclosedSiteDto(it) } ?: emptyList(),
            validFrom = entity.validFrom,
            validUntil = entity.validUntil,
            issuer = entity.issuer,
            trustLevel = entity.trustLevel,
            validator = entity.validator?.let { toTrustValidatorDto(it) },
            uploader = entity.uploader,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    fun toCertificateResponseDto(entity: CertificateDB): CertificateResponseDto {
        return CertificateResponseDto(
            businessPartnerNumber = entity.businessPartnerNumber,
            type = toCertificateTypeDto(entity.type),
            registrationNumber = entity.registrationNumber,
            areaOfApplication = entity.areaOfApplication,
            remark = entity.remark,
            enclosedSites = entity.enclosedSites?.map { toEnclosedSiteDto(it) } ?: emptyList(),
            validFrom = entity.validFrom,
            validUntil = entity.validUntil,
            issuer = entity.issuer,
            trustLevel = entity.trustLevel,
            validator = entity.validator?.let { toTrustValidatorDto(it) },
            uploader = entity.uploader,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    private fun toCertificateTypeDto(entity: CertificateTypeDB) =
        CertificateTypeDto(
            certificateType = entity.certificateType,
            certificateVersion = entity.certificateVersion
        )

    private fun toEnclosedSiteDto(entity: EnclosedSiteDB): EnclosedSiteDto {
        return EnclosedSiteDto(
            siteBpn = entity.siteBpn,
            areaOfApplication = entity.areaOfApplication
        )
    }

    private fun toTrustValidatorDto(entity: TrustValidatorDB): TrustValidatorDto {
        return TrustValidatorDto(
            validatorName = entity.validatorName,
            validatorBpn = entity.validatorBpn
        )
    }

}
