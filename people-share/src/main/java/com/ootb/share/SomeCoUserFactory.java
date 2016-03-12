package com.ootb.share;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import org.alfresco.web.site.SlingshotUserFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.surf.FrameworkUtil;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.exception.ConnectorServiceException;
import org.springframework.extensions.surf.exception.UserFactoryException;
import org.springframework.extensions.surf.site.AlfrescoUser;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.surf.util.StringBuilderWriter;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.connector.Connector;
import org.springframework.extensions.webscripts.connector.ConnectorContext;
import org.springframework.extensions.webscripts.connector.HttpMethod;
import org.springframework.extensions.webscripts.connector.Response;
import org.springframework.extensions.webscripts.json.JSONWriter;

public class SomeCoUserFactory extends SlingshotUserFactory {
	public static final String SCP_BIRTH_DATE = "{http://www.someco.com/model/people/1.0}birthDate";
	public static String SCP_SHIRT_SIZE = "{http://www.someco.com/model/people/1.0}shirtSize"; 
	public static String SCP_SIGNIFICANT_OTHER = "{http://www.someco.com/model/people/1.0}significantOther";
	public static String SCP_LIKES_NEIL_DIAMOND = "{http://www.someco.com/model/people/1.0}likesNeilDiamond";
	public static String PROP_SCP_BIRTH_DATE = "birthDate";
	public static String PROP_SCP_SHIRT_SIZE = "shirtSize";
	public static String PROP_SCP_SIGNIFICANT_OTHER = "significantOther";
	public static String PROP_SCP_LIKES_NEIL_DIAMOND = "likesNeilDiamond";
	
	@Override
    protected AlfrescoUser constructUser(JSONObject properties, Map<String, Boolean> capabilities,
            Map<String, Boolean> immutability) throws JSONException {
        AlfrescoUser user = super.constructUser(properties, capabilities, immutability);
        user.setProperty(PROP_SCP_BIRTH_DATE, properties.has(SCP_BIRTH_DATE) ? properties.getString(SCP_BIRTH_DATE) : null);
        user.setProperty(PROP_SCP_SHIRT_SIZE, properties.has(SCP_SHIRT_SIZE) ? properties.getString(SCP_SHIRT_SIZE) : null);
        user.setProperty(PROP_SCP_SIGNIFICANT_OTHER, properties.has(SCP_SIGNIFICANT_OTHER) ? properties.getString(SCP_SIGNIFICANT_OTHER) : null);
        user.setProperty(PROP_SCP_LIKES_NEIL_DIAMOND, properties.has(SCP_LIKES_NEIL_DIAMOND) ? properties.getString(SCP_LIKES_NEIL_DIAMOND) : null);
        return user;
    }
	
	@SuppressWarnings("deprecation")
	@Override
    public void saveUser(AlfrescoUser user) throws UserFactoryException {
        RequestContext context = (RequestContext)ThreadLocalRequestContext.getRequestContext();
        if (!context.getUserId().equals(user.getId())) {
            throw new UserFactoryException("Unable to persist user with different Id that current Id.");
        }
        
        StringBuilderWriter buf = new StringBuilderWriter(512);
        JSONWriter writer = new JSONWriter(buf);
        
        try {
            writer.startObject();
            
            writer.writeValue("username", user.getId());
            
            writer.startValue("properties");
            writer.startObject();
            writer.writeValue(CM_FIRSTNAME, user.getFirstName());
            writer.writeValue(CM_LASTNAME, user.getLastName());
            writer.writeValue(CM_JOBTITLE, user.getJobTitle());
            writer.writeValue(CM_ORGANIZATION, user.getOrganization());
            writer.writeValue(CM_LOCATION, user.getLocation());
            writer.writeValue(CM_EMAIL, user.getEmail());
            writer.writeValue(CM_TELEPHONE, user.getTelephone());
            writer.writeValue(CM_MOBILE, user.getMobilePhone());
            writer.writeValue(CM_SKYPE, user.getSkype());
            writer.writeValue(CM_INSTANTMSG, user.getInstantMsg());
            writer.writeValue(CM_GOOGLEUSERNAME, user.getGoogleUsername());
            writer.writeValue(CM_COMPANYADDRESS1, user.getCompanyAddress1());
            writer.writeValue(CM_COMPANYADDRESS2, user.getCompanyAddress2());
            writer.writeValue(CM_COMPANYADDRESS3, user.getCompanyAddress3());
            writer.writeValue(CM_COMPANYPOSTCODE, user.getCompanyPostcode());
            writer.writeValue(CM_COMPANYFAX, user.getCompanyFax());
            writer.writeValue(CM_COMPANYEMAIL, user.getCompanyEmail());
            writer.writeValue(CM_COMPANYTELEPHONE, user.getCompanyTelephone());
            
            // START SomeCo Specific properties
            writer.writeValue(SCP_BIRTH_DATE, user.getStringProperty(PROP_SCP_BIRTH_DATE));
            writer.writeValue(SCP_SHIRT_SIZE, user.getStringProperty(PROP_SCP_SHIRT_SIZE));
            writer.writeValue(SCP_SIGNIFICANT_OTHER, user.getStringProperty(PROP_SCP_SIGNIFICANT_OTHER));
            writer.writeValue(SCP_LIKES_NEIL_DIAMOND, user.getStringProperty(PROP_SCP_LIKES_NEIL_DIAMOND));
            // END SomeCo Specific properties
            
            writer.endObject();
		    writer.endValue();
		    
		    writer.startValue("content");
		    writer.startObject();
		    writer.writeValue(CM_PERSONDESCRIPTION, user.getBiography());
		    writer.endObject();
		    writer.endValue();
		    
		    writer.endObject();
		    
		    Connector conn = FrameworkUtil.getConnector(context, ALFRESCO_ENDPOINT_ID);
		    ConnectorContext c = new ConnectorContext(HttpMethod.POST);
		    c.setContentType("application/json");
		    Response res = conn.call("/slingshot/profile/userprofile", c,
		            new ByteArrayInputStream(buf.toString().getBytes()));
		    if (Status.STATUS_OK != res.getStatus().getCode()) {
		        throw new UserFactoryException("Remote error during User save: " + res.getStatus().getMessage());
		    }
		} catch (IOException ioErr) {
		    throw new UserFactoryException("IO error during User save: " + ioErr.getMessage(), ioErr);
		} catch (ConnectorServiceException cse)	{
		    throw new UserFactoryException("Configuration error during User save: " + cse.getMessage(), cse);
		}
	}
}
