package com.DIC.model;

import java.io.Serializable;

public class ConnectorMode implements Serializable{
	
	private long datasetId;
	private String serverHost;
	private String serverPort;
	private String modelName;
	private String model_register_env;
	private String source_db;
	private String unify_groups;
	
	
	
	public ConnectorMode() {
    }
	
	
	public long getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(long datasetId) {
		this.datasetId = datasetId;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModel_register_env() {
		return model_register_env;
	}

	public void setModel_register_env(String model_register_env) {
		this.model_register_env = model_register_env;
	}

	public String getSource_db() {
		return source_db;
	}

	public void setSource_db(String source_db) {
		this.source_db = source_db;
	}

	public String getUnify_groups() {
		return unify_groups;
	}

	public void setUnify_groups(String unify_groups) {
		this.unify_groups = unify_groups;
	}

	

}
