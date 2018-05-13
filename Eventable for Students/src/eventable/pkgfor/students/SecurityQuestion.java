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
public class SecurityQuestion {
    public String securityQuestionWording;
    public int id;

    public SecurityQuestion(String securityQuestionWording, int id) {
        this.securityQuestionWording = securityQuestionWording;
        this.id = id;
    }

    public String getSecurityQuestionWording() {
        return securityQuestionWording;
    }       

    public int getId() {
        return id;
    }
    
    
}
