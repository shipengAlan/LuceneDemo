package org.learn.demo.LuceneDemo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainTest {

	private static Main m = new Main();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		m.deleteDir(new File("./testindex"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadIndex() throws IOException, ParseException {
		m.loadIndex();
		Analyzer analyzer = new StandardAnalyzer();
		Directory directory = FSDirectory.open(new File("./testindex").toPath());
	    // Now search the index:
	    DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    // Parse a simple query that searches for "text":
	    QueryParser parser = new QueryParser("content", analyzer);
	    Query query = parser.parse("text");
	    ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;
	    assertEquals(3, hits.length);
	    // Iterate through the results:
	    int[] fileIDs = {0, 2, 3};
	    for (int i = 0; i < hits.length; i++) {
	      Document hitDoc = isearcher.doc(hits[i].doc);
	      assertEquals("" + fileIDs[i], hitDoc.get("fileID"));
	    }
	    ireader.close();
	    directory.close();
	}

}
