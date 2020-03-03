package app;
/**
 * This class holds a (name, array of integer values) pair for an array. 
 * The name is a sequence of one or more letters. 
 */
public class Array {
	
	public String name;
	
	public int[] values;
	
	public Array(String name) {
        this.name = name;
        values = null;
    }
	
	public String toString() {
		if (values == null || values.length == 0) {
			return name + "=[ ]";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append("=[");
		sb.append(values[0]);
		for (int i=1; i < values.length; i++) {
			sb.append(',');
			sb.append(values[i]);
		}
		sb.append(']');
		return sb.toString();
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof Array)) {
			return false;
		}
		Array as = (Array)o;
		return name.equals(as.name);
	}
}
