/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordNet;

import Static.StaticValue;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.POS;
import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Lin;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author Kylan.Xu
 */
public class SimilarityComputer {

    private String aimStr;
    private JWS ws;

    /*
    *initial computer
    */
    public SimilarityComputer(String str) {
        if (str.length() == 0) {
            System.out.println("输入为空");
        } else {
            this.aimStr = str;
            ws = new JWS(StaticValue.jwsdir, "2.1");
        }
    }

    /*
    interface to reset aim string
    */
    public void setAimStr(String str){
        this.aimStr = str;
    }
    
    /*
    traversal wordnet and find all of the words similar to the aim string
    */
    public void getSim() throws IOException, InterruptedException {
        String wnhome = StaticValue.jwidir;
        String path = wnhome + File.separator + "dict";
        File wnDir = new File(path);
        IRAMDictionary dict = new RAMDictionary(wnDir, ILoadPolicy.NO_LOAD);
        dict.open();
//        dict.load(true);
        trek(dict);
    }

    private double getSimilarity(String str) {
        String[] strs1 = splitString(aimStr);
        String[] strs2 = splitString(str);
        double sum = 0.0;
        for (String s1 : strs1) {
            for (String s2 : strs2) {
                double sc = maxScoreOfLin(s1, s2);
                sum += sc;
            }
        }
        double Similarity = sum / (strs1.length * strs2.length);
        sum = 0;
        return Similarity;
    }

    private String[] splitString(String str) {
        String[] ret = str.split(" ");
        return ret;
    }

    private double maxScoreOfLin(String str1, String str2) {
        Lin lin = ws.getLin();
        double sc = lin.max(str1, str2, "n");
        if (sc == 0) {
            sc = lin.max(str1, str2, "v");
        }
        return sc;
    }

    private void trek(IDictionary dict) {
        int count = 0;
        System.out.println("=================Treking across Wordnet=================");
        long t = System.currentTimeMillis();
        for (POS pos : POS.values()) { //遍历所有词性
            for (Iterator<IIndexWord> i = dict.getIndexWordIterator(pos); i.hasNext();) {
//               System.out.println(i.next().getWordIDs().toString());
                if (getSimilarity(dict.getWord(i.next().getWordIDs().get(0)).getLemma()) > 0.75) {
                    count++;
//                    System.out.println(dict.getWord(i.next().getWordIDs().get(0)).getLemma());
                };
                //遍历某一个词性的所有索引 
//              for(IWordID wid:i.next().getWordIDs()){
//                  //遍历每一个词的所有义项
//                  System.out.println(dict.getWord(wid).getSynset().getGloss());
//                  seen+=dict.getWord(wid).getSynset().getWords().size();//获取某一个synsets所具有的词
//                  if(seen>tickNext){
//                     System.out.print(".");
//                     tickNext=seen + tickSize;
//                  }
//              }
            }
        }
        System.out.printf("done (%1d msec)\n", System.currentTimeMillis() - t);
//       System.out.println("In my trek I saw "+ seen + " words");
        System.out.println("In my trek I found " + count + " words");
    }
}
