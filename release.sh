mvn -B clean install -Dmaven.test.skip=true -DskipTests -Dtag=3.1  release:prepare release:perform -P sonatype-oss-release

# mvn release:rollback
# git tag -d 3.1
# git push --delete origin 3.1