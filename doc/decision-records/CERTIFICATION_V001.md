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
# CX - 0135 - BP company certificate management v.1.0.0

## TABLE OF CONTENTS

- [CX - 0135 - BP company certificate management v.1.0.0](#cx---0135---bp-company-certificate-management-v100)
  - [TABLE OF CONTENTS](#table-of-contents)
  - [ABSTRACT](#abstract)
  - [FOR WHOM IS THE STANDARD DESIGNED](#for-whom-is-the-standard-designed)
  - [COMPARISON WITH THE PREVIOUS VERSION OF THE STANDARD](#comparison-with-the-previous-version-of-the-standard)
  - [1 INTRODUCTION](#1-introduction)
    - [1.1 AUDIENCE \& SCOPE](#11-audience--scope)
    - [1.2 CONTEXT](#12-context)
    - [1.3 CONFORMANCE](#13-conformance)
    - [1.4 EXAMPLES](#14-examples)
    - [1.5 TERMINOLOGY](#15-terminology)
      - [1.5.1 BPNL BUSINESS PARTNER NUMBER LEGAL ENTITY](#151-business-partner-number--bpnl---legal-entity)
      - [1.5.2 TYPE](#152-type)
      - [1.5.3 REGISTRATION AND ISSUING](#153-registration-and-issuing)
      - [1.5.4 AREA OF APPLICATION](#154-area-of-application)
      - [1.5.5 ENCLOSED SITES](#155-enclosed-sites)
      - [1.5.6 VALIDITY](#156-validity)
      - [1.5.7 TRUST LEVEL](#157-trust-level)
      - [1.5.8 VALIDATOR](#158-validator)
      - [1.5.9 SOURCE](#159-source)
      - [1.5.10 ID](#1510-id)
    - [1.6 INTERFACE](#16-interface)
      - [1.6.1 META DATA CONTROLLER](#161-meta-data-controller)
      - [1.6.2 PROVIDE A CERTIFICATE](#162-provide-a-certificate)
      - [1.6.2 REQUEST CERTIFICATE INFORMATION](#162-request-certificate-information)
  - [2 MAIN CONTENT](#2-main-content)
    - [2.1. DATA MODEL COMPANY CERTIFICATES](#21-data-model-company-certificates)
    - [2.2. COMPANY CERTIFICATE INTERFACE ENDPOINTS](#22-company-certificate-interface-endpoints)
    - [2.2 OUT OF SCOPE](#22-out-of-scope)
  - [3 REFERENCES](#3-references)
    - [3.1 NORMATIVE REFERENCES](#31-normative-references)
    - [3.2 NON-NORMATIVE REFERENCES](#32-non-normative-references)
    - [3.3 REFERENCE IMPLEMENTATIONS](#33-reference-implementations)
  - [ANNEXES](#annexes)
    - [FIGURES](#figures)
    - [TABLES](#tables)
    - [ABOUT THIS DOCUMENT \& MOTIVATION](#about-this-document--motivation)
    - [DISCLAIMER \& LIABILITY](#disclaimer--liability)
    - [REVISIONS \& UPDATE](#revisions--update)
    - [COPYRIGHT \& TRADEMARKS](#copyright--trademarks)

## ABSTRACT

*This section is non-normative.*

In the world of business, company certificates are often mandatory for conducting transactions between two companies. However, the process of provisioning, maintaining, and validating these certificates can be a major challenge. For example, if a company has 100 customers, they may need to provide their company certificates in 100 different ways and maintain them at 100 different points.

To address this issue, a use case has been developed that provides a standardized but generic data model for company certificates. This allows companies to provide and exchange certificates on a defined standard within the scope of the Catena-X dataspace. The second part of this use case focuses on the technological aspect of providing and validating certificates via an interface.

This standard is relevant for business application providers who wish to establish a solution for managing and validating company certificates, and returning them to customers. It is also important for data providers and consumers who need to exchange or validate certificates through a solution provider.

## FOR WHOM IS THE STANDARD DESIGNED

See chapter 1.1 Audience & Scope

## COMPARISON WITH THE PREVIOUS VERSION OF THE STANDARD

Not applicable - this is the first published version.

## 1 INTRODUCTION

### 1.1 AUDIENCE & SCOPE

*This section is non-normative.*

This standard is relevant for the following audience:

- Business Application Provider
- Data Provider and Consumer

This standard applies to business application providers who wish to establish a solution for managing and validating company certificates, and returning them to customers. It is also important for data providers and consumers who need to exchange or validate certificates through a solution provider.

The data model for company certificates included in this standard is generic and supports the following certificates: IATF 16949, ISO 14001, ISO 9001, ISO 27001, and VDA6.4.

Additional certificates may be added in the future.

You can find the other standards in the standard library of Catena-X: <https://catena-x.net/de/standard-library>.

### 1.2 CONTEXT

*This section is non-normative.*

The establishment of various industry networks, such as Catena-X, has significantly increased the need for data standards across the entire automotive value chain. To promote industry-wide, international data exchange and facilitate networking between OEMs, suppliers, customers, and industrial partners, it is essential to define and introduce a cross-industry standard for the provisioning, exchanging, maintenance, and validation of company certificates. This standard ensures interoperability and data sovereignty, while also increasing trust in certificates.

By implementing this standard, companies can streamline the process of managing and exchanging certificates, reducing the burden of maintaining multiple certificates for different customers. Additionally, the standard ensures that certificates are validated and exchanged in a secure and reliable manner, enhancing trust and confidence in the data exchange process. Overall, the introduction of a cross-industry standard for company certificates is a crucial step towards achieving seamless and secure data exchange across the automotive industry.

### 1.3 CONFORMANCE

As well as sections marked as non-normative, all authoring guidelines, diagrams, examples, and notes in this specification are non-normative. Everything else in this specification is normative.

The key words MAY, MUST, MUST NOT, OPTIONAL, RECOMMENDED, REQUIRED, SHOULD and SHOULD NOT in this document are to be interpreted as described in [BCP 14](https://datatracker.ietf.org/doc/html/bcp14) [[RFC2119](https://www.w3.org/TR/did-core/#bib-rfc2119 "Key words for use in RFCs to Indicate Requirement Levels")] [[RFC8174](https://www.w3.org/TR/did-core/#bib-rfc8174 "Ambiguity of Uppercase vs Lowercase in RFC 2119 Key Words")] when, and only when, they appear in all capitals, as shown here.

*This section is non-normative.*

All participants and their solutions will need to proof, that they are conform with the Catena-X standards. To validate that the standards are applied correctly, Catena-X employs Conformity Assessment Bodies (CABs).

### 1.4 EXAMPLES

A company that has 100 customers, they may need to provide their company certificates in up to 100 different ways and maintain them manually at 100 different points (typically proprietary customer portals).
A company has 100 customers, that provide different certificates, from different countries and they need to validate them.

### 1.5 TERMINOLOGY

*This section is non-normative.*

In this section the different parts of the data model are explained.

The data model follow the semantic data model that is published in the GitHub repository:
<https://github.com/eclipse-tractusx/sldt-semantic-models>

#### 1.5.1 BUSINESS PARTNER NUMBER : BPNL - LEGAL ENTITY

A BPNL represents and uniquely identifies a Legal Entity, which is defined by its legal name (including Legal Form, if registered), legal Address and Tax Number. For further details on BPNLs please see standard 0010 Business Partner Number.

For this standard and the data model the BPNL is the BPN id of the certified legal entity.

Attribute: businessPartnerNumber

#### 1.5.2 TYPE

Attribute: certitificateType</br>
Attribute: certificateVersion

Is the type of the certificate the BPN is certified for. This data model is generic and supports the following certificates: IATF, ISO 16949, ISO 14001, ISO 9001, ISO 27001, and VDA6.4. Additional certificates may be added in the future. And the version of a certificate e.g. 2015 (for IS09001:2015).

#### 1.5.3 REGISTRATION AND ISSUING

Attribute: issuer</br>
Attribute: registrationNumber

Issuer authority is the one who is handing out a certificate - e.g. TUEV Sued. The registration number is the certificate unique identifier at the certifying authority / issuing agency.

Example: ISO 9001 certificate is issued by TÜV Süd and they are the certifying authority.

#### 1.5.4 AREA OF APPLICATION

Attribute: areaOfApplication

It is the area of applications for the given certification i.e. additional details.

#### 1.5.5 ENCLOSED SITES

Attribute: enclosedSites

This attribute is closely linked to the business partner number (BPN) and indicates additional sites, such as production or engineering sites, that are covered by the certificate. In other words, the certificate is valid not only for the primary BPN, but also for any associated sites. This attribute is particularly useful for companies with multiple locations or business units, as it allows them to manage certificates more efficiently and ensures that all relevant sites are covered by the certificate.

#### 1.5.6 VALIDITY

Attribute: validFrom</br>
Attribute: validUntil

This is the valid from date for certificate, if not defined - its recommended to use the issueing/signing date of the document. Related to the valid from date there is the valid to date for a certificate -  31.12.9999 for no expiration.

#### 1.5.7 TRUST LEVEL

Attribute: trustLevel

Data object defining the trust level of the certicate.

The certificates are provided in the business context by the company itself - they are showing their certificates to other companies. Not every certificate can be directly validated by the issuing authority. Thats why there are different trust levels defined: none/ low /high / trusted.

None: no validation check at all, just uploaded / provided by the company
Low: manual validation check done by human after upload
Medium: certificate provided by trusted issuer and manually checked (as low)
High: automated cross check via some database (e.g. TÜV, IATF)
Trusted: directly provided by issuer (e.g. TÜV)

#### 1.5.8 VALIDATOR

Attribute: validator

Validator is the one who can validate certificate information. In the best way it is the authority that is issuing the certificates but there can be other validators. This attribute has a relation to the trust level.

E.g. Business service providers that offer a validation service for company certificates.

#### 1.5.9 SOURCE

Attribute: source

This defines the company who orginally provided the given certificate (e.g. Company A provided it to Business service provider B, Business service provider B is a trusted validator). This company is also identified by a BPN.

#### 1.5.10 ID

Attribute: documentID

The internal reference id to request a certificate document.

### 1.6 INTERFACE

*This section is non-normative.*

In this section the interface / API for the data model is explained. The interface is a main factor for interoperability.

#### 1.6.1 META DATA CONTROLLER

For developers implementing the business partner certificate API, it is essential to have access to a list of registered certificate types. This information is necessary to ensure correct spelling and to determine which certificate types are available for use with other API endpoints in this use case. To obtain this information, developers can make an API call to the MetaDataController, using the following endpoint: /api/catena/certificate-types. This call returns a list of all currently registered certificate types.

In addition to accessing the list of registered certificate types, developers may also need to register a new certificate type. This is necessary when providing new certificates for which the type is not yet known. To register a new certificate type, developers can make an API call to the MetaDataController using the following endpoint (POST): /api/catena/create-certificate-type. This call allows developers to add a new certificate type to the list of registered types, ensuring that it is available for use with other API endpoints in this use case.

#### 1.6.2 PROVIDE A CERTIFICATE

In the context of data exchange within the Catena-X dataspace, companies may need to provide their own certificates or the certificates of their partners to other participants. To facilitate this process, a specific API call is available.

Using the following endpoint (POST): api/catena/certificate/document, companies can provide a certificate document for a legal entity, including all sites assigned to the certificate. This API call allows companies to securely and efficiently share their certificates with other participants in the dataspace, ensuring that all relevant information is included in the document. By providing this API call, the Catena-X dataspace enables seamless and secure data exchange between companies, promoting interoperability and trust in the data exchange process.

#### 1.6.2 REQUEST CERTIFICATE INFORMATION

To validate a certificate provided by a company, users can make use of the Catena-X dataspace API. The API provides a range of trust levels, which indicate the level of validation that has been performed on the certificate. These levels include None (no validation check at all), Low (manual validation check done by a human after upload), Medium (certificate provided by a trusted issuer and manually checked), High (automated cross-check via a database, such as TÜV), and Trusted (directly provided by the issuer, such as TÜV).

To access this information, users can make an API call to the following endpoint: /api/catena/certificate/simple/{bpn}/{certificateType} . This call returns a true/false value indicating whether the certificate is valid, as well as the certification valid-until date and the trust level.

In addition to validating certificates, users may also need to request specific certificate information based on the BPN L/S/A. To do this, they can make an API call to the following endpoint: /api/catena/certificate/{bpn} this call returns the requested certificate for a legal entity, including all sites assigned to the certificate. By providing these API calls, the Catena-X dataspace enables users to easily verify that a business partner has the necessary certification, promoting trust and confidence in the data exchange process.

To specify this call - its possible to add the certificate type to get a certicate for a given certificate type: /api/catena/certificate/{bpn}/{certificateType}.

The following endpoint: /api/catena/certificate/document/ {cdID} returns requested certificate document for a legal entity including all sites assigned to a certificate - with the ID the direct and unique document can be requested and found.

## 2 MAIN CONTENT

The following rules are minimum requirements and may be supplemented.

### 2.1. DATA MODEL COMPANY CERTIFICATES

The following attributes that are described in detail in the terminology chapter MUST be provided when participating in the use case of company certificates:

- businessPartnerNumber with the datatype BPNL
- type with the datatype CertificateType
- registrationNumber as a string datatype
- validFrom with the datatype date
- validUntil with the datatype date
- trustLevel with the datatype TrustLevel
- documentID

There are attributes that are OPTIONAL:

- areaOfApplication with the datatype string. This is just relevant when there need to be additional details added to a certificate.
- enclosedSites with the datatype List of EcnlosedSites. This becomes a MUST attribute when the certificate is not only relevant for the legal entity but also for one site or more.
- issuer with the datatype BPN. Would be nice to know who issued the certificate but not mandatory.
- validator with the datatype TrustValidator. Typically validates certificate information so that it can be trusted (see also trust levels).

The data model follow the semantic data model that is published in the GitHub repository:
<https://github.com/eclipse-tractusx/sldt-semantic-models>

### 2.2. COMPANY CERTIFICATE INTERFACE ENDPOINTS

The following GET and POST endpoints are MUST - especially for business service providers:

GET endpoints:

/api/catena/certificate/{bpn} - Get call certificates of a given BPN.
/api/catena/certificate/{bpn}/{certificateType} - Get a specific certificates by certicate type for a given BPN.
/api/catena/certificate/simple/{bpn}/{certificateType} -  This endpoint checks whether a provided BPN is certified for a specific certificate type.
/api/catena/certificate/document/{cdID} - Request a specific / unique certicate document for a given BPN.
/api/catena/certificate-types - Get a list of all currently registered certificate types.

POST endpoints:

/api/catena/certificate/document - Provide a specific certicate document for a given BPN.
/api/catena/certificate-types - Register a new certificate type.

### 2.2 OUT OF SCOPE

This standardization document does not describe the process and functionality of the lifecycle-management of certificates.  

## 3 REFERENCES

### 3.1 NORMATIVE REFERENCES

Intentionally left blank.

### 3.2 NON-NORMATIVE REFERENCES

*This section is non-normative.*

- [BPDM Catena-X Website](https://catena-x.net/en/offers/bpdm)
- CX – 0010 Business Partner Number

### 3.3 REFERENCE IMPLEMENTATIONS

*This section is non-normative.*

## ANNEXES

### FIGURES

*This section is non-normative.*

Intentionally left blank.

### TABLES

*This section is non-normative.*

Intentionally left blank.

### ABOUT THIS DOCUMENT & MOTIVATION

Catena-X is the first open and collaborative data ecosystem. The goal is to provide an environment for the creation, operation, and joint use of end-to-end data chains along the entire automotive value chain. All partners are on an equal ground, have sovereign control over their data and no lock-in effects occur. This situation provides a sustainable solution for the digitalization of supply chains, especially for medium-sized and small companies, and supports the cooperation and collaboration of market participants and competitors.

The ever-growing Catena-X ecosystem will enable enormous amounts of data to be integrated and collaboratively harnessed. To ensure that these complex data volumes can be sent, received, and processed smoothly across all stages of the value chain, one language for all players is required: common standards.

The standards of the Catena-X data ecosystem define how the exchange of data and information in our network works. They are the basis for ensuring that the technologies, components, and processes used are developed and operated according to uniform rules.  

Common standards create added value for all partners: Within our network, data flows more smoothly through interfaces. In addition, we avoid cumbersome individual IT solutions for sharing data with other partners.  In the field of international standardization, Catena-X follows the proven international standardization institutions: ISO/IEC/ITU and CEN-CENELC/ETSI.

For users and data providers, implementation of standards will reduce the costs that would arise from adapting different systems. In addition, no important data is lost. On the contrary, it even becomes easier to collect data across companies. For operators and developers, standards will create a framework that provides reliable orientation and planning security.

The following document describes the Golden Record and its specific data quality requirements to build a trusted source of business partner data. In this case, it is the primary resource for explaining the required input and expected output of the Golden Record.

### DISCLAIMER & LIABILITY

The present document and its contents are provided “AS-IS” with no warranties whatsoever.

The information contained in this document is believed to be accurate and complete as of the date of publication, but may contain errors, mistakes, or omissions.

The Catena-X Automotive Network e.V. (“Catena-X”) makes no express or implied warranty with respect to the present document and its contents, including any warranty of title, ownership, merchantability, or fitness for a particular purpose or use. In particular, Catena-X does not make any representation or warranty, and does not assume any liability, that the contents of the document or their use (i) are technically accurate or sufficient, (ii) conform to any law, regulation and/or regulatory requirement, or (iii) do not infringe third-party intellectual property or other rights.

No investigation regarding the essentiality of any patents or other intellectual property rights has been carried out by Catena-X or its members, and Catena-X does not make any representation or warranty, and does not assume any liability, as to the non-infringement of any intellectual property rights which are, or may be, or may become, essential to the use of the present document or its contents.

Catena-X and its members are subject to the IP Regulations of the Association Catena-X Automotive Network e.V. which govern the handling of intellectual property rights in relation to the creation, exploitation and publication of technical documentation, specifications, and standards by Catena-X.[^4]

Neither Catena-X nor any of its members will be liable for any errors or omissions in this document, or for any damages resulting from use of the document or its contents, or reliance on its accuracy or completeness. In no event shall Catena-X or any of its members be held liable for any indirect, incidental, or consequential damages, including loss of profits. Any liability of Catena-X or any of its members, including liability for any intellectual property rights or for non-compliance with laws or regulations, relating to the use of the document or its contents, is expressly disclaimed.

### REVISIONS & UPDATE

The present document may be subject to revision or change of status. Catena-X reserves the right to adopt any changes or updates to the present document as it deems necessary or appropriate.[^5]

The present document may be made available in electronic versions and/or in print. The content of any electronic and/or print versions of the present document shall not be copied or modified without the prior written authorization of Catena-X. In case of any existing or perceived difference in contents between any versions and/or in print, the prevailing version of the present document is the one made publicly available by Catena-X in PDF format.

If you find any errors in the present document, please send your comments to: <standardisierung@catena-x.net>

### COPYRIGHT & TRADEMARKS

Any and all rights to the present document or parts of it, including but not limited under copyright law, are owned by Catena-X and its licensors.

The contents of this document shall not be copied, modified, distributed, displayed, made publicly available or otherwise be publicly communicated, in whole or in part, for any purposes, without the prior authorization by Catena-X, and nothing herein confers any right or license to do so.

The present document may include trademarks or trade names which are registered by their owners. Catena-X claims no ownership of these except for any which are indicated as being the property of Catena-X and conveys no right to use or reproduce any such trademark or trade name contained herein. Mention of any third-party trademarks in the present document does not constitute an endorsement by Catena-X of products, services or organizations associated with those trademarks.

“CATENA-X” is a trademark owned by Catena-X registered for its benefit and the benefit of its members. Using or reproducing this trademark or the trade name of Catena-X is expressly prohibited.

No express or implied license to any intellectual property rights in the present document or parts thereof, or relating to the use of its contents, or mentioned in the present document is granted herein.

The copyright and the foregoing restrictions extend to reproduction in all media.

© Catena-X Automotive Network e.V. All rights reserved.

[^4]: <https://catena-x.net/fileadmin/user_upload/Vereinsdokumente/Catena-X_IP_Regelwerk_IP_Regulations.pdf> </br>
[^5]: <https://catena-x.net/de/standardisierung/catena-x-einfuehren-umsetzen/standardisierung/standard-library>
