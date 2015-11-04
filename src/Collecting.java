import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


//issues: connect till possible, while(true)
//issues: sorted map by numbers, not first number
//isses: new object list3d implementation
//issues:create abstract class from the methods got from other packet


public class Collecting {
	
	
	//try to parse to int, if it is not working, then it is not a number
	
	static List<String> commentsInfo = new ArrayList(); 
	static List<String> comments = new ArrayList();
	static int nr = 0;
	
	static List<String> info0 = new ArrayList();

	public static void collectingCommentsOld(String url) throws IOException {

			String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";
			Document doc = Jsoup.connect(url).userAgent(ua).get();
			System.out.println("Connecting to:"+url);

			Elements commentElements = doc.getElementsByClass("comment-content-inner");
			Elements commentIPElements = doc.getElementsByClass("comment-date");			
			Elements commentAuthorElements = doc.getElementsByClass("comment-author");
			Elements commentVotingUpElements = doc.getElementsByClass("comment-votes-up");
			Elements commentVotingDownElements = doc.getElementsByClass("comment-votes-down");
						
			List<String> commentsIP = listFromElements(commentIPElements);
			List<String> commentsAuthor = listFromElements(commentAuthorElements);
            //commentsIP= addingTwoListsOneByOne (commentsAuthor, commentsIP);
			
            
            List<String> commentsVotingUp = listFromElements(commentVotingUpElements);
            List<String> commentsVotingDown = listFromElements(commentVotingDownElements);
            
            //commentsInfo = addingLists (commentsVotingUp, commentsVotingDown);
            List <String> commentsInfo1 = addingLists(commentsAuthor, commentsIP, commentsVotingUp, commentsVotingDown);
            
            commentsInfo = combineLists(commentsInfo, commentsInfo1);
            //commentsInfo.addAll(commentsInfo1);
            //final class is not overridible.
            
            comments = listFromElements(commentElements, comments);
			
			List<List3D> finalList = new ArrayList<List3D>();
			List3D list3D = new List3D(0, "", "", "");
			
			int numberOfComments = comments.size();			
			for (int i = 1; i < numberOfComments; i++) {
				finalList.add(new List3D(i, "", "", ""));
			}			
			
		}
	
	static Map<String, String> map0 = new LinkedHashMap<String, String>();
	
	//more upgraded method which includes the fact that some comments were deleted
	public static void collectingComments(String url) throws IOException {

		String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";
		Document doc = Jsoup.connect(url).userAgent(ua).data("query", "Java").get();
		System.out.println(doc.title());
		info0.add(doc.title());
		System.out.println("Connecting to:"+url);
		
		Elements commentElements0 = doc.getElementsByAttribute("data-post-id");
		
		Elements commentElements1 = doc.getElementsByClass("load-more-comments-btn");
		System.out.println(commentElements1.text());
		
		String aboutComment0 = null;
		String commentText0 = null;
		for (Element commentElement : commentElements0) {
			Document doc0 = Jsoup.parse(commentElement.outerHtml());
			nr++;
			aboutComment0 = nr + ". ";
			if (!doc0.getElementsByClass("comment-author").isEmpty()) {
			aboutComment0 += doc0.getElementsByClass("comment-author").text() + 
			" " + doc0.getElementsByClass("comment-date").first().text();
			}
			if (!doc0.getElementsByClass("comment-votes-up").isEmpty()){		
			aboutComment0 += " (Up "+ doc0.getElementsByClass("comment-votes-up").first().text() + 
			":Down " + doc0.getElementsByClass("comment-votes-down").first().text() + ")";
			}
			if (!doc0.getElementsByClass("comment-content-inner").isEmpty()){
			commentText0 = doc0.getElementsByClass("comment-content-inner").first().text();
			}
			map0.put(aboutComment0, commentText0);
		}
	}	
		
	public static void collectingDelfi(String url) throws IOException {
		
	    info0.add(url);
		url += "&com=1&reg=";

		//registered commentators
		String url0 = url + "1&no=0&s=2";
		collectingComments(url0);
		//the number of pages of the comments
		//for (int i = 0; i < 3; i++)
		int i = 0;
		while (i<10){
			i++;
			url0 = url + "0&s=2&no=" + i*20;
			collectingComments(url0);
		}
		
	Map<String, String> commentsWithInfo = combineListsToMap(commentsInfo, comments);
	commentsWithInfo = map0;
	//commentsWithInfo = sortMapByKeyAscending(commentsWithInfo);
	commentsMapToFile(commentsWithInfo);
	}
	
	
	
	
	public static void commentsMapToFile(Map <String, String> map) throws IOException {
	
		String path0 = "/Users/kristinaslekyte/Desktop/";
		String path = path0 + readingConsole("Please, enter the filename to save on your desktop!")+".txt";		
		while (new File(path).isFile()){
			System.out.println("The file already exists. Try once more!");
			path = path0 + readingConsole("Please, enter the filename to save on your desktop!")+".txt";
			//System.out.println("The program is terminated!");
			//return;
		};
		headOfFile(info0, path);
		mapToFile(map, path);	
	}
	
	//#combine lists to map
	public static Map<String, String> combineListsToMap (List<String> list1, List<String> list2) {
	
    Map<String, String> map = new HashMap<String, String>();
	Iterator<String> i1 = list1.iterator();
	Iterator<String> i2 = list2.iterator();
	while (i1.hasNext() && i2.hasNext()) {
	    map.put(i1.next(), i2.next());
	}
	if (i1.hasNext() || i2.hasNext()) System.out.println("The lists have different length!");
	return map;
}

	
	//#output by the keys in ascending order
	public static Map sortMapByKeyAscending (Map unsortMap) {	    
		Map<String, String> treeMap = new TreeMap<String, String>(unsortMap);	
		return treeMap;
	}
	
	//#combine lists
	public static List<String> combineLists(List<String> list1, List<String> list2) {
		List<String> list = list1;
		for (String frmList2 : list2) {
			list.add(frmList2);
		}
		return list;
	}
	
	//overload	
	public static List <String> addingLists (List<String> list1, List<String> list2){	
		List<String> list = new ArrayList();
		//sizes of the lists
		int list1Size = list1.size();
		int list2Size = list2.size();
		String listElement = null;
		
		if (list1Size != list2Size) {
			System.out.println("Lists sizes are not equal: somewhere it is a mistake.");		
		} else {
			System.out.println("Members from two lists are concatenated...");
			for (int i = 0; i<list1Size; i++){
			listElement = list1.get(i) + " " +list2.get(i);
			list.add(listElement);
			}
		}	
		return list;				
	}
	
	//overload
	public static List<String> addingLists(List<String> list1,
			List<String> list2, List<String> list3, List<String> list4) {
		List<String> list = new ArrayList();
		// sizes of the lists
		int list1Size = list1.size();
		int list2Size = list2.size();
		int list3Size = list3.size();
		int list4Size = list4.size();
		String listElement = null;

		if (list1Size != list2Size || list3Size != list4Size || list2Size != list3Size) {
			System.out
					.println("Lists sizes are not equal: somewhere it is a mistake. Perhaps some comments do not exist.");
		
			for (int i = 0; i < list1Size; i++) {
				nr++;
				listElement = nr + ". " + list1.get(i);
				if (list2Size == list1Size){
					listElement += " " + list2.get(i); 
				}
				if (list3Size == list1Size){
					listElement += " (Up "+ list3.get(i);
				}
				if (list4Size == list1Size){
					listElement += ":Down " + list4.get(i) + ")";
				}
				list.add(listElement);
			}
		
		} else {
			System.out.println("Members from two lists are concatenated...");
			for (int i = 0; i < list1Size; i++) {
				nr++;
				listElement = nr + ". " + list1.get(i) + " " + list2.get(i) + " (Up "
						+ list3.get(i) + ":Down " + list4.get(i) + ")";
				list.add(listElement);
			}
		}
		return list;
	}
	
	public static List <String> listFromElements (Elements elements, List<String> list){
		String elementText = null;
		for (Element element : elements) {
			elementText = element.text();
			list.add(elementText);
		}
		return list;		
	}
	
	//overload
	public static List <String> listFromElements (Elements elements){
		List<String> list = new ArrayList();
        listFromElements(elements, list);
		return list;		
	}
	
	public static void headOfFile (List <String> info, String filepath) throws IOException{
		BufferedWriter writer = null;
		writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(filepath, true), "utf-8"));
		if (info.size()>2){
		writer.write(info.get(1));
		writer.newLine();
		writer.write("Nuoroda: "+info.get(0));
		writer.newLine();
		}
		writer.close();		
	}
	
	public static void mapToFile(Map <String, String> map, String filepath) throws IOException{
				
		System.out.println("Puttting the map to the file...");
		BufferedWriter writer = null;
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filepath, true), "utf-8"));
				
		for (Map.Entry<String, String> entry : map.entrySet()) {
			writer.write(entry.getKey());
			writer.newLine();
			writer.write(entry.getValue());
			writer.newLine();
		}	
		writer.close();
		System.out.println("The comments are in the file " + filepath);	
	}
	
	public static String readingConsole (String question) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(question);
        String answer = br.readLine();
		return answer;		
	}
	
}
