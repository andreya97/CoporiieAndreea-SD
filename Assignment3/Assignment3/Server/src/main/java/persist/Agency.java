package persist;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import entity.Article;
import entity.RegisteredUser;
import mapper.Mapper;

public class Agency {
	private List<RegisteredUser> allUsers;
	private List<Article> allArticles;
	static String filename = "D:\\Scoala\\An III sem 2\\Assignment3\\";
	private static Agency instance;
	
	public Agency() throws FileNotFoundException
	{
		deserialize();
	}
	
	public static Agency getInstance() throws FileNotFoundException{
		if(instance==null){
			instance = new Agency();
		}
		return instance;
	}
	
	private List<RegisteredUser> getUsers() throws FileNotFoundException
	{
		JsonReader reader = new JsonReader(new FileReader(filename + "users.json"));
		return Mapper.usersFromJson(reader);
	}
	
	private List<Article> getArticles() throws FileNotFoundException
	{
		JsonReader reader = new JsonReader(new FileReader(filename + "articles.json"));
		return Mapper.articlesFromJson(reader);
	}
	
	public void deserialize() throws FileNotFoundException
	{
		this.allUsers = getUsers();
		this.allArticles = getArticles();
	}
	
	public void serialize() throws IOException{
		try (Writer writer = new FileWriter(filename + "articles.json")) {
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(allArticles, writer);
		}
		
		try (Writer writer = new FileWriter(filename + "users.json")) {
		    Gson gson = new GsonBuilder().create();
		    gson.toJson(allUsers, writer);
		};
		
	}
	
	
	
	
	
	public boolean existsUser(RegisteredUser user)
	{
		String username = user.getUsername();
		for (RegisteredUser u : allUsers)
		{
			if (u.getUsername().equals(username))
				return true;
		}
		return false;
	}
	
	public RegisteredUser getUser(String user)
	{
		for (RegisteredUser u : allUsers)
		{
			if (u.getUsername().equals(user))
				return u;
		}
		return null;
		
	}
	
	public void addUser(RegisteredUser user)
	{
		if (!existsUser(user))
			allUsers.add(user);
		else 
			System.out.println("User already exists!");
	}
	
	
	
	public Article getArticle(String title)
	{
		for (Article a : allArticles)
		{
			if (a.getTitle().equals(title))
				return a;
		}
		return null;
		
	}
	
	public boolean existsArticle(Article article)
	{
		String title = article.getTitle();
		for (Article a : allArticles)
		{
			if (a.getTitle().equals(title))
				return true;
		}
		return false;
	}
	
	public void addArticle(Article article)
	{
		if (!existsArticle(article))
			allArticles.add(article);
		else 
			System.out.println("Article already exists!");
	}
	
	public int getIndexOfArticle(Article article)
	{
		String title = article.getTitle();
		int i = 0;
		for (Article a : allArticles)
		{
			if (a.getTitle().equals(title))
				return i;
			i++;
		}
		return -1;
	}
	
	public boolean deleteArticle(Article article)
	{
		if (existsArticle(article))
		{
			int i = getIndexOfArticle(article);
			if (i != -1)
			allArticles.remove(i);
			return true;
		}
		else
			System.out.println("Article doesn't exist!");
		return false;
	}
	
	public void updateArticle(Article oldarticle, Article newarticle)
	{
		if (deleteArticle(oldarticle))
		{
			System.out.println("deleted");
		}
		allArticles.add(newarticle);
	}
	
	
	public List<RegisteredUser> getAllUsers()
	{
		return allUsers;
	}
	public List<Article> getAllArticles()
	{
		return allArticles;
	}
	
}
