package com.DIC.Service;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.DIC.DAO.Impl.GeneralDAOImpl;
import com.DIC.DAO.Impl.UserDAOImpl;
import com.DIC.model.PackPriceModel;

@ManagedBean(name="packSettingService")
@ViewScoped
public class PackSettingService {
	
	private static final Logger log = Logger.getLogger(PackSettingService.class.getName());
	
	private int packageId;
	
	private String packName;
	private int packType;
	private int listLimit;
	private int cost;
	private int duration;
	
	private PackPriceModel packageDetails;
	private String updateResult;
	
	
	GeneralDAOImpl gDao;
	
	@PostConstruct 
    public void init()
    {
		gDao=new GeneralDAOImpl();
    }
	
	public void getPackDetails() 
	{  
		this.packageDetails=null;
		this.updateResult="";
		packageDetails=gDao.getPackageById(packageId);
		
		if(packageDetails.getPackName() != null)
		{
		
					packName=packageDetails.getPackName();
					packType=packageDetails.getPackType();
					listLimit=packageDetails.getListLimit();
					cost=packageDetails.getCost();
					duration=packageDetails.getDuration();
					
					System.out.println(" Package Name : "+packageDetails.getPackName());
		}
		else
		{
			updateResult="Package id dose not exist.";
			System.out.println("*****************Package details null ******************");
			this.packName=null;
			this.packType=0;
			this.listLimit=0;
			this.cost=0;
			this.duration=0;
			
			
		}
	}
	
	public void savePackage() 
	{
		if(packName!=null && packType!=0 && listLimit!=0 && cost!=0 && duration!=0)
		{
		
				this.updateResult="";
				int updateCount=gDao.editPackageDetais(packageId, packName,packType,listLimit,cost,duration);
				if(updateCount>0)
				{
					updateResult="Successup updated package details..";
					
				}
				else
				{
					updateResult="There is an error occured, Please contact Support team.";
				}
		}else
		{
			this.updateResult="Please provide details";
		}
	}
	
	
		public void clear()
		{
			this.updateResult="";
			this.packageId=0;
		}


	public String getUpdateResult() {
		return updateResult;
	}


	public void setUpdateResult(String updateResult) {
		this.updateResult = updateResult;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public PackPriceModel getPackageDetails() {
		return packageDetails;
	}

	public void setPackageDetails(PackPriceModel packageDetails) {
		this.packageDetails = packageDetails;
	}

	public String getPackName() {
		return packName;
	}

	public int getPackType() {
		return packType;
	}

	public int getListLimit() {
		return listLimit;
	}

	public int getCost() {
		return cost;
	}

	public int getDuration() {
		return duration;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public void setPackType(int packType) {
		this.packType = packType;
	}

	public void setListLimit(int listLimit) {
		this.listLimit = listLimit;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	

}
