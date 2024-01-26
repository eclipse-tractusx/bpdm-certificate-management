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