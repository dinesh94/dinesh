/*package com.rage.siapp.nlp.tools.network.graph.test;

import java.io.File;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import com.rage.siapp.db.DatabaseConnection;
import com.rage.siapp.dictionary.Domain;
import com.rage.siapp.nlp.NLDocument;
import com.rage.siapp.nlp.NLDocumentCreater;
import com.rage.siapp.nlp.data.Article;
import com.rage.siapp.nlp.data.FileArticleCreater;
import com.rage.siapp.nlp.data.News;
import com.rage.siapp.nlp.tools.cluster.ClusterCreater;
import com.rage.siapp.nlp.tools.coref.sentence.Mention;
import com.rage.siapp.nlp.tools.coref.sentence.MentionFinder;
import com.rage.siapp.nlp.tools.coref.sentence.Reference;
import com.rage.siapp.nlp.tools.map.DocumentMap;
import com.rage.siapp.nlp.tools.map.DocumentMapCreater;
import com.rage.siapp.nlp.tools.network.graph.DocumentGraphCreaterv2;
import com.rage.siapp.nlp.tools.network.graph.Graph;
import com.rage.siapp.nlp.tools.network.graph.Node;
import com.rage.siapp.nlp.tools.network.graph.db.GraphUpdater;
import com.rage.siapp.nlp.tools.serialize.ArticleSerializer;
import com.rage.siapp.nlp.tools.serialize.ClustersSerializer;
import com.rage.siapp.nlp.tools.serialize.CoreferencesSerializer;
import com.rage.siapp.nlp.tools.serialize.DocumentMapSerializer;
import com.rage.siapp.nlp.tools.serialize.DocumentSerializer;
import com.rage.siapp.nlp.tools.serialize.ObjectSerializer;

public class GraphDBUpdateTest 
{
	private boolean isLocalTesting ;
	private Connection conn ;
	private Graph graph ;
	private Vector<Node> mainNodes ;

	public GraphDBUpdateTest(boolean isLocalTesting)
	{
		setLocalTesting(isLocalTesting) ;
		Connection conn = DatabaseConnection.getConnection() ;
		setConn(conn) ;
		setMainNodes(new Vector<Node>()) ;
	}

	public void run()
	{
		// createSentenceGraph() ;
		createDocumentGraph() ;
		
		for ( int i=0 ; i<getMainNodes().size() ; i++ )
		{
			GraphUpdater gu = new GraphUpdater(getConn(), getMainNodes().elementAt(i)) ;
			gu.run() ;
		}
	}

	public void createDocumentGraph()
	{
		String serializationPath = "" ;
		if ( isLocalTesting() )
			serializationPath = "/home/cp/Desktop/Serialized-Documents/Universal-Ontology" ;
		else
			serializationPath = "/home/rage/semantics/Fidelity-POC/serialized-documents" ;
		
		ObjectSerializer.setSerializationPath(serializationPath + File.separator + "objects") ;

		Vector<Long> articleIDs = new Vector<Long>(Arrays.asList(new Long[] {
				// new Long(3), 
				new Long(4), 
				new Long(5), 
				new Long(6), 
				new Long(7), 
				new Long(8), 
				new Long(9), 
				new Long(10),
				new Long(11)
		})) ;

		HashMap<Long, String> articleCPMap = new HashMap<Long, String>() ;
		articleCPMap.put(new Long(3), "Nordea") ;
		articleCPMap.put(new Long(4), "UBS") ;
		articleCPMap.put(new Long(5), "Bank of America") ;
		articleCPMap.put(new Long(6), "BNP Paribas") ;
		articleCPMap.put(new Long(7), "Citibank") ;
		articleCPMap.put(new Long(8), "Danske") ;
		articleCPMap.put(new Long(9), "HSBC") ;
		articleCPMap.put(new Long(10), "Nordea") ;
		articleCPMap.put(new Long(11), "RBS") ;

		Graph graph = new Graph() ;

		for ( int i=0 ; i<articleIDs.size() ; i++ )
		{
			Long articleID = articleIDs.elementAt(i) ;

			NLDocument document = DocumentSerializer.deserializeDocument(articleID) ;
			Article article = ArticleSerializer.deserializeDocument(articleID) ;

			Vector<Vector<String>> paragraphs = article == null ? new Vector<Vector<String>>() : article.getParagraphs() ;
			TreeMap<Mention, Vector<Reference>> coreferences = CoreferencesSerializer.deserializeCoreferences(articleID) ;

			if ( document == null || article == null )
			{
				String fileName = serializationPath + File.separator + "text-files" + File.separator + articleID + ".txt" ;

				article = ArticleSerializer.deserializeDocument(articleID) ;
				if ( article == null )
				{
					article = FileArticleCreater.createArticle(articleID, fileName) ;
					ArticleSerializer.serializeArticle(articleID, article) ;
				}

				paragraphs = article.getParagraphs() ;

				document = NLDocumentCreater.createDocument(article, -1) ;
				DocumentSerializer.serializeDocument(articleID, document) ;

				coreferences = MentionFinder.createCoreferences(articleID, document) ;
				CoreferencesSerializer.serializeCoreferences(articleID, coreferences) ;

				DocumentMap documentMap = DocumentMapCreater.createDocumentMap(document, coreferences) ;
				DocumentMapSerializer.serializeDocumentMap(articleID, documentMap) ;

				Vector<Vector<Integer>> clusters = ClusterCreater.createClusters(document, documentMap) ;
				ClustersSerializer.serializeClusters(articleID, clusters) ;
			}

			String counterParty = articleCPMap.get(articleID) ;

			DocumentGraphCreaterv2 dgcn = new DocumentGraphCreaterv2(counterParty, document, coreferences, paragraphs, graph) ;
			dgcn.run() ;

			Node node = dgcn.getMainNode() ;
			getMainNodes().addElement(node) ;
		}
	}

	public void createSentenceGraph()
	{
		Domain.presentDomain = Domain.FINANCE ;

		String serializationPath = "/home/cp/Desktop/Serialized-Documents/Universal-Ontology/objects" ;
		ObjectSerializer.setSerializationPath(serializationPath) ;

		Long newsID = new Long(1000) ;

		NLDocument document = null ; // DocumentSerializer.deserializeDocument(newsID) ;
		TreeMap<Mention, Vector<Reference>> coreferences = null ;
		Vector<Vector<String>> paragraphs = new Vector<Vector<String>>() ;

		if ( document == null )
		{
			Long channelID = new Long(-1) ;
			Long urlID = new Long(-1) ;

			String title = "" +
					"UBS's outlook remains unchanged in Greece by 20%." ;
			String sentence0 = "" ;

			String content = "" ;
			content = (title.endsWith(".") ? title : title + " .") + content ;

			Vector<String> thisParagraph = new Vector<String>() ;
			thisParagraph.addElement(title) ;
			thisParagraph.addElement(sentence0) ;
			paragraphs.addElement(thisParagraph) ;

			Article article = new News(newsID, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, channelID, urlID, title, content, paragraphs) ;
			document = NLDocumentCreater.createDocument(article, -1) ;
			coreferences = MentionFinder.createCoreferences(newsID, document) ;

			DocumentSerializer.serializeDocument(newsID, document) ;
			CoreferencesSerializer.serializeCoreferences(newsID, coreferences) ;
		}

		String counterParty = "Nordea" ;

		setGraph(new Graph()) ;

		DocumentGraphCreaterv2 dgc = new DocumentGraphCreaterv2(counterParty, document, coreferences, paragraphs, graph) ;
		dgc.run() ;

		Node node = dgc.getMainNode() ;

		Vector<Node> mainNodes = new Vector<Node>() ;
		mainNodes.addElement(node) ;

		setMainNodes(mainNodes) ; 
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public Vector<Node> getMainNodes() {
		return mainNodes;
	}

	public void setMainNodes(Vector<Node> mainNodes) {
		this.mainNodes = mainNodes;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public boolean isLocalTesting() {
		return isLocalTesting;
	}

	public void setLocalTesting(boolean isLocalTesting) {
		this.isLocalTesting = isLocalTesting;
	}

	public static void main(String[] args) 
	{
		boolean isLocalTesting = new Boolean(args[0]).booleanValue() ;
		GraphDBUpdateTest gdut = new GraphDBUpdateTest(isLocalTesting) ;
		gdut.run() ;
	}
}
*/