package aeriesrefresher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlBody;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;

public class HTMLParser{
	
	private String username;
	private String password;
	Class classes[];
	
	WebClient webClient;
    HtmlPage aeriesInitialPage;
    HtmlForm loginForm;
    HtmlTextInput usernameBox;
    HtmlPasswordInput passwordBox;
    HtmlAnchor submitAnchor;
    HtmlPage mainPage;
    HtmlAnchor gradeBookAnchor;
    HtmlPage gradeBookPage;
    HtmlBody gradeBookBody;
    List <HtmlTableRow> classList;
	
	public HTMLParser(String u, String p){
		username = u;
		password = p;
	}
	
	public Class[] getClasses(){
		try {
			refreshPage();
		}catch(Exception e){
			//System.out.println("error occurred");
			return null;
		}
		return classes;
	}
	
	public void refreshPage() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		//System.out.println("abi.lvusd.org is being opened");
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog"); 
		webClient = new WebClient();
		
		//at login page
		aeriesInitialPage = webClient.getPage("https://abi.lvusd.org/Login.asp");
	    loginForm = aeriesInitialPage.getFormByName("frmLogin");
	    usernameBox = loginForm.getInputByName("UserName");
	    passwordBox = loginForm.getInputByName("Password");
	    usernameBox.setValueAttribute(username);
	    passwordBox.setValueAttribute(password);
	    submitAnchor = (HtmlAnchor) loginForm.getElementsByAttribute("a", "href", "Javascript: document.frmLogin.submit()").get(0);
	    
	    //at main page
	    mainPage = submitAnchor.click();
	    gradeBookAnchor = (HtmlAnchor) mainPage.getElementById("Sub_ClassAssignments");
	   
	    //at gradebook page
	    gradeBookPage = gradeBookAnchor.click();
	    gradeBookBody = (HtmlBody) gradeBookPage.getElementsByTagName("body").get(0);
	    classList = gradeBookBody.getElementsByAttribute("tr", "onmouseover", "HighlightRow(this);");
	    classes = new Class[classList.size()];
		
	    //loop to create new Class objects and add them to an array;
		for(int i = 0; i < classList.size(); i++){
			List<DomNode> classInfo =  classList.get(i).getChildNodes();
	    	String name = ((HtmlTableDataCell)classInfo.get(1)).getTextContent();
	    	String updateDate = ((HtmlTableDataCell)classInfo.get(8)).getTextContent();
	    	String teacher = ((HtmlTableDataCell)classInfo.get(4)).getTextContent();
	    	String grade = ((HtmlTableDataCell)classInfo.get(5)).getTextContent();
			classes[i] = new Class(name,updateDate,teacher,grade);
		}
	}
	
	

}
