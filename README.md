# Bpdm-Certificate-Management

## Project Description

This repository is part of the overarching Eclipse Tractus-X project.

BPDM Certificate Management here BPDM is an acronym for business partner data management.
This project provides core services for querying and adding certificate data for business partner base information in the Eclipse Tractus-X landscape.

## Installation

For installation instructions for the Bpdm Certificate Management applications please refer to the [INSTALL](INSTALL.md) file.

## License Check

Licenses of all maven dependencies need to be approved by eclipse.
The Eclipse Dash License Tool can be used to check the license approval status of dependencies and to request reviews by the intellectual property team.

Generate summary of dependencies and their approval status:

```bash
mvn org.eclipse.dash:license-tool-plugin:license-check -Ddash.summary=DEPENDENCIES
```

Automatically create IP Team review requests:

```bash
mvn org.eclipse.dash:license-tool-plugin:license-check -Ddash.iplab.token=<token>
```

Check the [Eclipse Dash License Tool documentation](https://github.com/eclipse/dash-licenses) for more detailed information.

