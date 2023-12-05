# Bpdm Certificate Management Helm chart

This Helm Chart deploys the BPDM Certificate Management application to a Kubernetes environment.

## Prerequisites

* [Kubernetes Cluster](https://kubernetes.io)
* [Helm](https://helm.sh/docs/)

In an existing Kubernetes cluster the application can be deployed with the following command:

```bash
helm install release_name ./charts/bpdm-certificate-management --namespace your_namespace -f /path/to/my_release-values.yaml
```

This will install a new release of the BPDM Certificate Management service in the given namespace.
On default values, this release deploys the image tagged with the Chart's appversion from [Dockerhub](https://hub.docker.com/r/tractusx/bpdm-certificate-management).

By giving your own values file you can configure the Helm deployment of the BPDM Certificate Management service freely.
In the following sections you can have a look at the most important configuration options.

## Image Tag

Per default, the Helm deployment references the image tagged with the Chart's appversion from [Dockerhub](https://hub.docker.com/r/tractusx/bpdm-certificate-management).
This tag follows the latest version of the BPDM Certificate Management service and contains the newest features and bug fixes.
If you might want to switch to latest release tag instead for your deployment in order to follow recent releases.
In your values file you can overwrite the default tag:

```yaml
image:
  tag: "latest"
```
