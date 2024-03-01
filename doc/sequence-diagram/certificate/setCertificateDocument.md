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
    Note over User, CertificateController: Path: api/catena/certificate/document
    Note over User, CertificateController: Method: POST

User ->> CertificateController : setCertificateDocument(certificateDocumentRequestDto)
activate CertificateController
CertificateController ->> CertificateService : createCertificate(certificateDocumentRequestDto)
activate CertificateService

CertificateService ->> InvalidBpnFormatException : validateCertificateBeforeProcess(certificateDocumentRequestDto) <br> [Does validation of BPN's format (BPNL, BPNS or BPN). IF formart is <br> not accordingly, InvalidBpnFormatException is thrown]

alt Invokes findCertificateType(certificateDocumentRequestDto.type)
CertificateTypeRepository -->> CertificateService : findByCertificateTypeAndCertificateVersion(certificateType, certificateVersion)  
CertificateService ->> CertificateTypeNotExists : If DB query does not return any values, CertificateTypeNotExists is thrown
end

CertificateService -->> CertificateMapping : Invokes toCertificateDB(certificateDocumentRequestDto, certificateType); 
activate CertificateMapping
note over CertificateMapping: Here Mapping of CertificateDocumentRequestDto to <br> CertificateDB occurs. All the fields are mapped accordingly
CertificateMapping -->> CertificateService : Return List<BpnCertifiedCertificateResponse>; 
deactivate CertificateMapping

CertificateService -->> CertificateRepository : save() [Save create CertificateDB object in the Database]  


CertificateService -->> CertificateController : Return CertificateDocumentResponseDto?; 
deactivate CertificateService
CertificateController -->> User : Return ResponseEntity<CertificateDocumentResponseDto>; 
deactivate CertificateController

    Note over User, CertificateController: Response: 201 OK 
    Note over User, CertificateController: Content-Type: application/json

````