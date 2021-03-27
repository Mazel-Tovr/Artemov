# Artemov

#### Encode image
```gradlew run --args="enc {ImagePath}"```
<br>
#### Decode
```gradlew run --args="dec {ImagePath} {KeyPath}"```
<br>
#### Or 
```gradlew jar```
<br>
#### go to /build/libs and run with
```java -jar {pathToJar} {command} {ImagePath} {KeyPath if needed}```
<br>
#### For example 
```gradlew run --args="enc D:\GitHub\Artemov\src\main\resources\source\cyberpunk-2077enc.jpg" ```
<br>
<br>
```gradlew run --args="dec D:\GitHub\Artemov\src\main\resources\source\cyberpunk-2077enc.jpg D:\GitHub\Artemov\src\main\resources\source\cyberpunk-2077.key"```
