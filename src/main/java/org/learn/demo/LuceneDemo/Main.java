package org.learn.demo.LuceneDemo;

import org.apache.lucene.analysis.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.codecs.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;



public class Main {
	public void loadIndex() throws IOException, ParseException{
		// Define a analyzer
		Analyzer analyzer = new StandardAnalyzer();
		// Store the index in memory:
	    //Directory directory = new RAMDirectory();
	    // To store an index on disk, use this instead:
	    Directory directory = FSDirectory.open(new File("./testindex").toPath());
	    IndexWriterConfig config = new IndexWriterConfig(analyzer);
	    IndexWriter iwriter = new IndexWriter(directory, config);
	    String text1 = "This is the first text to be indexed.";
	    String text2 = "Ops.";
	    String text3 = "This is the second text to be indexed.";
	    String text4 = "This is the third text to be indexed.";
	    String [] strs = {text1, text2, text3, text4};
	    for(int i=0;i<4;i++){
		    Document doc = new Document();
		    doc.add(new Field("content", strs[i], TextField.TYPE_STORED));
		    doc.add(new Field("fileID", "" + i, TextField.TYPE_STORED));
		    iwriter.addDocument(doc);
	    }
	    iwriter.close();
	    directory.close();
	}
	public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
