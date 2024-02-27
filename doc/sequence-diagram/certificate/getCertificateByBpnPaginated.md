````mermaid

sequenceDiagram
actor User

    autonumber
    Note over User, CertificateController: Path: api/catena/certificate/{bpn}
    Note over User, CertificateController: Method: GET

User ->> CertificateController : getCertificatesByBpnPaginated(bpn, pageRequest)
activate CertificateController
CertificateController ->> CertificateService : getCertificatesByBpn(bpn, pageRequest)
activate CertificateService

alt Triggers method if BPN insert starts with BPNL
CertificateService ->> CertificateRepository : findByBusinessPartnerNumber [If no Certificate is found by bpn, shows CertificateNotExists exception]
activate CertificateRepository
CertificateRepository -->> CertificateService : #32; 
deactivate CertificateRepository

CertificateService ->> ResponseMappings : Mapping of Page of CertificateDB to Page<CertificateDto>
activate ResponseMappings
ResponseMappings -->> CertificateService : #32;
deactivate ResponseMappings
end

alt Triggers method if BPN insert starts with BPNS
CertificateService ->> CertificateMapping : findByEnclosedSitesSiteBpn [If no Certificate is found by bpn, shows CertificateNotExists exception]
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