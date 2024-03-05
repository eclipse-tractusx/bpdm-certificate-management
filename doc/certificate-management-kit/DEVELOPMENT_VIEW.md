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
# Development View

## API Reference

The Certificate Management contains a variety of different APIs, and with the help of Swagger UI interface, which is an interactive tool for exploring and testing RESTful APIs, those can easily be used.
Swagger UI provides a user-friendly interface for interacting with APIs, allowing you to make requests, view responses, and explore different API endpoints and operations,
allowing you to drill down into specific endpoints and view the associated request parameters, response schemas, and sample responses.

The Certificate Management includes core services for querying and adding certificate data for business partner base information in the Eclipse Tractus-X landscape.
In the following sections, the available methods and different controllers of the API are explained.
More Information to the open-source repository can be found on [GitHub](https://github.com/eclipse-tractusx/bpdm-certificate-management)

## Certificate Management Api references

### Available Methods

| Request Method | Request Description |
| :----------- |:---|
| POST| Submit data to be processed |
| GET | Retrieve data searching for the best match |

### 1. Certificate Controller

The Certificate Controller is the controller that creates, and retrieves various certificates via BPN, Type or DocumentID. The POST and GET endpoints  
available under Certificate controller are clustered in table below. Hyperlink in each record gives you more information about api endpoint.

| Certificate Controller Apis |
|:---|
| [Provide a specific certificate document for a given BPN](https://app.swaggerhub.com/apis/eclipse-tractusx-bot/bpdm-certificate-management/0.0.4-SNAPSHOT#/certificate-controller/setCertificateDocument) |
| [Get all certificates of a given BPN](https://app.swaggerhub.com/apis/eclipse-tractusx-bot/bpdm-certificate-management/0.0.4-SNAPSHOT#/certificate-controller/getCertificatesByBpnPaginated) |
| [Get a specific certificate by certificate type and a given BPN](https://app.swaggerhub.com/apis/eclipse-tractusx-bot/bpdm-certificate-management/0.0.4-SNAPSHOT#/certificate-controller/getCertificateByTypeAndBpnPaginated) |
| [Gets info whether BPN is certified for a specific certificate type](https://app.swaggerhub.com/apis/eclipse-tractusx-bot/bpdm-certificate-management/0.0.4-SNAPSHOT#/certificate-controller/checkCertificateByBpnAndType) |
| [Request certificate document for given document id](https://app.swaggerhub.com/apis/eclipse-tractusx-bot/bpdm-certificate-management/0.0.4-SNAPSHOT#/certificate-controller/retrieveCertificateDocument) |

### 2. Metadata Controller

The Metadata Controller is mainly used for registering and retrieving certificate types. There is one GET and a POST.
These are available under Metadata controller and are clustered in table below. Hyperlink in each record gives you more information about api endpoint.

| Metadata Controller Apis |  
| :--- |
| [Register a new certificate type](https://app.swaggerhub.com/apis/eclipse-tractusx-bot/bpdm-certificate-management/0.0.4-SNAPSHOT#/metadata-controller/setCertificateType) |
| [Get a list of all currently registered certificate types](https://app.swaggerhub.com/apis/eclipse-tractusx-bot/bpdm-certificate-management/0.0.4-SNAPSHOT#/metadata-controller/getCertificateTypes) |
