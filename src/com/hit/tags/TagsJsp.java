package com.hit.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * When used the new tag, this function is called
 * in our project, this function prints the administrator page icon
 */
public class TagsJsp extends SimpleTagSupport
{

	public void doTag() throws JspException, IOException
	{
		JspWriter out = getJspContext().getOut();
		// prints an admin icon
		out.print(
		    "<a id='Admin' data-toggle='modal' data-target='#adminUser'><i title='Administrator Area' class='fa fa-user-circle-o fa-lg Icon' aria-hidden='false'></i></a>");
	}

}
