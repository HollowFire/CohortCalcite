package org.apache.calcite.cohort.sql;

import org.apache.calcite.sql.SqlCohortNode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A <code>CohortFilterNode</code> is a node of a parse tree which represents a filter in cohort query.
 */


public class CohortFilterNode extends SqlCohortNode {
	
    private String type;
    private String dimension;
    private List<String> values;

    private boolean birthFlag = false;	

	public CohortFilterNode(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDim() {
		return dimension;
	}
	
	public void setDim(String dimension) {
		this.dimension = dimension;
	}
	
	public List<String> getValues() {
		return values;
	}
	
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public boolean getBirthFlag() {
		return birthFlag;
	}
	
	public void setBirthFlag(boolean birthFlag) {
		this.birthFlag = birthFlag;
	}
		
/*	public String toString() {
		return getJson().toString();
	}
	
	public JSONObject getJson() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", type);
        if (dimension != null) {
            map.put("dimension", dimension);
        }
        if (values != null) {
        	JSONArray valueArray = new JSONArray();
            for (String x: values) {
            	valueArray.put(x);
            }
            map.put("values", valueArray);
        }
        return new JSONObject(map);
    }
*/	
}
		
	
	
	