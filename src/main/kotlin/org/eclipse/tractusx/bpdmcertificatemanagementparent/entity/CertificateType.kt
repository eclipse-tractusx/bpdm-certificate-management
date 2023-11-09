package org.eclipse.tractusx.bpdmcertificatemanagementparent.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "certificate_type"
)
class CertificateType(

    @Column(name = "technical_key", nullable = false)
    val technicalKey: String,

    @Id
    val type: String,

    val version: String
)