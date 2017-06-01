package ag.it.solution.notifystudent.restConnection;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by mariano on 01/11/2016.
 */
public class RestConnection {

    private RestTemplate restTemplate;

    public RestConnection( ){
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        this.restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    /**
     * @return the restTemplate
     */
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     * @param restTemplate the restTemplate to set
     */
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



}
