package controller;

import java.sql.*;
import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;




/**
 * Servlet implementation class main
 */
@WebServlet("/main")
public class main extends HttpServlet {
	Context ctx;
    DataSource ds;
    Connection con;
    Statement stmt;
    ResultSet rs;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public main() {
        super();
        // TODO Auto-generated constructor stub
    }
    /*static final String url="jdbc:mysql://localhost:3306/asset";
    static final String user="root";
    static final String pass="lokesh1999";*/
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session=request.getSession();
			session.setMaxInactiveInterval(1000000);
			
			//Class.forName("com.mysql.cj.jdbc.Driver");
			int id=Integer.parseInt(request.getParameter("username"));
			String password=request.getParameter("password");
			String key="passkey";
			ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/asset");
			java.sql.Connection con=ds.getConnection();
			con.setAutoCommit(false);
			//Connection con= DriverManager.getConnection(url,user,pass);
			String sql="Select * from UserN where UserID=? and password=aes_encrypt(?,?)";
			PreparedStatement ps=(PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2,password);
			ps.setString(3,key);
			ResultSet rs=ps.executeQuery();
			String actor="";
			String name="";
			
			while(rs.next())
			{
				 actor=rs.getString(2);
				 name=rs.getString(3);
			}
			
			//user obj=new user(id,name,actor);
			//request.setAttribute("userObj", obj);
			if(actor.equalsIgnoreCase("admin"))
			{
				AdminAction obj=new AdminAction(id, name, actor);
				session.setAttribute("userObj", obj);
				session.setAttribute("actor", actor);
			}
			else if (actor.equalsIgnoreCase("staff")) {
				
				StaffAction obj=new StaffAction(id, name, actor);
				session.setAttribute("userObj", obj);
				session.setAttribute("actor", actor);
			}
			else if (actor.equalsIgnoreCase("audit manager")) {
				AuditorAction obj=new AuditorAction(id, name, actor);
				session.setAttribute("userObj", obj);
				session.setAttribute("actor", actor);
			}
			else
			{	
				session.setAttribute("actor", actor);
			}
			
			RequestDispatcher view=request.getRequestDispatcher("MainView.jsp");
			view.forward(request, response);
						
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
