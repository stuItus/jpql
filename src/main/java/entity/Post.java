package entity;

import java.util.*;

import javax.persistence.*;

import entity.enums.Status;
import lombok.*;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
@Setter
public class Post extends BaseEntity {

	@Column(name = "title", length = 100)
	private String title;

	@Column(name = "content", length = 100)
	private String content;

	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToMany(mappedBy = "post")
	private List<Comment> comments = new ArrayList<Comment>();
	
	@ManyToMany
	@JoinTable(name = "post_tag",
				joinColumns = @JoinColumn(name = "post_id"),
				inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags = new ArrayList<Tag>();
	
	@Override
	public String toString() {
		return "Post [title=" + title + ", content=" + content + ", status=" + status + ", getId()=" + getId() + "]";
	}
	
}