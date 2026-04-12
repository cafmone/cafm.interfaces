import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
public class Custom
{
	public static void main(String[] args) {

		String csv = "";
		HttpURLConnection pdf = null;
		String[] init = new String[] {"returnObject"};
		CafmTools.main(init);

		// pull facilities
		csv = CafmTools.facilities.pull();
		if(CafmTools.error != null) {
			System.out.println(CafmTools.error);
		} else {
			// System.out.println(csv);
		}

		// facilities identifiers
		csv = CafmTools.facilities.identifiers();
		if(CafmTools.error != null) {
			System.out.println(CafmTools.error);
		} else {
			// System.out.println(csv);
		}

		// facilities checkliste
 		pdf = CafmTools.facilities.checkliste("5f2950d6cde9d");
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
}
