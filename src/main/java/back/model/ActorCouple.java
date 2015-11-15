package back.model;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class ActorCouple implements DataSerializable{
	
	private String actor2;
	private String actor1;
	
	public ActorCouple() {
	}
	
	public ActorCouple(String actor1, String actor2) {
		this.actor1 = actor1;
		this.actor2 = actor2;
	}

	public String getActor1() {
		return actor1;
	}

	public void setActor1(String actor1) {
		this.actor1 = actor1;
	}

	public String getActor2() {
		return actor2;
	}

	public void setActor2(String actor2) {
		this.actor2 = actor2;
	}


	public void readData(ObjectDataInput in) throws IOException {
		actor1 = in.readUTF();
		actor2 = in.readUTF();
		
	}

	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeUTF(actor1);
		out.writeUTF(actor2);		
	}
	
	@Override
	public String toString() {
		return actor1 + " y " + actor2;
	}

	@Override
	public int hashCode() {
	    int res = 17;
	    res = res * 31 + Math.min(actor1.hashCode(), actor2.hashCode());
	    res = res * 31 + Math.max(actor1.hashCode(), actor2.hashCode());
	    return res;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActorCouple other = (ActorCouple) obj;
		if (actor1 == null) {
			if (other.actor1 != null)
				return false;
		}
		if (actor2 == null) {
			if (other.actor2 != null)
				return false;
		}
		if(actor1 != null && actor2 != null){
			if((actor1.equals(other.actor1) && actor2.equals(other.actor2)) || (actor1.equals(other.actor2) && actor2.equals(other.actor1))){
				return true;
			}
		}
		return false;
	}

	
}
