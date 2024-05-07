package org.rainy.minis.core.resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class ClassPathXmlResource implements Resource {

    private Iterator<Element> elementIterator;

    public ClassPathXmlResource(String xmlPath) {
        SAXReader saxReader = new SAXReader();
        URL url = this.getClass().getClassLoader().getResource(xmlPath);
        try {
            Document document = saxReader.read(url);
            Element rootElement = document.getRootElement();
            this.elementIterator = rootElement.elementIterator();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Element next() {
        return this.elementIterator.next();
    }
}
