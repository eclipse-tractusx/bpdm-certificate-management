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