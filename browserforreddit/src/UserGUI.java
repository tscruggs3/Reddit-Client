import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class UserGUI implements SceneRender {

   /**
    *Creates the scene for a UserGUI page. This scene displays all posts and comments for the user. 
    *Also displays all relevant user information usch as CommentKarma, PostKarma, and links to all posts/comments.
    *The default view for a UserGUI is to display all posts by a given User.
    *@param user a User object that is fed into the method which then gets all relevant information for that given User
    *@param type the type of data that will be displayed when the method is called. Either "Comments" or "Posts", defaults to Posts.
    *@return the GUI for a given User to display all posts/comments/information
    */
    public static Scene getScene(User user, String type) {
        BorderPane root = new BorderPane();
        root.setPrefSize(SCENE_WIDTH,SCENE_HEIGHT);
        HBox userInfo = createUserInfo(user);
        Node centerPane;

        if(type.equals("Comments")) {
            centerPane = addComments(user.getComments());
        } else {
            centerPane = addPosts(user.getPosts());
        }

        ScrollPane postScroller = new ScrollPane(centerPane);
        postScroller.setFitToWidth(true);

        root.setTop(userInfo);
        root.setCenter(postScroller);
           
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
    

   //Creates a VBox on the Top of the BorderPane with all info of the user
   //We can change the font/size/color of stuff if we want to later
    private static HBox createUserInfo(User user) {
        VBox info = new VBox(15);

        HBox header = new HBox(55);
        header.setAlignment(Pos.BOTTOM_LEFT);

        Text username  = new Text("/u/" + user.getUsername());
        username.setFont(Font.font(FONT_TYPE_TITLE, FontWeight.BOLD, USERNAME_SIZE));

        Button back = SceneRender.buildBackButton();

        Node numPane = addNums(user);
        Node buttonPane = addButtons(user);

        info.getChildren().addAll(numPane, buttonPane);
        info.setAlignment(Pos.CENTER);
        header.getChildren().addAll(back, username, info);

        return header;
    }
    
    //Adds the posts/comments buttons that switch the information shown below the topPane of BorderPane
    private static Node addButtons(User user){
      GridPane button = new GridPane();
      button.setAlignment(Pos.CENTER);
      
      Button posts = new Button("Posts");
      button.add(posts,0,0);
      button.setHalignment(posts, HPos.RIGHT);
      posts.setOnAction(evt -> RedditController.requestUserPage("http://www.reddit.com/user/"+ user.getUsername(), "Posts"));
      
      Button comments = new Button("Comments");
      button.add(comments,1,0);
      comments.setOnAction(evt -> RedditController.requestUserPage("http://www.reddit.com/user/"+ user.getUsername(), "Comments"));
      
      //TODO when pressing buttons switch the centerPane of the border pane to
      //the corresponding button. Maybe reset the page?
      
      return button;
    }
    
    //Scraps all relevant information of a user to display it on their page.
    //User is given to the scene at the very begining
    private static Node addNums(User user){
       GridPane nums = new GridPane();
       nums.setAlignment(Pos.CENTER);
      
       Label num1 = new Label(user.getPostKarma());
       nums.add(num1,1,0);
      
       Label info1 = new Label("Post Karma: ");
       nums.add(info1,0,0);
       nums.setHalignment(info1, HPos.RIGHT);

      
       Label info2 = new Label("Comment Karma: ");
       nums.add(info2,0,1);
       nums.setHalignment(info2, HPos.RIGHT);

       Label num2 = new Label(user.getLinkKarma());
       nums.add(num2,1,1);
      
       return nums;
      }

   //Creates a VBox of posts for this user
   private static VBox addPosts(List<PostPreview> postList) {
        VBox postsVBox = new VBox();
        for (int i = 0; i < postList.size(); i ++){
            Node post = SceneRender.buildPostPreview(postList.get(i));
            postsVBox.getChildren().add(post);
        }
        return postsVBox;
    }

   //Creates the comment VBox for this user that can then be up/down voted
   //Also, should we add the 'context' button? I guess thats the URL variable of commentList
   //but I'm not sure
    private static VBox addComments(List<CommentPreview> commentList) {
        VBox commentVBox = new VBox();
        for (int i = 0; i < commentList.size(); i ++){
            Node comment = SceneRender.buildCommentPreview(commentList.get(i));
            commentVBox.getChildren().add(comment);
        }
        return commentVBox;
    }
}
