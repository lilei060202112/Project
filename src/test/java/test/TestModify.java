package test;
import java.io.IOException;

import com.esri.arcgis.system.EngineInitializer;

import palmap.ModifyUdidUtil;

/*
 * ����Ŀ�����Լ����ٻ��䣬��mdb�еı�ʹ���α�õ�ֵ��ʹ���α�ʵ�ֲ�ѯ�����¡�setֵ��editsession��
 * java7����Files�������
 */
public class TestModify {

	public static void main(String[] args) throws IOException {
		EngineInitializer.initializeEngine();
		initializeArcGISLicenses();
		ModifyUdidUtil mum = new ModifyUdidUtil(args[0], args[1]);
		mum.modifyMdb();
		System.out.println("�޸Ľ���,����:"+mum.ds.count);
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
