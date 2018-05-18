/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import javafx.beans.property.SimpleStringProperty;

public class UserAttribute {
    private SimpleStringProperty email;
    private SimpleStringProperty studentName;
    private SimpleStringProperty zid;
    
    public UserAttribute(String email, String studentName, String zid) {
        this.email = new SimpleStringProperty(email);
        this.studentName = new SimpleStringProperty(studentName);
        this.zid = new SimpleStringProperty(zid);
    }
    
    public String getEmail() {
        return email.get();
    }
    
    public String getStudentName() {
        return studentName.get();
    }
    
    public String getZid() {
        return zid.get();
    }
}
