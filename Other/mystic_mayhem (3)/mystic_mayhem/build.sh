# check if MysticMayhemGame.java exists
if [ ! -f "MysticMayhemGame.java" ]; then
    echo "MysticMayhemGame.java not found"
    exit 1
fi
javac -d . MysticMayhemGame.java
jar -cmf manifest.mf MysticMayhemGame.jar mystic_mayhem
rm -rf mystic_mayhem
