````mermaid

sequenceDiagram
actor User

    autonumber
    Note over User, CertificateController: Path: api/catena/certificate/{bpn}/{certificateType}
    Note over User, CertificateController: Method: GET

User ->> CertificateController : checkCertificateByBpnAndType(bpn, certificateType)
activate CertificateController
CertificateController ->> CertificateService : checkCertificateByBpnAndType(bpn, certificateType)
activate CertificateService

CertificateRepository -->> CertificateService : findByCertificateType(certificateType); 
Note over CertificateService: If query done to database does not return a certificate by type, <br>CertificateTypeNotExists is thrown

alt Triggers method if BPN insert starts with BPNL (findCertificateByBusinessPartnerNumber)
CertificateRepository -->> CertificateService : findByBusinessPartnerNumber(bpn) [If no Certificate is found by bpn, shows CertificateNotExists exception]

alt Triggers method createCertifiedCertificateResponse(certificates, bpn, certificateType)
    note over CertificateRepository, CertificateService: Filter List<CertificateDB> retrieved earlier by type and bpn inserted
    
    alt
    CertificateService ->> CertificateMapping : If filtered List<CertificateDB> insn't empty, Mapping of CertificateDB to BpnCertifiedCertificateResponse <br> with isCertified assigned to true
    end

    alt
    CertificateService ->> CertificateMapping : If filtered List<CertificateDB> is empty, create listOf(BpnCertifiedCertificateResponse(bpn, false)) <br> with isCertified assigned to false
    end

end
end

alt Triggers method if BPN insert starts with BPNS (findCertificateByEnclosedSitesSiteBpn)
CertificateRepository -->> CertificateService : findByBusinessPartnerNumber(bpn) [If no Certificate is found by bpn, shows CertificateNotExists exception]

alt Triggers method createCertifiedCertificateResponse(certificates, bpn, certificateType)
    note over CertificateRepository, CertificateService: Filter List<CertificateDB> retrieved earlier by type and bpn inserted
    
    alt
    CertificateService ->> CertificateMapping : If filtered List<CertificateDB> insn't empty, Mapping of CertificateDB to BpnCertifiedCertificateResponse <br> with isCertified assigned to true
    end

    alt
    CertificateService ->> CertificateMapping : If filtered List<CertificateDB> is empty, create listOf(BpnCertifiedCertificateResponse(bpn, false)) <br> with isCertified assigned to false
    end

end
end

Note over CertificateService: If inserted BPN format is diferent of BPNL or BPNS, <br>InvalidBpnFormatException is shown

CertificateService -->> CertificateController : Return List<BpnCertifiedCertificateResponse>; 
deactivate CertificateService
CertificateController -->> User : Return List<BpnCertifiedCertificateResponse>; 
deactivate CertificateController

    Note over User, CertificateController: Response: 200 OK 
    Note over User, CertificateController: Content-Type: application/json


````