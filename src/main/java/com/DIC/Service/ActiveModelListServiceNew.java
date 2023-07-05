package com.DIC.Service;

import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.DIC.DAO.Impl.ConnectionDAOImpl;
import com.DIC.model.ConnectorMode;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


@ManagedBean(name="activeModelListServiceNew")
@ViewScoped
public class ActiveModelListServiceNew implements Serializable{
	
	private static final long serialVersionUID = 6529685098267757690L;
	
	

	private List<ConnectorMode> active;
	private List<ConnectorMode> filteredProducts;

	
	
	




	ConnectionDAOImpl dao;
	    public ActiveModelListServiceNew() {
		 
	       dao=new ConnectionDAOImpl();
	       active =dao.getActiveModelList();
	       
	       filteredProducts=new ArrayList<>();
	    }
	    

	    public List<ConnectorMode> getActive() {
			return active;
		}


		public void setActive(List<ConnectorMode> active) {
			this.active = active;
		}
		

		public List<ConnectorMode> getFilteredProducts() {
			return filteredProducts;
		}


		public void setFilteredProducts(List<ConnectorMode> filteredProducts) {
			this.filteredProducts = filteredProducts;
		}
	
	    

		

}
