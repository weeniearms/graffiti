graffiti
========

[![Build Status](https://travis-ci.org/weeniearms/graffiti.svg?branch=master)](https://travis-ci.org/weeniearms/graffiti)

Web app for generating diagrams from [PlantUML](http://plantuml.sourceforge.net) and [DOT](https://en.wikipedia.org/wiki/DOT_(graph_description_language)) syntax provided as query string, so that it can be embedded in github/gitlab markdown files. Inspired by [gravizo](http://gravizo.com/) (which offers a lot more but requires you to send potentially confidential information to a 3rd party).

### Usage

#### Running the app

Prior to running the app, make sure that you have graphviz installed.

Run as any other spring-boot fat jar:
```
java -jar graffiti-0.0.1-SNAPSHOT.jar
```

##### Configuration

The following configuration can be provided either by using an application.properties/application.yml file or by system/env variables:
- `graphviz.location` - the location of the dot executable (defaults to /usr/bin/dot)
- `graphviz.timeout` - timeout (in ms) on waiting for the dot process to finish (defaults to 2000)
- `cache.time-to-live` - time to live (in ms) for cached graphs (defaults to 1800000 = 30 minutes)
- `cache.max-weight` - maximum weight of cache in bytes (defaults to 104857600 = 100MB)

#### Embedding graphs in markdown

Regardless of the chosen output format, you need to add a trailing `;` to each line of the graph source in order to make it compatible with graffiti.

The below snippets might not work with all markdown processors - only the ones that will properly encode the query string are supported (otherwise, you'd have to provide an encoded string yourself, which decreases readability and usability).

Svg:
```
![Alt text](https://your_host/svg?
@startuml;
DataAccess - [First Component];
[First Component] ..> HTTP : use;
@enduml
)
```

Png:
```
![Alt text](https://your_host/png?
@startuml;
DataAccess - [First Component];
[First Component] ..> HTTP : use;
@enduml
)
```

## TODO

- Create a simple UI for testing your source strings