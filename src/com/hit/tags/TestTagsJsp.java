package com.hit.tags;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TestTagsJsp extends SimpleTagSupport{
	
	public void doTag() throws JspException, IOException
	 {
		 JspWriter out = getJspContext().getOut();
		 out.print("<a id='Admin' data-toggle='modal' data-target='#adminUser'><i title='Administrator Area' class='fa fa-user-circle-o fa-lg Icon' aria-hidden='false'></i></a>");	
	 }

}
