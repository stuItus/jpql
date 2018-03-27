package entity;

import java.util.*;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "tag")
@NoArgsConstructor
@Getter
@Setter

public class Tag extends BaseEntity {

	@Column(name = "name")
	private String name;
	
	@ManyToMany(mappedBy = "tags")
	private List<Post> posts = new ArrayList<Post>();

	@Override
	public String toString() {
		return "Tag [name=" + name + ", getId()=" + getId() + "]";
	}
	
}
