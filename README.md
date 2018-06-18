# Praqma jutils

This is a legacy library with functionality provided by newer versions of Java. 

## Publising via. OSSRH and Central

This project is published using the guide found [here](https://central.sonatype.org/pages/ossrh-guide.html) and the guide on how to [setup signing with pgp2](https://www.youtube.com/watch?v=DE3FVty3NgE&feature=youtu.be).

The changes required are in the `.pom` file which has the required components. 

The release was basically performed by running the command `mvn deploy`. This will either release your version (if the version number does not end in -SNAPSHOT) or do a proper release in case you have a non snapshot version in your `pom`.
