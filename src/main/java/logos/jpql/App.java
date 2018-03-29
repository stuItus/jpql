package logos.jpql;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import entity.Comment;
import entity.Post;
import entity.Product;
import entity.Tag;
import entity.enums.Status;

public class App 
{
    public static void main( String[] args ){
    	
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysql");
    	EntityManager em = factory.createEntityManager();
    	em.getTransaction().begin();
    	
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Post> query = cb.createQuery(Post.class);
    	Root<Post> root = query.from(Post.class);
    	query.select(root);
    	
//    	Expression<Integer> idExpression = root.get("id");
//    	Predicate idPredicate = cb.greaterThan(idExpression, 85);
//    	query.where(idPredicate);
    	
    	Expression<Integer> idExpression = root.get("id");
    	Predicate idMin = cb.greaterThanOrEqualTo(idExpression, 40);
    	Predicate idMax = cb.lessThanOrEqualTo(idExpression, 60);
    	Predicate idAll = cb.and(idMin, idMax);
//    	query.where(idAll);
    	
    	Join<Post, Product> postJoinProduct = root.join("product");
    	Expression<BigDecimal> priceExpression = postJoinProduct.get("price");
    	Predicate pricePredicate = cb.between(priceExpression, new BigDecimal("30.00"), new BigDecimal("50.00"));
//    	query.where(pricePredicate);
    	
    	Expression<String> productNameExpression = postJoinProduct.get("name");
    	Expression<Integer> idExpression2 = root.get("id");
    	Predicate productNamePredicate = cb.like(productNameExpression, "%4");
    	Predicate moreThanTen = cb.greaterThanOrEqualTo(idExpression2, 10);
    	Predicate allFourLessTen = cb.and(productNamePredicate, moreThanTen);
    	query.where(allFourLessTen);

    	List<Post> posts = em.createQuery(query).getResultList();
    	posts.forEach(p -> System.out.println(p));
    	
    	
//    	addTags(em);
//    	addPost(em);
//    	addComment(em);
    	
//    	List<Comment> comments = em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
//    	comments.forEach(c -> System.out.println(c));
    	
//    	Comment com = em.createQuery("SELECT c FROM Comment c WHERE c.id = :id", Comment.class).setParameter("id", 40).getSingleResult();
//    	System.out.println(com);
    	
//    	List<Post> posts = em.createQuery("SELECT p FROM Post p WHERE p.id > :post_id", Post.class).setParameter("post_id", 50).getResultList();
//    	posts.forEach(p -> System.out.println(p));
    	
//    	List<Post> posts = em.createQuery("SELECT p FROM Post p WHERE p.id IN (:ids)", Post.class).setParameter("ids", Arrays.asList(2, 56, 3, 76 , 99, 34)).getResultList();
//    	posts.forEach(p -> System.out.println(p));
    	
//    	List<Post> posts = em.createQuery("SELECT p FROM Post p WHERE p.title LIKE :post_title", Post.class).setParameter("post_title", "%8_").getResultList();
//    	posts.forEach(p -> System.out.println(p));
    	
//    	List<Post> posts = em.createQuery("SELECT p FROM Post p WHERE p.id BETWEEN :first AND :last", Post.class).setParameter("first", 76).setParameter("last", 82).getResultList();
//    	posts.forEach(p -> System.out.println(p));
    	
//    	Post post = em.createQuery("SELECT p FROM Post p WHERE p.id = 5", Post.class).getSingleResult();
//    	System.out.println(post);
    	
//    	Post post = em.createQuery("SELECT p FROM Post p RIGHT JOIN FETCH p.product pp WHERE p.id = :id", Post.class).setParameter("id", 9).getSingleResult();
//    	System.out.println(post);
//    	System.out.println(post.getProduct());
    	
    	// *****************************************************//
//    	Agreg functions
//    	Long count = em.createQuery("SELECT COUNT(c.id) FROM Comment c", Long.class).getSingleResult();
//    	System.out.println("Count :"+count);
//    	Long sum = em.createQuery("SELECT SUM(c.id) FROM Comment c", Long.class).getSingleResult();
//    	System.out.println("Sum :"+ sum);
//    	Double avg = em.createQuery("SELECT AVG(c.id) FROM Comment c", Double.class).getSingleResult();
//    	System.out.println("Average :"+avg);
//    	Integer max = em.createQuery("SELECT MAX(c.id) FROM Comment c", Integer.class).getSingleResult();
//    	System.out.println("Max :"+max);
//    	Integer min = em.createQuery("SELECT MIN(c.id) FROM Comment c", Integer.class).getSingleResult();
//    	System.out.println("Min :"+min);
    	
    	em.getTransaction().commit();
    	em.close();
    	factory.close();
    }
    
    private static void addTags(EntityManager em) {
    	List<String> tags = new ArrayList<>();
    	tags.add("java");
    	tags.add("sql");
    	tags.add("orm");
    	tags.add("jpa");
    	tags.add("sts");
    	tags.add("git");
    	
    	for (int i = 0; i < tags.size(); i++) {
			Tag tag = new Tag();
			tag.setName(tags.get(i));
			em.persist(tag);
		}
    }
    
    private static void addPost(EntityManager em) {
    	for (int i = 1; i < 100; i++) {
			Post post = new Post();
			post.setTitle("Post title #"+i);
			post.setContent("Post content#"+i);
			
			if (i % 2 == 0) post.setStatus(Status.DRAFT); 
			if (i % 2 == 1) post.setStatus(Status.PUBLISH); 
			
			Product product = new Product();
			product.setName("Product name #" + i);
			product.setDescription("Product description #"+i);
			product.setPrice(new BigDecimal(i + 10 + ".00"));
			product.setInStock(15 + i);
			
			post.setProduct(product);
			
			em.persist(post);
			
			List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
			post.setTags(tags);
    	}
    }
    
    private static void addComment(EntityManager em) {
    	for (int i = 1; i < 100; i++) {
    		Post post = (Post) em.createQuery("SELECT p FROM Post p WHERE p.id = :id", Post.class).setParameter("id", i).getSingleResult();
			
    		Comment comment = new Comment();
    		comment.setAuthor("Author #"+i);
    		comment.setComent("The best comment #"+i);
    		comment.setPost(post);
    		
    		em.persist(comment);
		}
    }
}
