package org.eclipse.tractusx.bpdmcertificatemanagementparent.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(
    name = "certificates"
)
class Certificate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val businessPartnerNumber: String,

    @OneToOne
    val certificateType: CertificateType,

    val certificateRegistrationNumber: String,

    val certificateAreaOfApplication: String,

    val certificateRemark: String?,

    @OneToMany(mappedBy = "certificate", cascade = [CascadeType.ALL])
    val certificateEnclosedSites: List<EnclosedSite>,

    val certificateValidFrom: LocalDateTime,

    val certificateValidUntil: LocalDateTime,

    val certificateIssuer: String,

    val certificateTrustLevel: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trust_validator_id")
    val certificateValidator: TrustValidator,

    val certificateUploader: String,

    val certificateDocumentID: String
)