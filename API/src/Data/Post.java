package Data;

import java.io.Serializable;

public class Post implements Serializable {
    private int postId;
    private String name;
    private Integer wage;

    public Post(int postId, String name, Integer wage) {
        this.postId = postId;
        this.name = name;
        this.wage = wage;
    }

    public Post(String name, Integer wage) {
        this.name = name;
        this.wage = wage;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWage() {
        return wage;
    }

    public void setWage(Integer wage) {
        this.wage = wage;
    }
}
