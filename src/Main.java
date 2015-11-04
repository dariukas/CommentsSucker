import java.io.IOException;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
             try {
				run();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private static void run() throws IOException {
		//String url1 ="http://www.delfi.lt/mokslas/mokslas/nepraraskite-budrumo-naktis-klube-gali-baigtis-nemaloniomis-pasekmemis.d?id=66664956&com=1&reg=";
		String url1 = "http://www.delfi.lt/gyvenimas/meile/nuomone-ar-smagu-zinoti-kad-iki-tol-sutuoktine-mylejosi-su-tokiu-jonu-is-sostines-ir-dar-su-antanu-is-uostamiescio.d?id=67020444&com=1&reg=";
		try {
			String url = Collecting.readingConsole("Please, enter the url!");
			Collecting.collectingDelfi(url);
		} catch (Exception e) {
			System.out.println("The url is not valid! The default url will be used.");
			Collecting.collectingDelfi(url1);	
		}	
	}

}
