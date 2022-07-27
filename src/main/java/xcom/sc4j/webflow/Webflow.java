

package xcom.sc4j.webflow ;


import static xcom.utils4j.logging.Loggers.ConsoleLoggerName ;

import java.io.File ;
import java.io.IOException ;
import java.net.URISyntaxException ;
import java.net.URL ;
import java.util.Collection ;
import java.util.List ;
import java.util.Map ;
import java.util.Map.Entry ;

import org.apache.poi.EncryptedDocumentException ;
import org.apache.poi.ss.usermodel.WorkbookFactory ;
import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;

import lombok.Data ;
import xcom.utils4j.data.Excels ;


@Data
public class Webflow {

	private static final Logger Console = LoggerFactory.getLogger(ConsoleLoggerName) ;


	Map<String, List<Entry<String, String>>> webflow ;
	Collection<List<Entry<String, String>>> catalog ;


	public Webflow(Map<String, List<Entry<String, String>>> webflow) throws URISyntaxException, EncryptedDocumentException, IOException {

		this.webflow = webflow ;

		if ( Console.isDebugEnabled() )
			webflow.forEach((k, v) -> Console.debug("Webflow: {} -> {}", k, v)) ;


		String pn = "/" + this.getClass().getPackageName().replaceAll("\\.", "/") ;
		while ( this.catalog == null ) {
			URL url = this.getClass().getResource(pn + "/Web Widget Catalog.xlsx") ;

			if ( url != null )
				this.catalog = Excels.readSheetAsCollection(WorkbookFactory.create(new File(url.toURI()), null, true).getSheet("Widgets")) ;

			else
				pn = pn.substring(0, pn.lastIndexOf('/')) ;    // Check parent directory.
		}

		if ( Console.isDebugEnabled() )
			catalog.forEach((l) -> Console.debug("Catalog: {} -> {}", l)) ;
	}


	public static Collection<Map<String, List<Entry<String, String>>>> data(Class<?> clazz) throws EncryptedDocumentException, IOException, URISyntaxException {

		String fn = "/" + clazz.getName().replaceAll("\\.", "/") + " Webflow.xlsx" ;
		File f = new File(clazz.getResource(fn).toURI()) ;
		return Excels.readWorkbookAsCollection(WorkbookFactory.create(f, null, true)) ;
	}


	public static void performWebflow(Webflow test) {}
}
