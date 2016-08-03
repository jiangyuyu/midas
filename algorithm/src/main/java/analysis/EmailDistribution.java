package analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class EmailDistribution {
  public void analyze(String fileName, String outputFile) throws IOException {
    HashMap<String, String> emailPersonMap = new HashMap<String, String>();
    HashMap<String, Integer> emailTypeMapCount = new HashMap<String, Integer>();
    HashMap<String, String> emailTypeMap = new HashMap<String, String>();
    HashMap<String, String> emailRegionMap = new HashMap<String, String>();
//    HashMap<String, Integer> commonDomain = new HashMap<String, Integer>();

    emailTypeMapCount.put("linkedin", 0);
    emailTypeMapCount.put("autogen", 0);
    emailTypeMapCount.put("commercial", 0);
    emailTypeMapCount.put("business", 0);

    HashSet<String> regionSet = new HashSet<String>();
    regionSet.addAll(Arrays.asList(regions));

    BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
    String temp;
    int totalEmails = 0;
    while ((temp = br.readLine()) != null) {
      String[] emailPerson = getEmailAndPerson(temp);
      if (emailPerson[0].equals("null@null.com") || emailPerson[0].equals("none@none.com")
          || emailPerson[0].equals("test@test.com")) continue;
      if (indexEmailPerson(emailPerson[0], emailPerson[1], emailPersonMap)) {
        totalEmails++;
//        indexCommonDomain(emailPerson[0], commonDomain);
        indexEmailType(emailPerson[0], emailTypeMapCount, emailTypeMap);
        indexEmailRegion(emailPerson[0], emailRegionMap, regionSet);
      }
    }

    System.out.println(totalEmails);
    System.out.println(emailTypeMapCount);
    BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFile)));
    int[] stats = new int[8];
    for (String email : emailTypeMap.keySet()) {
      if (email.isEmpty()) continue;
      if (emailTypeMap.get(email).equals("linkedin") || emailTypeMap.get(email).equals("autogen")) continue;
      bw.write(email + "\t" + emailPersonMap.get(email) + "\n");
      stats[0]++;
      boolean pn = false, reg = false, biz = false;
      if (emailPersonMap.get(email).length() > 0) {
        pn = true;
      }
      if (emailRegionMap.containsKey(email) && emailRegionMap.get(email).length() > 0) {
        reg = true;
      }
      if (emailTypeMap.get(email).equals("business")) {
        biz = true;
      }
      if (pn) {
        stats[1]++;
        if (reg) stats[4]++;
        if (biz) stats[6]++;
        if (reg && biz) stats[7]++;
      }
      if (reg) {
        stats[2]++;
        if (biz) stats[5]++;
      }
      if (biz) {
        stats[3]++;
      }
    }
    for (int count : stats) {
      System.out.printf(count + "\t");
    }
    System.out.println();
    br.close();
    bw.close();
  }

  private void indexCommonDomain(String email, HashMap<String, Integer> commonDomain) {
    String domain = email.substring(email.indexOf("@")+1);
    if (!commonDomain.containsKey(domain)) {
      commonDomain.put(domain, 0);
    }
    commonDomain.put(domain, commonDomain.get(domain) + 1);
  }

  private String[] getEmailAndPerson(String line) {
    String[] emailPerson = new String[2];
    Arrays.fill(emailPerson, "");
    if (line == null || line.trim().length() == 0) {
      return emailPerson;
    }
    line = line.replace("\'", "").trim();
    String[] temp = line.split("\t");
    if (temp.length < 1) {
      return emailPerson;
    } else {
      String email = temp[0].trim().toLowerCase();
      if (temp.length > 1) {
        String person = temp[1].trim();
        if (person.indexOf("(") != -1) person = person.substring(0, person.indexOf("(")).trim();
        // do not take person's name as email address
        if (email.equalsIgnoreCase(person)) {
          emailPerson[0] = email;
          emailPerson[1] = "";
        } else {
          emailPerson[0] = email;
          emailPerson[1] = person;
        }
      }
    }
    return emailPerson;
  }

  // return false if it is not a new email
  private boolean indexEmailPerson(String email, String person, HashMap<String, String> emailPersonMap) {
    if (email == null || email.length() == 0) return false;
    if (!emailPersonMap.containsKey(email)) {
      emailPersonMap.put(email, person.trim());
      return true;
    }
    String existingPerson = emailPersonMap.get(email);
    if (existingPerson.length() == 0) {
      if (person != null && person.length() > 0) {
        emailPersonMap.put(email, person);
      }
    } else if (person.length() > existingPerson.length() && include(person, existingPerson)) {
      // previous name is a substring of current name
      emailPersonMap.put(email, person);
    }
    return false;
  }

  public boolean include(String s1, String s2) {
    if (s1 == null || s2 == null) {
      return false;
    }
    if (s1.length() < s2.length()) {
      return false;
    }
    String[] elems = s1.split("\\W");
    String[] elems2 = s2.split("\\W");

    HashSet<String> elemsSet = new HashSet<String>();
    elemsSet.addAll(Arrays.asList(elems));
    for (String s : elems2) {
      if (s.length() == 0) continue;
      if (!elemsSet.contains(s)) return false;
    }
    return true;
  }

  private void indexEmailType(String email, HashMap<String, Integer> emailTypeMapCount, HashMap<String, String> emailTypeMap) {
    if (email.indexOf("linkedin.com") != -1 || email.indexOf("lynda.com") != -1 || email.indexOf("linkedin.biz") != -1) {
      emailTypeMapCount.put("linkedin", emailTypeMapCount.get("linkedin")+1);
      emailTypeMap.put(email, "linkedin");
    } else if (email.indexOf("calendar.google.com") != -1 || email.indexOf("docs.google.com")!=-1
        || email.indexOf("chatter.salesforce.com") != -1 || email.indexOf("wrfl.salesforce.com") != -1
        || email.indexOf("le.salesforce.com") != -1 || email.indexOf("wrkfl.salesforce.com") != -1
        || email.indexOf("donotreply") != -1) {
      emailTypeMapCount.put("autogen", emailTypeMapCount.get("autogen") + 1);
      emailTypeMap.put(email, "autogen");
    } else {
      if (email.indexOf("gmail.com") != -1 || email.indexOf("yahoo.com") != -1 || email.indexOf("hotmail.com") != -1
          || email.indexOf("hotmail.co.uk") != -1 || email.indexOf("msn.com") != -1
          || email.indexOf("facebookmail.com") != -1) {
        emailTypeMapCount.put("commercial", emailTypeMapCount.get("commercial") + 1);
        emailTypeMap.put(email, "commercial");
      } else {
        emailTypeMapCount.put("business", emailTypeMapCount.get("business") + 1);
        emailTypeMap.put(email, "business");
      }
    }
  }

  private void indexEmailRegion(String email, HashMap<String, String> emailRegionMap, HashSet<String> regionSet) {
    int lastDot = email.lastIndexOf(".");
    if (lastDot == -1) return;
    String region = email.substring(lastDot + 1);
    if (region.length() == 2 && regionSet.contains(region)) {
      emailRegionMap.put(email, region);
    }
  }

  public static void main(String[] args) throws IOException{
    if (args.length == 0) {
      System.out.println("Input file is not given");
      System.exit(-1);
    }
    String fileName = args[0];
    String outputFileName = args[1];
    EmailDistribution test = new EmailDistribution();
    test.analyze(fileName, outputFileName);
  }

  private static String[] regions = {
      "ac","ad","ae","af","ag","ai","al","am","an","ao","aq","ar","as","at","au","aw","ax","az","ba","bb","bd","be","bf","bg","bh","bi","bj","bm","bn","bo","bq","br","bs","bt","bv","bw","by","bz","ca","cc","cd","cf","cg","ch","ci","ck","cl","cm","cn","co","cr","cu","cv","cw","cx","cy","cz","de","dj","dk","dm","do","dz","ec","ee","eg","eh","er","es","et","eu","fi","fj","fk","fm","fo","fr","ga","gb","gd","ge","gf","gg","gh","gi","gl","gm","gn","gp","gq","gr","gs","gt","gu","gw","gy","hk","hm","hn","hr","ht","hu","id","ie","il","im","in","io","iq","ir","is","it","je","jm","jo","jp","ke","kg","kh","ki","km","kn","kp","kr","kw","ky","kz","la","lb","lc","li","lk","lr","ls","lt","lu","lv","ly","ma","mc","md","me","mg","mh","mk","ml","mm","mn","mo","mp","mq","mr","ms","mt","mu","mv","mw","mx","my","mz","na","nc","ne","nf","ng","ni","nl","no","np","nr","nu","nz","om","pa","pe","pf","pg","ph","pk","pl","pm","pn","pr","ps","pt","pw","py","qa","re","ro","rs","ru","rw","sa","sb","sc","sd","se","sg","sh","si","sj","sk","sl","sm","sn","so","sr","ss","st","su","sv","sx","sy","sz","tc","td","tf","tg","th","tj","tk","tl","tm","tn","to","tp","tr","tt","tv","tw","tz","ua","ug","uk","us","uy","uz","va","vc","ve","vg","vi","vn","vu","wf","ws","ye","yt","za","zm","zw"
  };
}
