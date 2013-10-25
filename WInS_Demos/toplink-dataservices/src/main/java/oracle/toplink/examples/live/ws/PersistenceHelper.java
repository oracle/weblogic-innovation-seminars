/*******************************************************************************
 * Copyright (c) 2013 Oracle Corporation
 ******************************************************************************/
package oracle.toplink.examples.live.ws;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ServiceLoader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import oracle.toplink.dataservices.DataServicePersistenceContext;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.jpa.rs.PersistenceContextFactory;
import org.eclipse.persistence.jpa.rs.PersistenceContextFactoryProvider;
import org.eclipse.persistence.oxm.MediaType;

/**
 * Helper class providing access to TopLink (EclipseLink) JSON marshalling and
 * data service context.
 */
public class PersistenceHelper {

    private static JAXBContext context;

    private static JAXBContext getContext() {
        if (context == null) {
            try {
                context = JAXBContextFactory.createContext(new Class[] { SubscriptionRequest.class }, null);
            } catch (JAXBException e) {
                throw new RuntimeException("Could not create JAXBContext for SubscriptionRequest", e);
            }
        }
        return context;
    }

    /**
     * Helper method that takes an incoming message and converts it to a
     * {@link SubscriptionRequest}.
     */
    public static SubscriptionRequest unmarshall(String json) throws JAXBException {
        Unmarshaller unmarshaller = getContext().createUnmarshaller();

        unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
        StringReader reader = new StringReader(json);
        StreamSource inSource = new StreamSource(reader);
        JAXBElement<SubscriptionRequest> requestElement = unmarshaller.unmarshal(inSource, SubscriptionRequest.class);
        SubscriptionRequest message = requestElement.getValue();

        return message;
    }

    /**
     * Helper method that takes an incoming message and converts it to a
     * {@link SubscriptionRequest}.
     */
    public static String marshall(SubscriptionRequest request) throws JAXBException {
        Marshaller marshaller = getContext().createMarshaller();

        marshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        marshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
        StringWriter writer = new StringWriter();
        marshaller.marshal(request, writer);

        return writer.toString();
    }

    /**
     * Initializes and stores the associated
     * {@link DataServicePersistenceContext} associated with this web socket.
     * @throws URISyntaxException 
     */
    protected static DataServicePersistenceContext lookupPersistenceContext() throws URISyntaxException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ServiceLoader<PersistenceContextFactoryProvider> contextFactoryLoader = ServiceLoader.load(PersistenceContextFactoryProvider.class, cl);
        PersistenceContextFactory factory = null;

        for (PersistenceContextFactoryProvider provider : contextFactoryLoader) {
            factory = provider.getPersistenceContextFactory(null);
            if (factory != null) {
                break;
            }
        }

        if (factory == null) {
            throw new RuntimeException("Cannot load persistence factory fot the Auction application.");
        }

        return (DataServicePersistenceContext) factory.get("auction", new URI("persistence/auction"), "v1.0", null);
    }

}