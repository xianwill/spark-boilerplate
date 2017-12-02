# Spark Boilerplate

This is a boilerplate to bootstrap a spark project quickly.

It includes:

* unit and integration test config
* runnable docker setup
* runnable EMR scripts
* several helpers you will likely find useful
* connectors for common external data sources (elasticsearch, cassandra, postgres, kafka)
  * samples utilizing postgres and kafka are a work-in-progress and forthcoming

## Some Strings to Hunt/Change

* myapp
* MyApp
* MyType
* example

Make sure you change the value in the `NAME` file. That drives the app name applied by sbt to build the jar as well as the emr and docker scripts.

## Tooling

* "Always be `sbt`-ing." I like to run non-spark dependent helper functions in watch mode with `~test`.
* Ensime is recommended for completion, debugging, etc. Follow http://ensime.org/editors/sublime/ to get it configured both globally and for your project.
* I'm using Sublime at the moment. http://ensime.org/editors/sublime/installation/ explains how to get Ensime setup in Sublime.
* Aside from the general guidelines, the project comes with a lot of helpers I find useful...
  * Sample `scalatest` tests
  * bin scripts for launching an emr cluster
  * A docker-compose with a spark master, spark worker and a number of external data sources  
  * bin scripts for various docker interactions
    * spin up the resources described in docker compose
    * create external data source schemas
    * submit the assembly to the cluster
  * Various helpers for:
  	* loading CSV, JSON and resources (`CsvLoader` and `JsonLoader`, `StringLoader`)
  	* file path conventions (`PathHelper`)
  	* simple S3 operations such as downloading a file (`S3Helper`)
  	* issuing basic REST API requests to elastic search (`ElasticSearchHelper`)
  	* `SchemaGenerator` - a helper for truing up slightly divergent schemas that need to be read from the same dataset

If, like me, your system scala version is 2.12.0 or greater, you can:

```
cp ensime.sbt.template ensime.sbt
```

before generating your ensime config. Spark 2.2.0 does not yet support 2.12.0.

## Testing

I like to run watch mode with `~test` -- but, I don't like to wait for the spark session to reinitialize with every test. So, this project is configured with a separate test command for tests that depend on the spark session.

Name your integration specs with the suffix `IntegrationSpec`. Then, in an sbt console, run them with:

```
it:test
```

This is a nice Stack Overflow post on testing with a spark context btw - https://stackoverflow.com/questions/43729262/how-to-write-unit-tests-in-spark-2-0#answer-43769845.

## Packaging

The build is configured with the `assembly` plugin to build an uber jar suitable for deployment. Build the uber jar with:

```
sbt assembly
```

Then you can submit it to docker or deploy it to S3 with the helper scripts.

## Docker/Local Development

First of all - big shout out for the getty image (https://github.com/gettyimages/docker-spark) used in this project's `docker-compose.yml`. Its great.

Make your own `.env`

```
cp env.tempate .env
```

...then edit it.


`$DATA_ROOT` will be mounted to `/data` within the spark containers. `bin/docker-submit` sets `/data` as the `basePath` for the sample spark app. Passing in the base path like this is a convention I like to follow because it lets me easily switch my `basePath` to be S3 rooted so my move to EMR is seamless.

To launch a dockerized spark master and worker, as well as cassandra, elasticsearch, postgres and kafka from your spark project root - do this:

```
./bin/docker-up
```

Next, run

```
./bin/docker-schemas
```

so that the schemas required by the app will be created in cassandra.

if you don't need all those dependencies for your own project, prune them from the docker compose file and also remove the package dependencies from `Dependencies.scala` and `build.sbt` and related config from `package.scala`.

you can rebuild and submit your app at anytime with

```
sbt assembly

./bin/docker-submit
```

## EMR

Configure instance types, spot pricing and other EC2 and EMR properties in`props-emr`. Once you have all of your EMR properties configured, you can deploy the current version of your assembled jar to s3 with:

```
./bin/deploy-s3
```

Then launch an auto-terminating EMR cluster that executes your app as a step

```
./bin/emr-myapp
```

You can add more steps and more scripts by copy/pasting `bin/step-myapp` to `bin/step-yourapp` and adding additional `bin/emr-*` scripts. `emr-*` scripts can be configured to run multiple steps by adding additional `step-*` output to the `$DEF_STEP` array.

There is also a helper script to launch a general purpose EMR cluster called `emr-general`. Make sure you terminate the `emr-general` cluster yourself when you are done with it.

