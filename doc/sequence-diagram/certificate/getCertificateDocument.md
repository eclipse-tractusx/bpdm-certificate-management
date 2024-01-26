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