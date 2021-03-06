*Читать на других языках: [English](README.md)*

## Кратко

Реализация аннотации @MustOverride для сборки проекта через maven.

## Использование

Добавить в pom.xml:

1. В раздел плагинов

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

2. В раздел зависимости

```
<dependency>
	<groupId>net.eltex</groupId>
	<artifactId>eltex_annotation_processor</artifactId>
	<version>0.0.1</version>
</dependency>
```

При сборке через мавен если не будет переопределен метод то будет выдано сообщение:

"Not exist method 'methodName()'. Annotation @MustOverride."

## Примечание!:
- Если аннотация @MustOverride в классе, который находится в зависимости, то она не будет обрабатываться в дочерних класса из основного проекта. 

- Только для Eclipse: если экспортировать проект то его нельзя будет подключить в Annotation Processing так как используется tools.jar.

Если вы знаете как исправить проблемы в примечание то пожалуйста напишите на alex.poljuhovic@eltex.net или the69eyes1@gmail.com .