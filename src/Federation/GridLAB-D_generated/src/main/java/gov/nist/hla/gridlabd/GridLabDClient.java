package gov.nist.hla.gridlabd;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gov.nist.hla.gridlabd.exception.StatusCodeException;

public class GridLabDClient {
    private static final Logger log = LogManager.getLogger();
    
    final private HttpClient client;
    
    final private String host;
    
    final private int port;
    
    public GridLabDClient(String host, int port) {
        this.client = HttpClients.createDefault();
        this.host = host;
        this.port = port;
    }
    
    public void pauseat(String timeStamp)
            throws IOException {
        log.trace("pauseat {}", timeStamp);
        
        timeStamp = timeStamp.replaceAll(" ", "%20");
        // URLEncoder.encode(timeStamp, "UTF-8") produces characters that GridLAB-D does not recognize
        
        get("/control/pauseat=" + timeStamp);
        log.info("sent pauseat={} to {}:{}", timeStamp, host, port);
    }
    
    public void resume()
            throws IOException {
        log.trace("resume");
        get("/control/resume");
        log.info("sent resume to {}:{}", host, port);
    }
    
    public void shutdown()
            throws IOException {
        log.trace("shutdown");
        get("/control/shutdown");
        log.info("sent shutdown to {}:{}", host, port);
    }
    
    public boolean isPaused()
            throws IOException {
        log.trace("isPaused");
        return get("/raw/mainloop_state").equals("PAUSED");
    }
    
    public String getGlobalVariable(String variableName)
            throws IOException {
        log.trace("getGlobalVariable {}", variableName);
        return get("/raw/" + variableName);
    }
    
    public String getObjectProperty(String objectName, String propertyName)
            throws IOException {
        log.trace("getObjectProperty {} {}", objectName, propertyName);
        return get("/raw/" + objectName + "/" + propertyName);
    }
    
    // only properties of type double support unit conversion in GridLAB-D
    public double getObjectProperty(String objectName, String propertyName, String unit)
            throws IOException {
        log.trace("getObjectProperty {} {} {} (double)", objectName, propertyName, unit);
        
        String valueWithUnits;
        if (unit == null || unit.isEmpty()) {
            valueWithUnits = getObjectProperty(objectName, propertyName);
            log.debug("received {}.{}={}", objectName, propertyName, valueWithUnits);
        } else {
            String xmlResponse = get("/xml/" + objectName + "/" + propertyName + "%5B" + unit + "%5D");
            log.debug("XML response from GridLAB-D: {}", xmlResponse);
            // GridLAB-D response format:
            // <property>
            //   <object>object_name</object>
            //   <name>property_name</name>
            //   <value>value[ unit]</value>
            // </property>
            
            final String openingTag = "<value>";
            int beginIndex = xmlResponse.indexOf(openingTag);
            if (beginIndex == -1) {
                throw new IOException("GridLAB-D response did not include the " + openingTag + " element");
            }
            beginIndex += openingTag.length();
            
            final String closingTag = "</value>";
            int endIndex = xmlResponse.indexOf(closingTag, beginIndex);
            if (endIndex == -1) {
                throw new IOException("GridLAB-D response did not include the closing tag " + closingTag);
            }
            
            valueWithUnits = xmlResponse.substring(beginIndex, endIndex);
            log.debug("read value element as {}", valueWithUnits);
        }
        
        // GridLAB-D doubles are formatted as "value [unit]" where value matches %+lg and unit is optional
        int unitSeperatorIndex = valueWithUnits.indexOf(' ');
        if (unitSeperatorIndex < 0) {
            log.trace("no unit found in value from GridLAB-D");
            return Double.parseDouble(valueWithUnits);
        }
        return Double.parseDouble(valueWithUnits.substring(0, unitSeperatorIndex));
    }
    
    public void setObjectProperty(String objectName, String propertyName, String value)
            throws IOException {
        log.trace("setObjectProperty {} {} {}", objectName, propertyName, value);
        get("/raw/" + objectName + "/" + propertyName + "=" + value);
    }
    
    // only properties of type double support unit conversion in GridLAB-D
    public void setObjectProperty(String objectName, String propertyName, double value, String unit)
            throws IOException {
        log.trace("setObjectProperty {} {} {} {} (double)", objectName, propertyName, value, unit);
        if (unit == null || unit.isEmpty()) {
            setObjectProperty(objectName, propertyName, Double.toString(value));
        } else {
            get("/xml/" + objectName + "/" + propertyName + "=" + value + "%20" + unit);
        }
    }
    
    public String toString() {
        return this.getClass().getName() + "[host=" + host +", port=" + port + "]";
    }
    
    private String get(String path)
            throws IOException, StatusCodeException {
        log.trace("get {}", path);
        
        String uri = "http://" + host + ":" + port + path;
        HttpResponse response = client.execute(new HttpGet(uri));
        log.debug("sent get request {}", uri);
        
        int statusCode = response.getStatusLine().getStatusCode();
        String reasonPhrase = response.getStatusLine().getReasonPhrase();
        if (statusCode < 200 || statusCode >= 300) {
            throw new StatusCodeException(statusCode, reasonPhrase);
        }
        log.debug("received status line {}: {}", statusCode, reasonPhrase);

        HttpEntity entity = response.getEntity();
        if (entity == null) {
            throw new IOException("HTTP GET response did not include a message body");
        }
        return EntityUtils.toString(entity);
    }
}
