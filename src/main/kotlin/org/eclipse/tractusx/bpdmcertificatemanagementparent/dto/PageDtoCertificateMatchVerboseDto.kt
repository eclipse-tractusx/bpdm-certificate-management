package org.eclipse.tractusx.bpdmcertificatemanagementparent.dto

data class PageDtoCertificateMatchVerboseDto(
    var totalElements: Int,
    var totalPages: Int,
    var page: Int,
    var contentSize: Int,
    var content: List<CertificateMatchVerboseDto>
)
