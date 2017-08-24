# Spark Boilerplate

This is a boilerplate to bootstrap a spark project quickly.

It includes:

* Runnable tests
* A runnable docker setup
* Runnable EMR scripts
* Several Helpers you will likely find useful

## Some Strings to Hunt/Change

* myapp
* MyApp
* MyType
* example

## Tooling

* Always be `sbt`-ing. I like to run non-spark dependent helper functions in watch mode with `~test`.
* Ensime is recommended for completion, debugging, etc. Follow http://ensime.org/editors/sublime/ to get it configured both globally and for your project.
* I'm using Sublime at the moment. http://ensime.org/editors/sublime/installation/ explains how to get Ensime setup in Sublime.
* Aside from the general guidelines, the project comes with a lot of helpers I find useful...
  * bin scripts for launching an emr cluster
  * Sample `scalatest` tests
  * A `docker-compose` file and a `Dockerfile` for end-to-end local testing
  * CSV and JSON resource loaders
  * A stubbed out project config with baked in path conventions
  * A schema generator

## Docker

Make your own `.env`

```
cp env.tempate .env
```

...then edit it.

To launch docker, from your spark project root - do this:

```
sbt assembly
./bin/docker-up
```

## EMR

* Configure instance types and spot pricing in `bin/props-emr`.
* Configure EC2 and EMR properties in props-emr

Deploy to s3 with:

```
sbt assembly
./bin/deploy-s3
```

Then launch an auto-terminating EMR cluster that executes your app as a step

```
./emr-myapp
```

You can add more steps and more scripts by copy/pasting `bin/step-myapp` to `bin/step-yourapp` and adding additional `emr-*` scripts

There is also a helper script to launch a general purpose EMR cluster called `emr-general`


