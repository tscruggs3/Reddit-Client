/**
 * @author Jordan Sybesma
 * 
 * A class for storing data about a subreddit
 */

import java.util.List;
import java.util.ArrayList;

public class Subreddit {

	private String title;
	private List<PostPreview> postList;
	private String nextPageURL;

	/**
	 * Constructor
	 * @param title The title of the subreddit
	 */
	public Subreddit(String title, String nextPageURL) {
		this.title = title;
		this.nextPageURL = nextPageURL;
		postList = new ArrayList<PostPreview>();
	}

	/**
	 * Adds a post preview to the subreddit
	 * @param newPostPreview Post preview to be added
	 */
	public void addPostPreview(PostPreview newPostPreview) {
		postList.add(newPostPreview);
	}

	/**
	 * Returns the title of the subreddit
	 * @return Title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the list of post previews associated with the subreddit
	 * @return List of post previews
	 */
	public List<PostPreview> getPostList() {
		return postList;
	}

	/**
	 * Returns the URL of the next page of the subreddit
	 * @return
	 */
	public String getNextPageURL() {
		return nextPageURL;
	}
}