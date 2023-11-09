package org.eclipse.tractusx.bpdmcertificatemanagementparent.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class TrustValidator(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var validatorName: String,
    var validatorBPN: String
)