package framework.utilities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import com.DIC.model.PackageModel;
import com.DIC.model.UserDetails;

public class DurationValidation {
	
	private static final Logger log = Logger.getLogger(DurationValidation.class.getName());
	
	
	public static boolean durationValidation(UserDetails userDetails,PackageModel packageModel)
	{
		LocalDate accountCreateDate=LocalDate.parse(userDetails.getCreate_date().toString());
		LocalDate currentDate = LocalDate.now();
		long daysBetween = ChronoUnit.DAYS.between(accountCreateDate, currentDate);
		
		System.out.println(" Duration days : "+daysBetween+"    "+packageModel.getPackDuration());
		
		if(daysBetween <= packageModel.getPackDuration())
		{
			return true;
		}else
		{
			return false;
		}
		
		
	}

}
