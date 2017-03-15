package com.specmate.connectors.hpconnector;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.RestClient;
import com.specmate.common.RestResult;
import com.specmate.common.SpecmateException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;

@Component(service = IRequirementsSource.class, immediate = true)
public class HPConnector implements IRequirementsSource {

	private static final String CONNECTOR_URL = "http://localhost:8081";
	private RestClient restClient;
	private LogService logService;

	public HPConnector() {
		this.restClient = new RestClient(CONNECTOR_URL);
	}

	@Override
	public IContainer getRequirements() throws SpecmateException {
		RestResult<JSONArray> result;
		try {
			result = restClient.getList("/getRequirements");
		} catch (Exception e) {
			logService.log(LogService.LOG_ERROR, "Could not retrieve requirements from HP interface.");
			return null;
		}
		Response response = result.getResponse();
		JSONArray requirementsList = result.getPayload();
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new SpecmateException("Error while retrieving requirements list from HP Interface");
		}

		Folder folder = BaseFactory.eINSTANCE.createFolder();
		for (int i = 0; i < requirementsList.length(); i++) {
			JSONObject jsonRequirement = requirementsList.getJSONObject(i);
			Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
			requirement.setName(jsonRequirement.getString("title"));
			requirement.setId(jsonRequirement.getString("title"));
			requirement.setExtId(jsonRequirement.getString("extId"));
			requirement.setDescription(jsonRequirement.getString("description"));
			// TODO other fields
			folder.getContents().add(requirement);
		}
		return folder;
	}

	@Override
	public String getId() {
		return "HP-Import";
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
