<!--
  - Copyright (c) 2023,2024 Contributors to the Eclipse Foundation
  -
  - See the NOTICE file(s) distributed with this work for additional
  - information regarding copyright ownership.
  -
  - This program and the accompanying materials are made available under the
  - terms of the Apache License, Version 2.0 which is available at
  - https://www.apache.org/licenses/LICENSE-2.0.
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  - WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  - License for the specific language governing permissions and limitations
  - under the License.
  -
  - SPDX-License-Identifier: Apache-2.0
-->
````mermaid

sequenceDiagram
actor User

    autonumber
    Note over User, CertificateController: Path: api/catena/certificate/document/{cdID}
    Note over User, CertificateController: Method: GET

User ->> CertificateController : getCertificateDocument(UUID)
activate CertificateController
CertificateController ->> CertificateService : retrieveCertificate(UUID)
activate CertificateService
CertificateService ->> CertificateRepository : findByDocumentID [If Document is not found by ID, <br>shows CertificateDocumentIdNotFound exception]
activate CertificateRepository
CertificateRepository -->> CertificateService : #32; 
deactivate CertificateRepository
CertificateService ->> CertificateMapping : toCertificateDocumentResponseDto [Mapping of each field <br >from CertificateDB to CertificateDocumentResponseDto]
activate CertificateMapping
CertificateMapping -->> CertificateService : Return CertificateDocumentResponseDto; 
deactivate CertificateMapping
CertificateService -->> CertificateController : Return ResponseEntity<CertificateDocumentResponseDto>; 
deactivate CertificateService
CertificateController -->> User : Return ResponseEntity<CertificateDocumentResponseDto>; 
deactivate CertificateController

    Note over User, CertificateController: Response: 200 OK 
    Note over User, CertificateController: Content-Type: application/json

````