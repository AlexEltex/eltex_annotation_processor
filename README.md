*Read this in other languages: [Русский](README.ru.md)*

## Concisely

Implementation @MustOverride annotations to build the project through maven.

## Using

Add to pom.xml:

1. Add to plugins

```
<plugin>
    <groupId>org.bsc.maven</groupId>
    <artifactId>maven-processor-plugin</artifactId>
    <executions>
        <execution>
            <id>process</id>
            <goals>
                <goal>process</goal>
            </goals>
            <phase>compile</phase>
            <configuration>
                <processors>
                	<processor>net.eltex.aptprocessor.MustOverrideProcessor</processor>
                </processors>
            </configuration>
        </execution>
    </executions>
</plugin>
```

2. Add to dependencies

```
<dependency>
	<groupId>net.eltex</groupId>
	<artifactId>eltex_annotation_processor</artifactId>
	<version>0.0.1</version>
</dependency>
```

When assembling through Maven unless it is overridden method you will get a message:

"Not exist method 'methodName()'. Annotation @MustOverride."

## Note!:
- If @MustOverride annotation in the class, which is based, it will not be processed in the child class of the main project.

- Only for Eclipse: if you export the project it can not be connected to the Annotation Processing because it uses tools.jar.

If you know how to fix the problem in a note please write to alex.poljuhovic@eltex.net or the69eyes1@gmail.com.