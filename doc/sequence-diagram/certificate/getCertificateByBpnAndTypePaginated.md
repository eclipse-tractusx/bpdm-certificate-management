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
    Note over User, CertificateController: Path: api/catena/certificate/{bpn}/{certificateType}
    Note over User, CertificateController: Method: GET

User ->> CertificateController : getCertificateByTypeAndBpnPaginated(bpn, certificateType, pageRequest)
activate CertificateController
CertificateController ->> CertificateService : getCertificateByTypeAndBpn(bpn, pcertificateType, ageRequest)
activate CertificateService

CertificateRepository -->> CertificateService : findByCertificateType(certificateType); 
Note over CertificateService: If query done to database does not return a certificate by type, <br>CertificateTypeNotExists is thrown


alt Triggers method if BPN insert starts with BPNL
CertificateService ->> CertificateRepository : findByBusinessPartnerNumberAndTypeCertificateType [If no Certificate is found by bpn, shows CertificateNotExists exception]
activate CertificateRepository
CertificateRepository -->> CertificateService : #32; 
deactivate CertificateRepository

CertificateService ->> ResponseMappings : Mapping of Page of CertificateDB to Page<CertificateDto>
activate ResponseMappings
ResponseMappings -->> CertificateService : #32;
deactivate ResponseMappings
end

alt Triggers method if BPN insert starts with BPNS
CertificateService ->> CertificateMapping : findByEnclosedSitesSiteBpnAndTypeCertificateType [If no Certificate is found by bpn, shows CertificateNotExists exception]
activate CertificateMapping
CertificateMapping -->> CertificateService : #32;
deactivate CertificateMapping

CertificateService ->> ResponseMappings : Mapping of Page of CertificateDB to Page<CertificateDto>
activate ResponseMappings
ResponseMappings -->> CertificateService : #32;
deactivate ResponseMappings
end

Note over CertificateService: If inserted BPN format is diferent of BPNL or BPNS, <br>InvalidBpnFormatException is shown

CertificateService -->> CertificateController : Return PageDto<CertificateResponseDto>; 
deactivate CertificateService
CertificateController -->> User : Return PageDto<CertificateResponseDto>; 
deactivate CertificateController

    Note over User, CertificateController: Response: 200 OK 
    Note over User, CertificateController: Content-Type: application/json


````