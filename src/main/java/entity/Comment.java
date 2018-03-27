package entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Getter
@Setter
public class Comment extends BaseEntity {
	
	@Column(name = "coment")
	private String coment;
	
	@Column(name = "author")
	private String author;
	
	@ManyToOne
	@JoinColumn(name = "post_Id")
	private Post post;
	
	
	@Override
	public String toString() {
		return "Comment [coment=" + coment + ", author=" + author + ", getId()=" + getId() + "]";
	}
	
}
