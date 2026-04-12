public class CafmToolsSettings
{
public String user;
public String pass;
public String url;
public String plugin;
public String pubkey;
public String apikey;
	//--------------------------------------
	/**
	 * get
	 *
	 * @access public
	 * @return CafmToolsSettings
	 */
	//--------------------------------------
	public CafmToolsSettings get() {
		CafmToolsSettings obj = new CafmToolsSettings();
		obj.user = "[your user]";
		obj.pass = "[your password]";
		obj.url = "http://127.0.0.1/login/";
		obj.plugin = "[your user]";
		obj.pubkey = "[server ssh public key]";
		obj.apikey = "[server api key]";
		return obj;
	}
}
