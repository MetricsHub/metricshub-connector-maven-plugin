![GitHub release (with filter)](https://img.shields.io/github/v/release/metricshub/metricshub-connector-maven-plugin)
![Build](https://img.shields.io/github/actions/workflow/status/metricshub/metricshub-connector-maven-plugin/deploy.yml)
![GitHub top language](https://img.shields.io/github/languages/top/metricshub/metricshub-connector-maven-plugin)
![License](https://img.shields.io/github/license/metricshub/metricshub-connector-maven-plugin)

# MetricsHub Connector Maven Plugin

This is a Maven Report plugin, which is invoked by Maven's site plugin in the `site` lifecycle.

This plugin is designed to be used with the [MetricsHub Community Connectors](https://github.com/metricshub/community-connectors).

It reads the connector files from a source directory (E.g. `./src/main/connector`), parses the `.yaml` files and produces the corresponding Reference Guide, as a set of HTML documents (through Doxia's Sink API), which is integrated into the project's documentation.

See **[Project Documentation](https://metricshub.org/metricshub-connector-maven-plugin)** for more information on how to use this plugin in your maven project.

## Build instructions

This is a simple Maven Plugin project. Build with:

```bash
mvn verify
```

## Integration Tests

While modifying the *MetricsHub Connector Maven Plugin*, you will want to see how your changes are reflected in a *test* documentation project.

Conveniently, the project comes with integration tests, i.e. a documentation project that is automatically built with the skin as it is in the workspace.
The integration test is run with the below command:

```bash
mvn verify
```

This command builds the skin and run it against a documentation project. The result can be seen in `./metricshub-connector-maven-plugin/target/it/metricshub-connectors/site/*.html`.

We recommend running [http-server](https://github.com/http-party/http-server#readme) to browse the result. Install with:

```bash
npm install --global http-server
```

Launch a Web server with the generated test documentation with:

```bash
http-server metricshub-connector-maven-plugin/target/it/metricshub-connectors/target/site
```

In case of a build failure, the output of the build is stored in `./metricshub-connector-maven-plugin/target/it/metricshub-connectors/build.log`.

## Release instructions

The artifact is deployed to the MetricsHub Maven repository:

* Releases: https://repo.metricshub.com/repository/maven-oss-releases/
* Snapshots: https://repo.metricshub.com/repository/maven-oss-snapshots/

Both use the server Id `metricshub`, and require credentials to deploy artifacts manually.

But it is strongly recommended to only use [GitHub Actions "Release to repo.metricshub.com"](actions/workflows/release.yml) to perform a release:

* Manually trigger the "Release" workflow
* Specify the version being released and the next version number (SNAPSHOT)
* Merge the PR that has been created to prepare the next version

The release is published immediately: there is no staging repository to release. A version that has been partially deployed can't be deployed again: delete its components on https://repo.metricshub.com before restarting the workflow, or release the next version instead.

## License

License is Apache-2. Each source file must include the Apache-2 header (build will fail otherwise).
To update source files with the proper header, simply execute the below command:

```bash
mvn license:update-file-header
```