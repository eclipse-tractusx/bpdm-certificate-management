package org.eclipse.tractusx.bpdmcertificatemanagementparent.service

import org.eclipse.tractusx.bpdmcertificatemanagementparent.dto.*
import org.eclipse.tractusx.bpdmcertificatemanagementparent.repository.CertificateRepository
import org.springframework.stereotype.Service

@Service
class CertificateService(
    val certificateRepository: CertificateRepository
) {

    fun getCertificatesPaginated(bpn: String, page: Int, size: Int): PageDtoCertificateMatchVerboseDto {
        val certificates = certificateRepository.findAllByBusinessPartnerNumber(bpn)

        return PageDtoCertificateMatchVerboseDto(
            totalElements = certificates.size,
            totalPages = certificates.size / size,
            page = page,
            contentSize = certificates.size,
            content = certificates.map { certificate ->
                CertificateMatchVerboseDto(
                    businessPartnerBPN = certificate.businessPartnerNumber,
                    certificate = CertificateTypeVerboseDto(
                        certificateType = certificate.certificateType.type,
                        certificateVersion = certificate.certificateType.version
                    ),
                    certificateRegistrationNumber = certificate.certificateRegistrationNumber,
                    certificateAreaOfApplication = certificate.certificateAreaOfApplication,
                    certificateRemark = certificate.certificateRemark,
                    certificateEnclosedSites = certificate.certificateEnclosedSites.map { enclosedSite ->
                        EnclosedSiteVerboseDto(
                            siteBPN = enclosedSite.siteBPN,
                            areaOfApplication = enclosedSite.areaOfApplication
                        )
                    },
                    certificateValidFrom = certificate.certificateValidFrom,
                    certificateValidUntil = certificate.certificateValidUntil,
                    certificateIssuer = certificate.certificateIssuer,
                    certificateTrustLevel = certificate.certificateTrustLevel,
                    certificateValidator = TrustValidatorVerboseDto(
                        validatorName = certificate.certificateValidator.validatorName,
                        validatorBPN = certificate.certificateValidator.validatorBPN
                    ),
                    certificateuploader = certificate.certificateUploader,
                    certificateDocumentID = certificate.certificateDocumentID
                )
            }
        )
    }
}