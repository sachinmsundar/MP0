import java.io.*;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MP0 {
    Random generator;
    String userName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    public MP0(String userName) {
        this.userName = userName;
    }


    public Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        long longSeed = Long.parseLong(this.userName);
        this.generator = new Random(longSeed);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public String[] process() throws Exception{
    	String[] topItems = new String[20];
        Integer[] indexes = getIndexes();

    	//TO DO
        int i = 0;
        List<String> lines = new ArrayList<String>();
        String[] words;
        String word;
        BufferedReader rd = null;
        List<String> ignoreList = Arrays.asList(stopWordsArray);
        HashMap<String, Long>  map = new HashMap<String, Long>();

        try {
            rd = new BufferedReader(new InputStreamReader(System.in));
            String line;
            line = rd.readLine();
            while ((line != null && !line.equals(""))) {
                lines.add(line);
                line = rd.readLine();
            }

            rd.close();

            //Pattern p = Pattern.compile(Pattern.quote(delimiters));
            for(i = 0; i < indexes.length; ++i) {
                //words = null;
                //word = null;
                words = lines.get(indexes[i]).split("\\t|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/");

                for (int j = 0; j < words.length; ++j) {
                    word = words[j].toLowerCase().trim();
                    if (ignoreList.contains(word) == false) {
                        if(map.containsKey(word)){
                            map.put(word, (map.get(word) + 1));
                        }
                        else{
                            map.put(word, (long) 1);
                        }
                    }
                }
            }

            //TreeMap<String, Long> sortedMap = sortMap(map);



            Stream<Map.Entry<String,Long>> sortedMap =
                    map.entrySet().stream()
                            .sorted(Map.Entry.comparingByKey())
                            .sorted(Map.Entry.comparingByValue());

            Long abc = sortedMap.count();
            //String k = sortedMap.

//            Map<K,V> topTen =
//                    map.entrySet().stream()
//                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                            .limit(10)
//                            .collect(Collectors.toMap(
//                                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }
        catch(Exception e){
            throw e;
        }

        //TO DO
		return topItems;
    }

//    //TO DO
//    public static TreeMap<String, Long> sortMap(HashMap<String, Long> map){
//        Comparator<String> comp = new VC(map);
//        TreeMap<String, Long> res = new TreeMap<String, Long>(comp);
//        res.putAll(map);
//        return res;
//    }
//
//    static class VC implements Comparator<String>{
//
//        HashMap<String, Long> map = new HashMap<String, Long>();
//
//        public VC(HashMap<String, Long> map){
//            this.map.putAll(map);
//        }
//
//        @Override
//        public int compare(String s1, String s2) {
//            if(map.get(s1) > map.get(s2)){
//                return -1;
//            }
//            else if(map.get(s1) > map.get(s2)){
//                return 0;
//            }
//            else {
//                return 1;
//            }
//        }
//    }
//    //TO DO

    public static void main(String args[]) throws Exception {
    	if (args.length < 1){
    		System.out.println("missing the argument");
    	}
    	else{
    		String userName = args[0];
	    	MP0 mp = new MP0(userName);
	    	String[] topItems = mp.process();

	        for (String item: topItems){
	            System.out.println(item);
	        }
	    }
	}

}
