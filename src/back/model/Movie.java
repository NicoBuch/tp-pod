package back.model;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import com.sun.xml.internal.fastinfoset.stax.events.ReadIterator;

public class Movie implements DataSerializable {
	
	private String[] actors;
	private int votes;
	private int metascore;
	private String title;
	private int year;
	private String director;
	
	// mantener el orden que se hizo en el write!
	public void readData(ObjectDataInput in) throws IOException 
	{
		int actorsLength = in.readInt();
		actors = new String[actorsLength];
		for(int i = 0; i < actorsLength; i++) {
			actors[i] = in.readUTF();
		}
		votes = in.readInt();
		metascore = in.readInt();
		title = in.readUTF();
		year = in.readInt();
		director = in.readUTF();
	}

	// mantener el orden que se hizo en el read!
	public void writeData(ObjectDataOutput out) throws IOException 
	{
		out.writeInt(actors.length);
		for(String actor : actors){
			out.writeUTF(actor);
		}
		out.writeInt(votes);
		out.writeInt(metascore);
		out.writeUTF(title);
		out.writeInt(year);
		out.writeUTF(director);
		
	}

    public String toString() 
    {
    	return title;
    }
	
	
	public String[] getActors() {
		return actors;
	}
	public void setActors(String[] actors) {
		this.actors = actors;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	public int getMetascore() {
		return metascore;
	}
	public void setMetascore(int metascore) {
		this.metascore = metascore;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	
		

}
