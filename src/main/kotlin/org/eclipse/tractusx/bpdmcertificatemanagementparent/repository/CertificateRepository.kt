package org.eclipse.tractusx.bpdmcertificatemanagementparent.repository

import org.eclipse.tractusx.bpdmcertificatemanagementparent.entity.Certificate
import org.springframework.data.repository.CrudRepository

interface CertificateRepository:CrudRepository<Certificate, Long> {

    fun findAllByBusinessPartnerNumber(bpn: String): List<Certificate>
}