/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdas;

import WordNet.SimilarityComputer;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
 
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import java.util.Iterator;
import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Lin;

/**
 *
 * @author Xcc
 */
public class MDAS {

    static private String str1;
    static private String str2;
    private static String dir = "C:/Program Files (x86)/WordNet";
    private static JWS ws = new JWS(dir, "2.1");
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String s1="worker";
        SimilarityComputer sc = new SimilarityComputer(s1);
        sc.getSim();
        sc.setAimStr("employee");
        sc.getSim();
    }
}