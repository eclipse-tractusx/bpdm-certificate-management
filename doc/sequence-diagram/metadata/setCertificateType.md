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