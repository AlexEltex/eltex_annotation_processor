
## Concisely (Кратко)

Implementation @MustOvveride annotations to build the project through maven.

Реализация аннотации @MustOvveride для сборки проекта через maven.

## Using (Использование)

Add to pom.xml (Добавить в pom.xml):

1. Add to plugins (В раздел плагинов)

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

2. Add to dependencies (В раздел зависимости)

```
<dependency>
	<groupId>net.eltex</groupId>
	<artifactId>eltex_annotation_processor</artifactId>
	<version>0.0.1</version>
</dependency>
```

When assembling through Maven unless it is overridden method you will get a message(При сборке через мавен если не будет переопределен метод то будет выдано сообщение):

"Not exist method 'methodName()'. Annotation @MustOverride."

## Note (Примечание):
- If @MustOvveride annotation in the class, which is based, it will not be processed in the child class of the main project.
Если аннотация @MustOvveride в классе, который находится в зависимости, то она не будет обрабатываться в дочерних класса из основного проекта. 

- Only for Eclipse: if you export the project it can not be connected to the Annotation Processing because it uses tools.jar.
Только для Eclipse: если экспортировать проект то его нельзя будет подключить в Annotation Processing так как используется tools.jar.

If you know how to correct items in the note please write to or alex.poljuhovic@eltex.net the69eyes1@gmail.com.

Если вы знаете как исправить пункты в примечание то пожалуйста напишите на alex.poljuhovic@eltex.net или the69eyes1@gmail.com .