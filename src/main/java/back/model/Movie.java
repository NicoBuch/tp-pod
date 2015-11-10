package back.model;

import java.io.IOException;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import service.ActorsArrayDeserializer;
import service.FlexibleFloatDeserializer;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements DataSerializable {
	
	@JsonDeserialize(using = ActorsArrayDeserializer.class)
	@JsonProperty("Actors")
	private String[] actors;
	
	@JsonDeserialize(using = FlexibleFloatDeserializer.class)
	@JsonProperty("imdbVotes")
	private int votes;
	
	@JsonDeserialize(using = FlexibleFloatDeserializer.class)
	@JsonProperty("Metascore")
	private int metascore;
	
	@JsonProperty("Title")
	private String title;
	
	@JsonProperty("Year")
	private String year;
	
	@JsonProperty("Director")
	private String director;
	
	@JsonProperty("Type")
	private String type;
	
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
		year = in.readUTF();
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
		out.writeUTF(year);
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isMovie(){
		return "movie".equals(type);
	}
	
		

}
