package org.hiveadmin.hive.beans;

import java.util.List;
import java.util.Map;
public class Partition {
	private List<Columns>partition_spec;

	private String location;

	public List<Columns> getPartition_spec() {
		return partition_spec;
	}
	public void setPartition_spec(List<Columns> partition_spec) {
		this.partition_spec = partition_spec;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	

}
