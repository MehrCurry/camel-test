/**
 * Created 03.11.2011
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;

import de.gzockoll.pdfcategorizer.DocumentInfo;
import de.gzockoll.pdfcategorizer.FileScanner;

/**
 * @author Guido Zockoll
 * 
 */
public class PdfProcessor implements Processor {
    FileScanner scanner = new FileScanner();

    @Override
    public void process(Exchange exchange) throws Exception {
        GenericFile gf = (GenericFile) exchange.getIn().getBody();
        DocumentInfo info = scanner.getInfo((File) gf.getFile());
        for (Method m : info.getClass().getMethods()) {
            if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
                Object result;
                if ((result = m.invoke(info)) != null)
                    exchange.setProperty(m.getName().substring(3), result.toString());
            }

        }
    }
}
