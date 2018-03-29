package entity;

import java.math.*;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity{
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "price", columnDefinition = "DECIMAL(7,2)")
	private BigDecimal price;
	
	@Column(name = "in_stock", length = 100)
	private int inStock;
	
	@OneToOne (mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Post post;

	@Override
	public String toString() {
		return "Product [name=" + name + ", description=" + description + ", price=" + price + ", inStock=" + inStock
				+ ", getId()=" + getId() + "]";
	}
	
}
