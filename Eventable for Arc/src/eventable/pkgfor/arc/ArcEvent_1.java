/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventable.pkgfor.arc;

import javafx.beans.property.SimpleStringProperty;

public class ArcEvent_1 {
    public final SimpleStringProperty email, studentName, zid;

    public String getEmail() {
        return email.get();
    }

    public String getStudentName() {
        return studentName.get();
    }

    public String getZid() {
        return zid.get();
    }
       
    public ArcEvent_1(String email, String studentName, String zid) {
        this.email = new SimpleStringProperty(email);
        this.studentName = new SimpleStringProperty(studentName);
        this.zid = new SimpleStringProperty(zid);
    }
}
