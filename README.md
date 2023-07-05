## About RestlessFramework
A lightweight Java WEB (Java 7, JSF 2.2, PrimeFaces 10, Bootstrap 3) template application for starting a project.
It was named after it's developer, RestlessDevil (inspired by a Serbian turbo folk song) and it has nothing to do with REST API architecture.

## Getting started
Clone this project from Restless Devil's GitHub and import it into your NetBeans or build it using Maven (developed and tested using 3.6).

```
cd RePol
git clone https://github.com/RestlessDevil/RestlessFramework
cd RestlessFramework
mvn package
```

## Features
1. Dashboard (ApplicationScoped) bean which initializes all mission-critical components and shows which one crashed and what Exception it threw. Can also be used for configuration reload without needing to restart the servlet container.
1. JDBC Connection Pool singleton class (thread-safe)
2. Settings class that loads all properties-based configuration files and enables overriding them with external properties files (comes handy when the same WAR file needs to be deployed on multiple servers)
3. Simple but effective Cache for caching objects that take time to build
