package test;
import java.io.IOException;

import com.esri.arcgis.system.EngineInitializer;

import palmap.ModifyUdidUtil;

/*
 * 本项目帮助自己快速回忆，打开mdb中的表、使用游标得到值、使用游标实现查询，更新、set值、editsession、
 * java7特性Files类遍历、
 */
public class TestModify {

	public static void main(String[] args) throws IOException {
		EngineInitializer.initializeEngine();
		initializeArcGISLicenses();
		ModifyUdidUtil mum = new ModifyUdidUtil(args[0], args[1]);
		mum.modifyMdb();
		System.out.println("修改结束,共计:"+mum.ds.count);
	}
	
	public static void initializeArcGISLicenses() {
		try {
			com.esri.arcgis.system.AoInitialize ao = new 
					com.esri.arcgis.system.AoInitialize();
			if (ao.isProductCodeAvailable(com.esri.arcgis
					.system.esriLicenseProductCode.
					esriLicenseProductCodeAdvanced) 
					== com.esri.arcgis.system.esriLicenseStatus.
					esriLicenseAvailable)
				ao.initialize(com.esri.arcgis.system.
						esriLicenseProductCode.esriLicenseProductCodeAdvanced);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
