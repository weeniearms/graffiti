graffiti
========

[![Build Status](https://travis-ci.org/weeniearms/graffiti.svg?branch=master)](https://travis-ci.org/weeniearms/graffiti)

Web app for generating diagrams from [PlantUML](http://plantuml.sourceforge.net) syntax provided as query string, so that it can be embedded in github/gitlab markdown files. Inspired by [gravizo](http://gravizo.com/) (which offers a lot more but requires you to send potentially confidential information to a 3rd party).

### Usage

#### Running the app

Prior to running the app, make sure that you have graphviz installed.

Run as any other spring-boot fat jar:
```
java -jar graffiti-0.0.1-SNAPSHOT.jar
```

#### Embedding graphs in markdown

Regardless of the chosen output format, you need to add a trailing `;` to the graph source in order to make it compatible with graffiti.

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

- Add [DOT](http://en.wikipedia.org/wiki/DOT_(graph_description_language)) graph support
- Create a simple UI for testing your source strings