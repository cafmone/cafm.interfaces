## CAFM.TOOLS java interface
edit CafmToolsSettings.java  
``` java
public CafmToolsSettings get() {
	CafmToolsSettings obj = new CafmToolsSettings();
	obj.user = "[your user]";
	obj.pass = "[your password]";
	obj.url = "http://127.0.0.1/login/";
	obj.plugin = "[plugin name]";
	obj.pubkey = "[server ssh public key]";
	obj.apikey = "[server api key]";
	return obj;
}
```
javac CafmTools*.java  
echo Main-Class: CafmTools > manifest.txt  
jar -c -f CafmTools.jar -v -m manifest.txt CafmTools*.class  
java -jar CafmTools.jar  

