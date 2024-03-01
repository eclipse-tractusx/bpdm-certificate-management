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
    Note over User, MetadataController: Method: GET

User ->> MetadataController : getCertificateTypes
activate MetadataController
MetadataController ->> MetadataService : getCertificateTypes
activate MetadataService
CertificateTypeRepository -->> MetadataService : findAll() [Retrieve all types paginated]
MetadataService ->> ResponseMappings : Mapping of each CertificateTypeDB to CertificateTypeDto
activate ResponseMappings
ResponseMappings -->> MetadataService : #32; 
deactivate ResponseMappings
MetadataService ->> ResponseMappings : Mapping of a Page to PageDto
activate ResponseMappings
ResponseMappings -->> MetadataService : #32; 
deactivate ResponseMappings
MetadataService -->> MetadataController : Returns PageDto<CertificateTypeDto> 
deactivate MetadataService
MetadataController ->> User : Returns PageDto<CertificateTypeDto>
deactivate MetadataController

    Note over User, MetadataController: Response: 200 OK 
    Note over User, MetadataController: Content-Type: application/json

````