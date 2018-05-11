/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.students;

/**
 *
 * @author AriSurfacePro
 */
public class Comment {
    public final String user;
    public final String comment;

    public Comment(String user, String comment) {
        this.user = user;
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }
    
    
    
}
