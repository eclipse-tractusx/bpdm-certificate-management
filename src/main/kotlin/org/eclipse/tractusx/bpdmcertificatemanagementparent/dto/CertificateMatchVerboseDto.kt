package org.eclipse.tractusx.bpdmcertificatemanagementparent.dto

import java.time.LocalDateTime

data class CertificateMatchVerboseDto(
    var businessPartnerBPN: String,
    var certificate: CertificateTypeVerboseDto,
    var certificateRegistrationNumber: String,
    var certificateAreaOfApplication: String,
    var certificateRemark: String?,
    var certificateEnclosedSites: List<EnclosedSiteVerboseDto>,
    var certificateValidFrom: LocalDateTime,
    var certificateValidUntil: LocalDateTime,
    var certificateIssuer: String,
    var certificateTrustLevel: String,
    var certificateValidator: TrustValidatorVerboseDto,
    var certificateuploader: String,
    var certificateDocumentID: String
)
