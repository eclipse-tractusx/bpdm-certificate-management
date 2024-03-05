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
    Note over User, MetadataController: Path: api/catena/certificate-types
    Note over User, MetadataController: Method: POST

User ->> MetadataController : setCertificateType
activate MetadataController
MetadataController ->> MetadataService : createCertificateType
activate MetadataService

MetadataService ->> InvalidTypeFormatException : validateTypeBeforeProcess [If Type or Version is empty shows exception]
activate InvalidTypeFormatException 
InvalidTypeFormatException -->> MetadataService : #32; 
deactivate InvalidTypeFormatException

MetadataService ->> CertificateTypeRepository : Checks DB if certificate is already persisted 
activate CertificateTypeRepository
CertificateTypeRepository -->> MetadataService : #32; 
deactivate CertificateTypeRepository

MetadataService ->> CertificateTypeAlreadyExists : If already exists shows exception
activate CertificateTypeAlreadyExists
CertificateTypeAlreadyExists -->> MetadataService : #32; 
deactivate CertificateTypeAlreadyExists

MetadataService ->> MetadataService : Create CertificateTypeDB with values passed

MetadataService -->> CertificateTypeRepository : save() [Save create CertificateTypeDB object in the Database]  

MetadataService ->> ResponseMappings : Invokes toDto() of entity CertificateTypeDB
activate ResponseMappings
note over ResponseMappings: Here Mapping of CertificateTypeDB to <br> CertificateTypeDto occurs. All the fields are mapped accordingly
ResponseMappings -->> MetadataService : #32; 
deactivate ResponseMappings
MetadataService -->> MetadataController : Returns CertificateTypeDto;; 
deactivate MetadataService
MetadataController -->> User : Returns CertificateTypeDto;; 
deactivate MetadataController

    Note over User, MetadataController: Response: 200 OK 
    Note over User, MetadataController: Content-Type: application/json



````