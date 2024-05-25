package com.DIC.Service;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.model.PackPriceModel;

@ManagedBean(name="packPriceService")
@ViewScoped
public class PackPriceService {
	
	private static final Logger log = Logger.getLogger(PackPriceService.class.getName());
	

	private List<PackPriceModel> packPriceModelList;
	
	
	GeneralDAOImpl gdao;
	
	@PostConstruct 
    public void init()
    {
		gdao=new GeneralDAOImpl();
		packPriceModelList=gdao.getPackagePriceDetails();
    }

	public List<PackPriceModel> getPackPriceModelList() {
		return packPriceModelList;
	}

	public void setPackPriceModelList(List<PackPriceModel> packPriceModelList) {
		this.packPriceModelList = packPriceModelList;
	}

	
	
	
	
	
}
