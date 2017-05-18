---
title: "incompatibilities"
bg: red
color: white
fa-icon: exclamation-triangle
---

In order to render the diagrams properly, markdown processors need to be able properly encode the query string. Otherwise, you'd have to encode it yourself, making it less readable/maintainable.

The following markdown processors are known to work:
- gitlab
- github

There are also some minor syntax incompatibilities:
- Multiple empty lines are not supported when embedding in markdown
- Single line comments `//` won't work (but multi-line comments `/* */` in DOT diagrams should be fine)
- PlantUML syntax needs to be modified to include a trailing `;` on each line
