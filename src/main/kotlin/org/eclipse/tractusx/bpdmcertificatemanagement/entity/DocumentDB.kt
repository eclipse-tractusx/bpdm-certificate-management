/*
 * Copyright (c) 2023, 2024 Contributors to the Eclipse Foundation
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
import org.eclipse.tractusx.bpdmcertificatemanagement.dto.FileType

@Entity
@Table(name = "document")
class DocumentDB (

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certificate_sequence")
    @SequenceGenerator(name = "certificate_sequence", sequenceName = "certificate_sequence", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    val id: Long = 0,

    @Column(name = "certificate_file", nullable = false, columnDefinition = "TEXT")
    var certificateDocument: String,

    @Column(name = "certificate_format", nullable = false)
    @Enumerated(EnumType.STRING)
    val certificateDocumentFormat: FileType

)