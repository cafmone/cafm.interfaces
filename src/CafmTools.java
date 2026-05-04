/**
 * CAFM.TOOLS java interface
 *
 * @package CafmTools
 * @author Alexander Kuballa
 * @copyright Copyright (c) 2026, CAFM.ONE GmbH
 */

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.DataOutputStream;
//import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.Base64;
import java.io.File;
import java.nio.file.Files;

public class CafmTools
{
/**
 * error message
 * @access public
 * @var String
*/
public static String error;
protected static boolean returnObject;
protected static CafmToolsSettings settings;
protected static HttpURLConnection http;

	//--------------------------------------
	/**
	 * Help
	 *
	 * @access public
	 */
	//--------------------------------------
	public static void help() {
		System.out.println("HELP");
		System.out.println("facilities pull devices [date] (1)");
		System.out.println("facilities pull trash");
		System.out.println("facilities pull log");
		System.out.println("facilities pull identifiers");
		System.out.println("facilities pull checkliste [id] (2)");
		System.out.println("facilities push devices [path]");
		System.out.println("returnObject (3)");
		System.out.println("");
		System.out.println("(1) 2025-12-30 or 0 to return all");
		System.out.println("(2) required");
		System.out.println("(3) return instead of print");
	}

	//--------------------------------------
	/**
	 * Constructor
	 *
	 * @access public
	 * @param String[] args
	 */
	//--------------------------------------
	public static void main(String[] args) {
		settings = new CafmToolsSettings().get();
		if(args.length > 0) {
			switch (args[0]) {
				case "facilities":
					if(args.length > 1) {
						switch (args[1]) {
							case "pull":
								if(args.length > 2) {
									switch (args[2]) {
										case "devices":
											String date = "0";
											if(args.length > 3) {
												date = args[3];
											}
											facilities.pull.devices(date);
										break;
										case "trash":
											facilities.pull.trash();
										break;
										case "log":
											facilities.pull.log();
										break;
										case "identifiers":
											facilities.pull.identifiers();
										break;
										case "checkliste":
											if(args.length > 3) {
												facilities.pull.checkliste(args[3]);
											} else { help(); }
										break;
										default: help(); break;
									}
								} else {
									help();
								}
							break;
							case "push":
								if(args.length > 2) {
									switch (args[2]) {
										case "devices":
											String path = "0";
											if(args.length > 3) {
												path = args[3];
											}
											facilities.push.devices(path);
										break;
										default: help(); break;
									}
								} else {
									help();
								}
							break;
							default: help(); break;
						}
					} else {
						help();
					}
				break;
				case "returnObject": 
					returnObject = true;
				break;
				default: help(); break;
			}
		} else {
			help();
		}
		if(error != null) {
			System.out.println(error);
		}
	}

	//--------------------------------------
	/**
	 * Http connect
	 *
	 * @access protected
	 * @param String url
	 * @param int redirects 0=nofollow
	 */
	//--------------------------------------
	protected static void connect(String url, int redirects) {
		boolean redirect = false;
		if(redirects <= 5) {
			try {
				String stringToEncode = settings.user + ":" + settings.pass;
				String authHeaderValue = "Basic " + Base64.getEncoder().encodeToString(stringToEncode.getBytes());
				URL obj = new URI(url).toURL();
				http = (HttpURLConnection) obj.openConnection();
				http.setReadTimeout(5000);
				http.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
				http.setRequestMethod("POST");
				http.setRequestProperty("Connection", "Keep-Alive");
				http.setRequestProperty("Cache-Control", "no-cache");
				http.addRequestProperty("User-Agent", settings.plugin);
				http.setRequestProperty("Authorization", authHeaderValue);
				if(redirects != 0) {
					int status = http.getResponseCode();
					if (status != HttpURLConnection.HTTP_OK && status != 401) {
						if (
							status == HttpURLConnection.HTTP_MOVED_TEMP || 
							status == HttpURLConnection.HTTP_MOVED_PERM || 
							status == HttpURLConnection.HTTP_SEE_OTHER
						) {
							connect(http.getHeaderField("Location"), redirects+1);
						}
					} else {
						if(error == null && status != HttpURLConnection.HTTP_OK) {
							error = "Error: "+status;
						}
					}
				}
			}
			catch (Exception e) {
				error = "Error: " + e.getMessage();
			}
		} else {
			error = "Error: Too many redirects ("+redirects+")";
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
			BufferedReader in;
			if(http.getErrorStream() != null) {
				in = new BufferedReader(
					new InputStreamReader(http.getErrorStream())
				);
			} else {
				in = new BufferedReader(
					new InputStreamReader(http.getInputStream())
				);
			}
			String inputLine;
			StringBuffer html = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine+"\n");
			}
			in.close();
			return html.toString();
		}
		catch (Exception e) {
			error = "Error: " + e.getMessage();
			return "";
		}
	}

	//--------------------------------------
	/**
	 * class facilities
	 *
	 * @access public
	 */
	//--------------------------------------
	public class facilities {

		//--------------------------------------
		/**
		 * class pull
		 *
		 * @access public
		 */
		//--------------------------------------
		public class pull {

			//--------------------------------------
			/**
			 * facilities.pull.devices
			 *
			 * @access public
			 * @param String date
			 * @return HttpURLConnection
			 * @return null
			 */
			//--------------------------------------
			public static HttpURLConnection devices(String date) {
				error = null;
				try {
					String url = settings.url+"/api.php?plugin="+settings.plugin+"&action=facilities&facilities=export&date="+date;
					connect(url, 1);
					if(http != null) {
						String contentType = http.getHeaderField("Content-Type");
						if (!"text/csv; charset=utf-8".equals(contentType)) {
							if(error == null) {
								error = http2string(http);
							}
						} else {
							if(returnObject == true) {
								return http;
							} else {
								System.out.println(http2string(http));
							}
						}
					}
				} catch (Exception e) {
					error = "Error: " + e.getMessage();
				}
				return null;
			}

			//--------------------------------------
			/**
			 * facilities.pull.trash
			 *
			 * @access public
			 * @return HttpURLConnection
			 * @return null
			 */
			//--------------------------------------
			public static HttpURLConnection trash() {
				error = null;
				try {
					String url = settings.url+"/api.php?plugin="+settings.plugin+"&action=facilities&facilities=trash";
					connect(url, 1);
					if(http != null) {
						String contentType = http.getHeaderField("Content-Type");
						if (!"text/csv; charset=utf-8".equals(contentType)) {
							if(error == null) {
								error = http2string(http);
							}
						} else {
							if(returnObject == true) {
								return http;
							} else {
								System.out.println(http2string(http));
							}
						}
					}
				} catch (Exception e) {
					error = "Error: " + e.getMessage();
				}
				return null;
			}

			//--------------------------------------
			/**
			 * facilities.pull.log
			 *
			 * @access public
			 * @return HttpURLConnection
			 * @return null
			 */
			//--------------------------------------
			public static HttpURLConnection log() {
				error = null;
				try {
					String url = settings.url+"/api.php?plugin="+settings.plugin+"&action=facilities&facilities=log";
					connect(url, 1);
					if(http != null) {
						String contentType = http.getHeaderField("Content-Type");
						if (!"text/plain; charset=utf-8".equals(contentType)) {
							if(error == null) {
								error = http2string(http);
							}
						} else {
							if(returnObject == true) {
								return http;
							} else {
								System.out.println(http2string(http));
							}
						}
					}
				} catch (Exception e) {
					error = "Error: " + e.getMessage();
				}
				return null;
			}

			//--------------------------------------
			/**
			 * facilities.pull.identifiers
			 *
			 * @access public
			 * @return HttpURLConnection
			 * @return null
			 */
			//--------------------------------------
			public static HttpURLConnection identifiers() {
				error = null;
				try {
					String url = settings.url+"/api.php?plugin="+settings.plugin+"&action=facilities&facilities=identifiers";
					connect(url, 1);
					if(http != null) {
						String contentType = http.getHeaderField("Content-Type");
						if (!"text/csv; charset=utf-8".equals(contentType)) {
							if(error == null) {
								error = http2string(http);
							}
						} else {
							if(returnObject == true) {
								return http;
							} else {
								System.out.println(http2string(http));
							}
						}
					}
				} catch (Exception e) {
					error = "Error: " + e.getMessage();
				}
				return null;
			}

			//--------------------------------------
			/**
			 * facilities.pull.checkliste
			 *
			 * @access public
			 * @param String id
			 * @return HttpURLConnection
			 * @return null
			 */
			//--------------------------------------
			public static HttpURLConnection checkliste(String id) {
				error = null;
				try {
					String url = settings.url + "/../shorturl/facilities/todos/id/" + id;
					connect(url, 1);
					if(http != null) {
						String contentType = http.getHeaderField("Content-Type");
						if (!"application/pdf".equals(contentType)) {
							if(error == null) {
								error = http2string(http);
							}
						} else {
							return http;
						}
					}
				} catch (Exception e) {
					error = "Error: " + e.getMessage();
				}
				return null;
			}

		}

		//--------------------------------------
		/**
		 * class push
		 *
		 * @access public
		 */
		//--------------------------------------
		public class push {

			//--------------------------------------
			/**
			 * facilities.push.devices
			 *
			 * @access public
			 * @param String date
			 * @return HttpURLConnection
			 * @return null
			 */
			//--------------------------------------
			public static HttpURLConnection devices(String path) {
				error = null;
				try {
					String url = settings.url+"/api.php?plugin="+settings.plugin+"&action=facilities&facilities=import";
					connect(url, 0);
					if(http != null) {

						File file = new File(path);
						String mimeType = Files.probeContentType(file.toPath());
						byte[] fileContent = Files.readAllBytes(file.toPath());

						http.setDoOutput(true);
						http.addRequestProperty("Content-Type", "multipart/form-data;boundary=*****");
						DataOutputStream request = new DataOutputStream(http.getOutputStream());
						request.writeBytes("--*****");
						request.writeBytes("\r\n");
						request.writeBytes("Content-Disposition: form-data; name=\"attachment\"; filename=\"facilities.csv\"");
						request.writeBytes("\r\n");
						request.writeBytes("Content-Type: "+mimeType);
						request.writeBytes("\r\n\r\n");
						request.write(fileContent);
						request.writeBytes("\r\n");
						request.writeBytes("--*****--");
						request.writeBytes("\r\n");
						request.flush();
						request.close();

						int status = http.getResponseCode();
						if(status != 200) {
							//System.out.println(status);
							System.out.println(http2string(http));
							//error = http2string(http);
						} else {
							System.out.println(http2string(http));
						}

					}
				} catch (Exception e) {
					error = "Error: " + e.getMessage();
				}
				return null;
			}

		}
	}

}
