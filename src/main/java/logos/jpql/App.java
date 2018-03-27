package logos.jpql;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.Comment;
import entity.Post;
import entity.Tag;
import entity.enums.Status;

public class App 
{
    public static void main( String[] args ){
    	
    	EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysql");
    	EntityManager em = factory.createEntityManager();
    	em.getTransaction().begin();
    	
//    	addTags(em);
//    	addPost(em);
//    	addComment(em);
    	
    	List<Comment> comments = em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
//    	comments.forEach(c -> System.out.println(c));
    	
    	Comment com = em.createQuery("SELECT c FROM Comment c WHERE c.id = :id", Comment.class).setParameter("id", 40).getSingleResult();
//    	System.out.println(com);
    	
    	List<Post> posts = em.createQuery("SELECT p FROM Post p WHERE p.id > :post_id", Post.class).setParameter("post_id", 50).getResultList();
    	posts.forEach(p -> System.out.println(p));
    	
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
