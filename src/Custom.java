import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
public class Custom
{
	public static void main(String[] args) {

		HttpURLConnection csv = null;
		HttpURLConnection pdf = null;
		String[] init = new String[] {"returnObject"};
		CafmTools.main(init);

		// pull facilities
		csv = CafmTools.facilities.pull.devices("0");
		if(CafmTools.error != null) {
			System.out.println(CafmTools.error);
		} else {
			//System.out.println(http2string(csv));
		}

		// facilities identifiers
		csv = CafmTools.facilities.pull.identifiers();
		if(CafmTools.error != null) {
			System.out.println(CafmTools.error);
		} else {
			//System.out.println(http2string(csv));
		}

		// facilities checkliste
 		pdf = CafmTools.facilities.pull.checkliste("5f2950d6cde9d");
		if(CafmTools.error != null) {
			System.out.println(CafmTools.error);
		} else {
			try {
				BufferedReader in = new BufferedReader(
					new InputStreamReader(pdf.getInputStream())
				);
				String inputLine;
				StringBuffer txt = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					txt.append(inputLine);
				}
				in.close();
				//System.out.println(txt.toString());
			}
			catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

	}
	
	//--------------------------------------
	/**
	 * hhtp2string
	 *
	 * @access public
	 * @return String
	 */
	//--------------------------------------
	protected static String http2string(HttpURLConnection http) {
		try {
			BufferedReader in = new BufferedReader(
				new InputStreamReader(http.getInputStream())
			);
			String inputLine;
			StringBuffer html = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine+"\r\n");
			}
			in.close();
			return html.toString();
		}
		catch (Exception e) {
			return "";
		}
	}
	
	
}
