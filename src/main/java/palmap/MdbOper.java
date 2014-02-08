package palmap;

import java.io.IOException;

import com.esri.arcgis.datasourcesGDB.AccessWorkspaceFactory;
import com.esri.arcgis.geodatabase.ICursor;
import com.esri.arcgis.geodatabase.IFeatureWorkspace;
import com.esri.arcgis.geodatabase.IQueryFilter;
import com.esri.arcgis.geodatabase.IRow;
import com.esri.arcgis.geodatabase.ITable;
import com.esri.arcgis.geodatabase.IWorkspace;
import com.esri.arcgis.geodatabase.IWorkspaceEdit;
import com.esri.arcgis.geodatabase.IWorkspaceFactory;
import com.esri.arcgis.geodatabase.QueryFilter;

public class MdbOper {
	
	private IFeatureWorkspace desFeatureWorkspace;
	private IFeatureWorkspace srcFeatureWorkspace;
	
	public MdbOper() {}
	
	public MdbOper (String srcPath, String desPath) throws IOException {
		srcFeatureWorkspace = getFeatureWorkspace(srcPath);
		desFeatureWorkspace = getFeatureWorkspace(desPath);
	}
	
		
	public IFeatureWorkspace getFeatureWorkspace(String path) throws IOException {
		IWorkspaceFactory iWorkspaceFactory = new AccessWorkspaceFactory();
		IWorkspace iWorkspace = iWorkspaceFactory.openFromFile(path, 0);
		IFeatureWorkspace iFeatureWorkspace = (IFeatureWorkspace) iWorkspace;
		return iFeatureWorkspace;
	}
	
	
	private long getOldUDID (String tableName) throws IOException {
		ICursor iCursor = getCursor(tableName);
		int index = iCursor.findField("UDID");
		IRow iRow = iCursor.nextRow();
		long oldvalue = (Integer)iRow.getValue(index);
		return oldvalue;
	}
	
	private ICursor getCursor(String tableName) throws IOException {
		ITable it = desFeatureWorkspace.openTable(tableName);
		ICursor iCursor = it.ITable_search(null, false);
		return iCursor;
	}
	
	public void setNewUDID (String tableName) throws IOException {
		//--得到新值
		ITable iTable = srcFeatureWorkspace.openTable(tableName);
		IQueryFilter iQueryFilter = new QueryFilter();
		String string = "[Navinfo ID] = "+getOldUDID(tableName);
		iQueryFilter.setWhereClause(string);
		ICursor iCursor = iTable.ITable_search(iQueryFilter, false);
		IRow iRow = iCursor.nextRow();
		if (iRow != null) {
			int newValue = (Integer)iRow.getValue(iTable.getFields().findField("UDID"));
			
			//--set新值
			ITable it = desFeatureWorkspace.openTable(tableName);
			IWorkspaceEdit iwe = (IWorkspaceEdit) desFeatureWorkspace;
			iwe.startEditing(true);
			iwe.startEditOperation();
			ICursor desCursor = it.update(null, false);
			int index = desCursor.findField("UDID");
			IRow desRow = desCursor.nextRow();
			//set值时，不能是long型，可以是int
			desRow.setValue(index, newValue);
			desCursor.updateRow(desRow);
			iwe.stopEditOperation();
			iwe.stopEditing(true);
		}
		
	}
	
}
