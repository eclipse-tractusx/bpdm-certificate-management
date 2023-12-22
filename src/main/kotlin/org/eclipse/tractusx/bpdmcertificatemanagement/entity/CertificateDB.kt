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

package org.eclipse.tractusx.bpdmcertificatemanagement.entity

import jakarta.persistence.*
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.TrustLevelType
import java.time.ZonedDateTime

@Entity
@Table(
    name = "certificate",
    uniqueConstraints = [UniqueConstraint(columnNames = ["business_partner_number", "certificate_type_id"])]
)
class CertificateDB(

    @Column(name = "business_partner_number")
    var businessPartnerNumber: String?,

    @ManyToOne
    @JoinColumn(name = "certificate_type_id", nullable = false)
    var type: CertificateTypeDB,

    @Column(name = "registration_number")
    var registrationNumber: String?,

    @Column(name = "area_of_application")
    var areaOfApplication: String?,

    @Column(name = "remark")
    var remark: String?,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "enclosed_sites", joinColumns = [JoinColumn(name = "certificate_id")])
    val enclosedSites: MutableSet<EnclosedSiteDB>? = mutableSetOf(),

    @Column(name = "valid_from")
    var validFrom: ZonedDateTime?,

    @Column(name = "valid_until")
    var validUntil: ZonedDateTime?,

    @Column(name = "issuer")
    var issuer: String?,

    @Column(name = "trust_level", nullable = false)
    @Enumerated(EnumType.STRING)
    var trustLevel: TrustLevelType,

    @Embedded
    var validator: TrustValidatorDB?,

    @Column(name = "uploader")
    var uploader: String?,

): BaseEntity()
