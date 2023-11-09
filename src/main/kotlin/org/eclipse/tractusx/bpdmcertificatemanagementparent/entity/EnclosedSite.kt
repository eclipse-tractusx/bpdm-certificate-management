package org.eclipse.tractusx.bpdmcertificatemanagementparent.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class EnclosedSite(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var siteBPN: String,
    var areaOfApplication: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "certificate_id")
    var certificate: Certificate
)